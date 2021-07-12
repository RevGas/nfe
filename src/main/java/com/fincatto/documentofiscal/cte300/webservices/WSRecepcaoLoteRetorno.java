package com.fincatto.documentofiscal.cte300.webservices;

import br.inf.portalfiscal.cte.CteProc;
import br.inf.portalfiscal.cte.TConsReciCTe;
import br.inf.portalfiscal.cte.TEvento;
import br.inf.portalfiscal.cte.TRetConsReciCTe;
import br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.hom.*;
import com.fincatto.documentofiscal.DFLog;
import com.fincatto.documentofiscal.S3;
import com.fincatto.documentofiscal.cte300.CTeConfig;
import com.fincatto.documentofiscal.cte300.parsers.CTChaveParser;
import com.fincatto.documentofiscal.cte300.parsers.CTeParser;
import com.fincatto.documentofiscal.utils.Util;
import com.tartigrado.df.validadores.cte.CTeValidatorFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.Holder;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

class WSRecepcaoLoteRetorno implements DFLog {

    private static final String VERSAO = "3.00";
    private final CTeConfig config;

    WSRecepcaoLoteRetorno(final CTeConfig config) {
        this.config = config;
    }

    TRetConsReciCTe consultaLote(final String nRec) throws Exception {
        return efetuaConsulta(gerarDadosConsulta(nRec));
    }

    private TRetConsReciCTe efetuaConsulta(final TConsReciCTe tConsReciCTe) throws Exception {
        JAXBElement<TConsReciCTe> etConsReciCTe = CTeParser.parserTConsReciCTe(tConsReciCTe);
        CTeValidatorFactory.padrao().validaTConsReciCTe(Util.marshllerTConsReciCTe(etConsReciCTe));

        TRetConsReciCTe tRetConsReciCTe = GatewayRetRecepcao.valueOfCodigoUF(new CTChaveParser().getTUf(this.config.getCUF().getCodigoIbge()))
                .getTRetConsReciCTe(etConsReciCTe, this.config);

        sendTRetEnviCTe(tRetConsReciCTe);
        uploadProcCTe(tRetConsReciCTe);
        return tRetConsReciCTe;
    }

    private void uploadProcCTe(TRetConsReciCTe tRetConsReciCTe) {
        try {
            if (!tRetConsReciCTe.getProtCTe().isEmpty() && tRetConsReciCTe.getProtCTe().get(0).getInfProt().getCStat().equals("100")) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = factory.newDocumentBuilder();
                //Recuperar enviNFe do S3
                File enviCTe = new S3().downloadFile(S3.bucket, S3.getPathCte(Util.chaveFromtRetConsReciCTe(tRetConsReciCTe), "enviCTe", tRetConsReciCTe.getTpAmb()));
                //Instanciar um CteProc para ser manipulado
                CteProc cteProc = new CteProc();
                cteProc.setVersao(tRetConsReciCTe.getVersao());
                //Converter enviNFe e retEnviNFe e procNFe em Document
                Document enviCTeDocument = dBuilder.parse(enviCTe);
                Document retEnviCTeDocument = dBuilder.parse(new ByteArrayInputStream(Util.marshllerRetConsReciCTe(new br.inf.portalfiscal.cte.ObjectFactory().createRetConsReciCTe(tRetConsReciCTe)).getBytes()));
                String cte = Util.marshallerCteProc(cteProc);
                Document cteProcDocument = dBuilder.parse(new ByteArrayInputStream(cte.getBytes()));
                StringWriter stringResult3 = new StringWriter();
                TransformerFactory.newInstance().newTransformer().transform(new DOMSource(cteProcDocument), new StreamResult(stringResult3));
                //Extrair e importar Nodes no nfeProc
                Node cteNode = enviCTeDocument.getElementsByTagName("CTe").item(0);
                Node prot = retEnviCTeDocument.getElementsByTagName("protCTe").item(0);
                cteNode = cteProcDocument.importNode(cteNode, true);
                prot = cteProcDocument.importNode(prot, true);
                Node nfeProcNode = cteProcDocument.getFirstChild();
                nfeProcNode.appendChild(cteNode);
                nfeProcNode.appendChild(prot);
                //Gerar string de procNfe e enviar para o S3
                DOMSource source = new DOMSource(nfeProcNode);
                StringWriter stringResult = new StringWriter();
                TransformerFactory.newInstance().newTransformer().transform(source, new StreamResult(stringResult));
                new S3().sendProcCTe(stringResult.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private TConsReciCTe gerarDadosConsulta(final String nRec) {
        TConsReciCTe tConsReciCTe = new TConsReciCTe();
        tConsReciCTe.setNRec(nRec);
        tConsReciCTe.setTpAmb(this.config.getAmbiente().getCodigo());
        tConsReciCTe.setVersao("3.00");
        return tConsReciCTe;
    }

    public static void sendTRetEnviCTe(TRetConsReciCTe retorno) throws JAXBException, IOException {
        try {
            new S3().sendTRetConsReciCTe(retorno); //Tentar enviar para o S3
        } catch (IndexOutOfBoundsException exception) {
            System.out.println("OCORREU UM ERRO AO TENTAR ENVIAR PARA O S3");
        }

    }

}
