package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.*;
import com.fincatto.documentofiscal.DFLog;
import com.fincatto.documentofiscal.S3;
import com.fincatto.documentofiscal.nfe.NFeConfig;
import com.fincatto.documentofiscal.nfe400.NotaFiscalChaveParser;
import com.fincatto.documentofiscal.utils.DFSocketFactory;
import com.fincatto.documentofiscal.utils.Util;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;

class WSNotaConsulta implements DFLog {
    private static final String NOME_SERVICO = "CONSULTAR";
    private static final String VERSAO_SERVICO = "4.00";
    private final NFeConfig config;
    
    WSNotaConsulta(final NFeConfig config) {
        this.config = config;
    }

    TRetConsSitNFe consultaProtocolo(final String chNFe) throws Exception {
        return this.efetuaConsulta(gerarDadosConsulta(chNFe));
    }

    private TRetConsSitNFe efetuaConsulta(JAXBElement<TConsSitNFe> tConsSitNFe) throws Exception {
        final NotaFiscalChaveParser chaveParser = new NotaFiscalChaveParser(tConsSitNFe.getValue().getChNFe());
        return GatewayConsultaProtocolo.valueOfCodigoUF(chaveParser.getNFUnidadeFederativa()).getTRetConsSitNFe(tConsSitNFe, chaveParser.getModelo(), config.getAmbiente(), new DFSocketFactory(config).createSSLContext().getSocketFactory());
    }

    String consultaNota(String chNFe, String TpAmb) {
        String procNFe;
        try{
            procNFe = Util.fileToString(new S3().downloadProcNFe(chNFe, TpAmb));
            return procNFe;
        } catch (Exception ignored){}

        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            TRetConsSitNFe retConsSitNFe =  consultaProtocolo(chNFe);
            TNfeProc tNfeProc = new TNfeProc();
            tNfeProc.setVersao(retConsSitNFe.getVersao());
            Document enviNFeDocument = dBuilder.parse(new S3().downloadEnviNFe(chNFe, TpAmb));
            Document retConsSitNFeDocument = dBuilder.parse(new ByteArrayInputStream(Util.marshllerretConsSitNFe(new ObjectFactory().createRetConsSitNFe(retConsSitNFe)).getBytes()));
            Document nfeProcDocument = dBuilder.parse(new ByteArrayInputStream(Util.marshlerNfeProc(new ObjectFactory().createNfeProc(tNfeProc)).getBytes()));

            //Extrair e importar Nodes no nfeProc
            Node nfeNode = enviNFeDocument.getElementsByTagName("NFe").item(0);
            Node prot = retConsSitNFeDocument.getElementsByTagName("protNFe").item(0);
            nfeNode = nfeProcDocument.importNode(nfeNode, true);
            prot = nfeProcDocument.importNode(prot, true);
            Node nfeProcNode = nfeProcDocument.getFirstChild();
            nfeProcNode.appendChild(nfeNode);
            nfeProcNode.appendChild(prot);

            //Gerar string de procNfe e enviar para o S3
            DOMSource source = new DOMSource(nfeProcNode);
            StringWriter stringResult = new StringWriter();
            TransformerFactory.newInstance().newTransformer().transform(source, new StreamResult(stringResult));

            new S3().sendProcNFe(stringResult.toString());
            return stringResult.toString();
        } catch (Exception ignored){}
        return null;
    }
    
    private JAXBElement<TConsSitNFe> gerarDadosConsulta(final String chNFe) {
        final TConsSitNFe consulta = new TConsSitNFe();
        consulta.setChNFe(chNFe);
        consulta.setTpAmb(this.config.getAmbiente().getCodigo());
        consulta.setVersao(WSNotaConsulta.VERSAO_SERVICO);
        consulta.setXServ(WSNotaConsulta.NOME_SERVICO);
        
        JAXBElement<TConsSitNFe> tConsSitNFe = (JAXBElement<TConsSitNFe>) new ObjectFactory().createConsSitNFe(consulta);
        
        return tConsSitNFe;
    }
}
