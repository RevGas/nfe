package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.*;
import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.S3;
import com.fincatto.documentofiscal.nfe.NFeConfig;

import com.fincatto.documentofiscal.DFLog;
import com.fincatto.documentofiscal.nfe400.classes.NFRetornoStatus;
import com.fincatto.documentofiscal.utils.Util;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Objects;

class WSLoteConsulta implements DFLog {
    
    private final NFeConfig config;
    
    WSLoteConsulta(final NFeConfig config) {
        this.config = config;
    }

    TRetConsReciNFe consultaLote(final String numeroRecibo, final DFModelo modelo) throws Exception {
        TRetConsReciNFe tRetConsReciNFe = GatewayLoteConsulta.valueOfCodigoUF(this.config.getCUF()).getTRetConsReciNFe(numeroRecibo, this.config.getAmbiente(), this.config.getVersao());
        if(tRetConsReciNFe.getProtNFe() != null && !tRetConsReciNFe.getProtNFe().isEmpty() && Objects.equals(tRetConsReciNFe.getProtNFe().get(0).getInfProt().getCStat(), String.valueOf(NFRetornoStatus.CODIGO_100.getCodigo()))){ // TODO melhorar verificação de status
            uploadProcNFe(tRetConsReciNFe);
        }
        return tRetConsReciNFe;
    }

    void uploadProcNFe(TRetConsReciNFe tRetConsReciNFe) throws ParserConfigurationException, IOException, SAXException, JAXBException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();

        //Recuperar enviNFe do S3
        File enviNFe = new S3().downloadFile(S3.bucket, S3.getPath(tRetConsReciNFe.getProtNFe().get(0).getInfProt().getChNFe(), "enviNFe", tRetConsReciNFe.getTpAmb()));

        //Instanciar um ProcNFE para ser manipulado
        TNfeProc tNfeProc = new TNfeProc();
        tNfeProc.setVersao(tRetConsReciNFe.getVersao());

        //Converter enviNFe e retEnviNFe e procNFe em Document
        Document enviNFeDocument = dBuilder.parse(enviNFe);
        Document retConsReciNFe = dBuilder.parse(new ByteArrayInputStream(Util.marshllerRetRetConsReciNFe(new ObjectFactory().createRetConsReciNFe(tRetConsReciNFe)).getBytes()));
        Document nfeProcDocument = dBuilder.parse(new ByteArrayInputStream(Util.marshlerNfeProc(new ObjectFactory().createNfeProc(tNfeProc)).getBytes()));

        //Extrair e importar Nodes no nfeProc
        Node nfeNode = enviNFeDocument.getElementsByTagName("NFe").item(0);
        Node prot = retConsReciNFe.getElementsByTagName("protNFe").item(0);
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
    }

}
