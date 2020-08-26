package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.model.evento_manifesta_destinatario.Evento_ManifestaDest_PL_v101.TEnvEvento;
import br.inf.portalfiscal.nfe.model.evento_manifesta_destinatario.Evento_ManifestaDest_PL_v101.TRetEnvEvento;
import com.fincatto.documentofiscal.DFAmbiente;
import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.DFUnidadeFederativa;
import com.fincatto.documentofiscal.S3;
import com.fincatto.documentofiscal.utils.Util;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public enum GatewayManifestaDestinatario {

    AN {
        @Override
        public TRetEnvEvento getTRetEnvEvento(final DFModelo modelo, final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetEnvEventoANNFE(xml, ambiente) : getTRetEnvEventoANNFCE(xml, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{};
        }
    };

    public abstract TRetEnvEvento getTRetEnvEvento(final DFModelo modelo, final String xml, final DFAmbiente ambiente) throws JAXBException, Exception;

    public abstract DFUnidadeFederativa[] getUFs();

    public static GatewayManifestaDestinatario valueOfCodigoUF(final DFUnidadeFederativa uf) {
        for (final GatewayManifestaDestinatario autorizador : GatewayManifestaDestinatario.values()) {
            if (Arrays.asList(autorizador.getUFs()).contains(uf)) {
                return autorizador;
            }
        }
        throw new IllegalStateException(String.format("N\u00e3o existe metodo de envio para a UF %s", uf.getCodigo()));
    }

    public TRetEnvEvento getTRetEnvEventoANNFE(String xml, DFAmbiente ambiente) throws JAXBException, IOException {
        Object retorno;
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.an.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.an.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnvEvento(xml));

            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.an.NFeRecepcaoEvento4SoapManifestaDestinatario port = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.an.NFeRecepcaoEvento4().getNFeRecepcaoEvento4SoapManifestaDestinatario();
            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.an.NfeRecepcaoEventoNFResult result = port.nfeRecepcaoEventoNF(nfeDadosMsg);

            retorno = result.getContent().get(0);
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.an.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.an.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnvEvento(xml));

            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.an.hom.NFeRecepcaoEvento4SoapManifestaDestinatario port = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.an.hom.NFeRecepcaoEvento4().getNFeRecepcaoEvento4SoapManifestaDestinatario();
            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.an.hom.NfeRecepcaoEventoNFResult result = port.nfeRecepcaoEventoNF(nfeDadosMsg);

            retorno = result.getContent().get(0);
        }
        sendRetEnvEvento(retorno);
        return ((JAXBElement<TRetEnvEvento>) retorno).getValue();
    }

    public TRetEnvEvento getTRetEnvEventoANNFCE(String xml, DFAmbiente ambiente) throws JAXBException {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            return null;
        } else {
            return null;
        }
    }

    private JAXBElement<TEnvEvento> getTEnvEvento(String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.nfe.model.evento_manifesta_destinatario.Evento_ManifestaDest_PL_v101");

        Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(xml);
        JAXBElement<TEnvEvento> tEnvEvento = (JAXBElement<TEnvEvento>) jaxbUnmarshaller.unmarshal(reader);
        return tEnvEvento;
    }

    public static void sendRetEnvEvento(Object retorno) throws JAXBException, IOException {
        new S3().sendRetEnvEventoManifestacao (Util.marshllerRetEnvEventoManifestacao ((JAXBElement<br.inf.portalfiscal.nfe.model.evento_manifesta_destinatario.Evento_ManifestaDest_PL_v101.TRetEnvEvento>) retorno), ((JAXBElement<br.inf.portalfiscal.nfe.model.evento_manifesta_destinatario.Evento_ManifestaDest_PL_v101.TRetEnvEvento>) retorno).getValue()); //Tentar enviar para o S3
    }

    public static void sendRetEnvEvento(Object retorno, String chaveNFe) throws JAXBException, IOException {
        new S3().sendRetEnvEventoManifestacao (Util.marshllerRetEnvEventoManifestacao ((JAXBElement<br.inf.portalfiscal.nfe.model.evento_manifesta_destinatario.Evento_ManifestaDest_PL_v101.TRetEnvEvento>) retorno), ((JAXBElement<br.inf.portalfiscal.nfe.model.evento_manifesta_destinatario.Evento_ManifestaDest_PL_v101.TRetEnvEvento>) retorno).getValue(), chaveNFe); //Tentar enviar para o S3
    }

}