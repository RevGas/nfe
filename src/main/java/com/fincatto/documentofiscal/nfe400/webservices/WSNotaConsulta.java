package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.ObjectFactory;
import br.inf.portalfiscal.nfe.TConsSitNFe;
import br.inf.portalfiscal.nfe.TNfeProc;
import br.inf.portalfiscal.nfe.TRetConsSitNFe;
import com.fincatto.documentofiscal.DFLog;
import com.fincatto.documentofiscal.S3;
import com.fincatto.documentofiscal.nfe.NFeConfig;
import com.fincatto.documentofiscal.nfe400.NotaFiscalChaveParser;
import com.fincatto.documentofiscal.utils.Util;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

class WSNotaConsulta implements DFLog {
    private static final String NOME_SERVICO = "CONSULTAR";
    private static final String VERSAO_SERVICO = "4.00";
    private final NFeConfig config;
    
    WSNotaConsulta(final NFeConfig config) {
        this.config = config;
    }

    TRetConsSitNFe consultaProtocolo(final String chNFe) throws Exception {
        TRetConsSitNFe tRetConsSitNFe = this.efetuaConsulta(gerarDadosConsulta(chNFe));
        return tRetConsSitNFe;
    }

    private TRetConsSitNFe efetuaConsulta(JAXBElement<TConsSitNFe> tConsSitNFe) throws Exception {
        final NotaFiscalChaveParser chaveParser = new NotaFiscalChaveParser(tConsSitNFe.getValue().getChNFe());
        return GatewayConsultaProtocolo.valueOfCodigoUF(chaveParser.getNFUnidadeFederativa()).getTRetConsSitNFe(tConsSitNFe, chaveParser.getModelo(), config.getAmbiente());
    }

    String consultaNota(String chNFe, String TpAmb) throws IOException {
        String procNFe;
        procNFe = Util.fileToString(new S3().downloadProcNFe(chNFe, TpAmb));
        return procNFe;

//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder dBuilder = factory.newDocumentBuilder();
//
//        //Recuperar enviNFe do S3
//        File enviNFe = null;
//        File retEnviNFe = null;
//        try{
//            enviNFe = new S3().downloadEnviNFe(chNFe, TpAmb);
//        }catch (IOException ex){
//            System.out.println(ex.getMessage());
//        }
//        try{
//            retEnviNFe = new S3().downloadRetEnviNFe(chNFe, TpAmb);
//        }catch (IOException ex){
//            System.out.println(ex.getMessage());
//        }

//        //Instanciar um ProcNFE para ser manipulado
//        TNfeProc tNfeProc = new TNfeProc();
//        tNfeProc.setVersao(retEnviNFe.getVersao());
//
//        //Converter enviNFe e retEnviNFe e procNFe em Document
//        Document enviNFeDocument = dBuilder.parse(enviNFe);
//        Document retEnviNFeDocument = dBuilder.parse(new ByteArrayInputStream( Util.fileToString(retEnviNFe).getBytes()));
//        Document nfeProcDocument = dBuilder.parse(new ByteArrayInputStream(Util.marshlerNfeProc(new ObjectFactory().createNfeProc(tNfeProc)).getBytes()));
//
//        //Extrair e importar Nodes no nfeProc
//        Node nfeNode = enviNFeDocument.getElementsByTagName("NFe").item(0);
//        Node prot = retEnviNFeDocument.getElementsByTagName("protNFe").item(0);
//        nfeNode = nfeProcDocument.importNode(nfeNode, true);
//        prot = nfeProcDocument.importNode(prot, true);
//        Node nfeProcNode = nfeProcDocument.getFirstChild();
//        nfeProcNode.appendChild(nfeNode);
//        nfeProcNode.appendChild(prot);
//
//        //Gerar string de procNfe e enviar para o S3
//        DOMSource source = new DOMSource(nfeProcNode);
//        StringWriter stringResult = new StringWriter();
//        TransformerFactory.newInstance().newTransformer().transform(source, new StreamResult(stringResult));
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