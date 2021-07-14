package com.fincatto.documentofiscal.cte300.webservices;

import br.inf.portalfiscal.cte.*;
import br.inf.portalfiscal.cte.wsdl.cterecepcao.svrs.hom.CteCabecMsg;
import br.inf.portalfiscal.cte.wsdl.cterecepcao.svrs.hom.CteDadosMsg;
import br.inf.portalfiscal.cte.wsdl.cterecepcao.svrs.hom.ObjectFactory;
import br.inf.portalfiscal.cte.wsdl.cterecepcaosinc.svrs.hom.CteRecepcaoSinc;
import br.inf.portalfiscal.cte.wsdl.cterecepcaosinc.svrs.hom.CteRecepcaoSincResult;
import br.inf.portalfiscal.cte.wsdl.cterecepcaosinc.svrs.hom.CteRecepcaoSincSoap12;
import com.fincatto.documentofiscal.DFLog;
import com.fincatto.documentofiscal.S3;
import com.fincatto.documentofiscal.cte300.CTeConfig;
import com.fincatto.documentofiscal.cte300.parsers.CTChaveParser;
import com.fincatto.documentofiscal.cte300.parsers.CTeParser;
import com.fincatto.documentofiscal.cte300.utils.CTeGeraChave;
import com.fincatto.documentofiscal.utils.DFAssinaturaDigital;
import com.fincatto.documentofiscal.utils.Util;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
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
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

class WSRecepcaoLote implements DFLog {

    private static final String VERSAO = "3.00";
    private final CTeConfig config;

    WSRecepcaoLote(final CTeConfig config) {
        this.config = config;
    }

    public TRetEnviCTe envioRecepcao(TEnviCTe tEnviCTe) throws Exception {
        this.addDataCTE(tEnviCTe);
        CTeValidatorFactory.padrao().validaTEnviCTe(getDocumentoAssinado(tEnviCTe));

        return GatewayRecepcao.valueOfCodigoUF(new CTChaveParser().getTUf(tEnviCTe.getCTe().get(0).getInfCte().getIde().getCUF())).getTRetEnviCTe(tEnviCTe, this.config);
    }

    public TRetCTe envioRecepcaoSinc(TEnviCTe tEnviCTe) throws Exception {
        this.addDataCTE(tEnviCTe);
        TCTe tcTe = tEnviCTe.getCTe().get(0);

        TRetCTe tRetCTe = GatewayRecepcao.valueOfCodigoUF(new CTChaveParser().getTUf(tcTe.getInfCte().getIde().getCUF())).getTRetCTe(tcTe, this.config);

        sendRetCTe(tRetCTe, tcTe);
        uploadProcCTe(tRetCTe);

        return tRetCTe;
    }

    private void sendRetCTe(TRetCTe tRetCTe, TCTe tcTe) {
        try {
            new S3().sendTRetCTe(tRetCTe, tcTe);
        } catch (JAXBException | IOException e) {
            System.out.println("OCORREU UM ERRO AO ENVIAR RETORNO SINC CTE");
        }
    }

    private void uploadProcCTe(TRetCTe tRetCTe) {
        try {
            if (tRetCTe.getProtCTe() != null && tRetCTe.getProtCTe().getInfProt().getCStat().equals("100")) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = factory.newDocumentBuilder();
                //Recuperar enviNFe do S3
                File CTe = new S3().downloadFile(S3.bucket, S3.getPathCte(Util.chaveFromtRetCTe(tRetCTe), "CTe", tRetCTe.getTpAmb()));
                //Instanciar um CteProc para ser manipulado
                CteProc cteProc = new CteProc();
                cteProc.setVersao(tRetCTe.getVersao());
                //Converter enviNFe e retEnviNFe e procNFe em Document
                Document enviCTeDocument = dBuilder.parse(CTe);
                Document retEnviCTeDocument = dBuilder.parse(new ByteArrayInputStream(Util.marshllerRetCTe(new br.inf.portalfiscal.cte.ObjectFactory().createRetCTe(tRetCTe)).getBytes()));
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

    private void addDataCTE(TEnviCTe tEnviCTe) {
        CTeGeraChave gerarChave = new CTeGeraChave(tEnviCTe);
        tEnviCTe.getCTe().get(0).getInfCte().getIde().setCCT(gerarChave.geraCodigoRandomico());
        tEnviCTe.getCTe().get(0).getInfCte().setId("CTe" + gerarChave.getChaveAcesso());
        tEnviCTe.getCTe().get(0).getInfCTeSupl().setQrCodCTe("https://dfe-portal.svrs.rs.gov.br/cte/qrCode?chCTe=" +
                tEnviCTe.getCTe().get(0).getInfCte().getId().replace("CTe", "") +
                "&tpAmb=" +
                tEnviCTe.getCTe().get(0).getInfCte().getIde().getTpAmb());
        tEnviCTe.getCTe().get(0).getInfCte().getIde().setCDV(gerarChave.getDV().toString());
    }

    private String getDocumentoAssinado(TEnviCTe tEnviCTe) throws JAXBException, Exception {
        return new DFAssinaturaDigital(this.config).assinarDocumento(CTeParser.parserTEnviCTe(tEnviCTe).replace(" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", ""), "infCte");
    }

    private String getDocumentoAssinado(TCTe tcTe) throws JAXBException, Exception {
        return new DFAssinaturaDigital(this.config).assinarDocumento(CTeParser.parserTCTe(tcTe).replace(" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", ""), "infCte");
    }

}
