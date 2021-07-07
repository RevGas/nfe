package com.fincatto.mdfe300.webservices;

import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import com.fincatto.documentofiscal.utils.DFSocketFactory;
import com.fincatto.mdfe300.classes.MDFAutorizador;

import br.inf.portalfiscal.mdfe.TEnviMDFe;
import br.inf.portalfiscal.mdfe.TRetEnviMDFe;
import br.inf.portalfiscal.mdfe.wsdl.mdferecepcao.MDFeRecepcao;
import br.inf.portalfiscal.mdfe.wsdl.mdferecepcao.MDFeRecepcaoSoap12;
import br.inf.portalfiscal.mdfe.wsdl.mdferecepcao.MdfeCabecMsg;
import br.inf.portalfiscal.mdfe.wsdl.mdferecepcao.MdfeDadosMsg;
import br.inf.portalfiscal.mdfe.wsdl.mdferecepcao.MdfeRecepcaoLoteResult;
import br.inf.portalfiscal.mdfe.wsdl.mdferecepcao.ObjectFactory;
import com.fincatto.documentofiscal.utils.DFAssinaturaDigital;

import com.fincatto.mdfe300.MDFeConfig;
import com.fincatto.mdfe300.classes.RetornoEnvioMDFe;

class WSRecepcao {

    private static final String VERSAO_LEIAUTE = "3.00";
    private final MDFeConfig config;

    WSRecepcao(final MDFeConfig config) {
        this.config = config;
    }

    RetornoEnvioMDFe enviaMDFe(TEnviMDFe enviMDFe) throws Exception {
        br.inf.portalfiscal.mdfe.ObjectFactory factoryObject = new br.inf.portalfiscal.mdfe.ObjectFactory();

        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.mdfe");
        Marshaller marshaller = context.createMarshaller();

        JAXBElement<TEnviMDFe> tEnviMDFe = factoryObject.createEnviMDFe(enviMDFe);
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(tEnviMDFe, stringWriter);

        //Verificar a melhor forma de remover o namespace da assinatura
        String documentoAssinado = new DFAssinaturaDigital(this.config).assinarDocumento(stringWriter.toString().replace(" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", ""), "infMDFe");
        StringReader reader = new StringReader(documentoAssinado);

        Unmarshaller unmarshaller = context.createUnmarshaller();
        tEnviMDFe = (JAXBElement<TEnviMDFe>) unmarshaller.unmarshal(new StreamSource(reader));

        MdfeDadosMsg mdfeDadosMsg = new MdfeDadosMsg();
        mdfeDadosMsg.getContent().add(tEnviMDFe);

        MdfeCabecMsg mdfeCabecMsg = new MdfeCabecMsg();
        mdfeCabecMsg.setCUF(this.config.getCUF().getCodigoIbge());
        mdfeCabecMsg.setVersaoDados(VERSAO_LEIAUTE);

        Holder<MdfeCabecMsg> holder = new Holder<>(new ObjectFactory().createMdfeCabecMsg(mdfeCabecMsg).getValue());

        MDFeRecepcaoSoap12 port = new MDFeRecepcao(new URL(MDFAutorizador.MDFe.getMDFeRecepcao(this.config.getAmbiente()))).getMDFeRecepcaoSoap12();
        ((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory", new DFSocketFactory(config).createSSLContext().getSocketFactory());
        MdfeRecepcaoLoteResult result = port.mdfeRecepcaoLote(mdfeDadosMsg, holder);

        RetornoEnvioMDFe retornoEnvioMDFe = new RetornoEnvioMDFe(documentoAssinado, ((JAXBElement<TRetEnviMDFe>) result.getContent().get(0)).getValue());

        return retornoEnvioMDFe;
    }
}
