package com.fincatto.mdfe300.webservices;

import java.io.StringWriter;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;

import com.fincatto.mdfe300.classes.MDFAutorizador;

import br.inf.portalfiscal.mdfe.TEnviMDFe;
import br.inf.portalfiscal.mdfe.TRetMDFe;
import br.inf.portalfiscal.mdfe.wsdl.mdferecepcaosinc.MDFeRecepcaoSinc;
import br.inf.portalfiscal.mdfe.wsdl.mdferecepcaosinc.MDFeRecepcaoSincSoap12;
import br.inf.portalfiscal.mdfe.wsdl.mdferecepcaosinc.MdfeRecepcaoResult;
import com.fincatto.documentofiscal.utils.DFAssinaturaDigital;

import com.fincatto.mdfe300.MDFeConfig;
import com.fincatto.mdfe300.classes.RetornoEnvioMDFe;

class WSRecepcao {

    private static final String VERSAO_LEIAUTE = "3.00";
    private static final String URL_QRCODE = "https://dfe-portal.svrs.rs.gov.br/mdfe/qrCode";
    private final MDFeConfig config;

    WSRecepcao(final MDFeConfig config) {
        this.config = config;
    }

    RetornoEnvioMDFe enviaMDFe(TEnviMDFe enviMDFe) throws Exception {
        br.inf.portalfiscal.mdfe.ObjectFactory factoryObject = new br.inf.portalfiscal.mdfe.ObjectFactory();

        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.mdfe");
        Marshaller marshaller = context.createMarshaller();
        
        /**
         * QR Code https://dfe-portal.svrs.rs.gov.br/Mdfe/Documentos#
         * 1ª parte - URL para acessar o MDF-e, seguido do caractere “?”
         * 2ª parte - parâmetros chMDFe e tpAmb da mesma forma como na forma de emissão normal separados pelo caractere “&”; 
         */
        enviMDFe.getMDFe().getInfMDFeSupl().setQrCodMDFe(URL_QRCODE + "?chMDFe=" +
            enviMDFe.getMDFe().getInfMDFe().getId().replace("MDFe", "") + "&tpAmb=" +
            enviMDFe.getMDFe().getInfMDFe().getIde().getTpAmb());
        
        JAXBElement<TEnviMDFe> tEnviMDFe = factoryObject.createEnviMDFe(enviMDFe);
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(tEnviMDFe, stringWriter);

        //Verificar a melhor forma de remover o namespace da assinatura
        String documentoAssinado = new DFAssinaturaDigital(this.config).assinarDocumento(stringWriter.toString().replace(" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", ""), "infMDFe");
      
        MDFeRecepcaoSincSoap12 port = new MDFeRecepcaoSinc(new URL(MDFAutorizador.MDFe.getMDFeRecepcaoSinc(this.config.getAmbiente()))).getMDFeRecepcaoSincSoap12();
        MdfeRecepcaoResult result = port.mdfeRecepcao(documentoAssinado);

        RetornoEnvioMDFe retornoEnvioMDFe = new RetornoEnvioMDFe(documentoAssinado, (TRetMDFe) result.getContent().get(0));
        
        return retornoEnvioMDFe;
    }
}
