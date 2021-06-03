package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.ObjectFactory;
import br.inf.portalfiscal.nfe.TConsReciNFe;
import br.inf.portalfiscal.nfe.TRetConsReciNFe;
import com.fincatto.documentofiscal.DFAmbiente;
import com.fincatto.documentofiscal.DFUnidadeFederativa;
import java.util.Arrays;
import javax.net.ssl.SSLSocketFactory;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.ws.BindingProvider;

public enum GatewayLoteConsulta {

    BA {
        @Override
        public TRetConsReciNFe getTRetConsReciNFe(final String numeroRecibo, final DFAmbiente ambiente, final String versao, final SSLSocketFactory socketFactory) throws JAXBException, Exception {
            return getTRetConsReciNFeBANFE(versao, numeroRecibo, ambiente, socketFactory);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.BA};
        }
    },
    SVRS {
        @Override
        public TRetConsReciNFe getTRetConsReciNFe(final String numeroRecibo, final DFAmbiente ambiente, final String versao, final SSLSocketFactory socketFactory) throws JAXBException, Exception {
            return getTRetConsReciNFeSVRSNFE(versao, numeroRecibo, ambiente, socketFactory);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.PI};
        }
    },
    SP {
        @Override
        public TRetConsReciNFe getTRetConsReciNFe(final String numeroRecibo, final DFAmbiente ambiente, final String versao, final SSLSocketFactory socketFactory) throws JAXBException, Exception {
            return getTRetConsReciNFeSPNFE(versao, numeroRecibo, ambiente, socketFactory);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.SP};
        }
    };


    public abstract TRetConsReciNFe getTRetConsReciNFe(final String numeroRecibo, final DFAmbiente ambiente, final String versao, final SSLSocketFactory socketFactory) throws JAXBException, Exception;

    public abstract DFUnidadeFederativa[] getUFs();

    public static GatewayLoteConsulta valueOfCodigoUF(final DFUnidadeFederativa uf) {
        for (final GatewayLoteConsulta autorizador : GatewayLoteConsulta.values()) {
            if (Arrays.asList(autorizador.getUFs()).contains(uf)) {
                return autorizador;
            }
        }
        throw new IllegalStateException(String.format("N\u00e3o existe metodo de envio para a UF %s", uf.getCodigo()));
    }

    public TRetConsReciNFe getTRetConsReciNFeBANFE(final String versao, final String numeroRecibo, final DFAmbiente ambiente, final SSLSocketFactory socketFactory) throws JAXBException, Exception {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nferetautorizacao4.ba.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nferetautorizacao4.ba.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTConsReciNFe(versao, numeroRecibo, ambiente));

            br.inf.portalfiscal.nfe.wsdl.nferetautorizacao4.ba.NFeRetAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nferetautorizacao4.ba.NFeRetAutorizacao4().getNFeRetAutorizacao4Soap();
            ((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory", socketFactory);
            br.inf.portalfiscal.nfe.wsdl.nferetautorizacao4.ba.NfeResultMsg result = port.nfeRetAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetConsReciNFe>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nferetautorizacao4.ba.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nferetautorizacao4.ba.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTConsReciNFe(versao, numeroRecibo, ambiente));

            br.inf.portalfiscal.nfe.wsdl.nferetautorizacao4.ba.hom.NFeRetAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nferetautorizacao4.ba.hom.NFeRetAutorizacao4().getNFeRetAutorizacao4Soap();
            ((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory", socketFactory);
            br.inf.portalfiscal.nfe.wsdl.nferetautorizacao4.ba.hom.NfeResultMsg result = port.nfeRetAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetConsReciNFe>) result.getContent().get(0)).getValue();
        }
    }

    public TRetConsReciNFe getTRetConsReciNFeSVRSNFE(final String versao, final String numeroRecibo, final DFAmbiente ambiente, final SSLSocketFactory socketFactory) throws JAXBException, Exception {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            return null;
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nferetautorizacao4.svrs.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nferetautorizacao4.svrs.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTConsReciNFe(versao, numeroRecibo, ambiente));

            br.inf.portalfiscal.nfe.wsdl.nferetautorizacao4.svrs.hom.NFeRetAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nferetautorizacao4.svrs.hom.NFeRetAutorizacao4().getNFeRetAutorizacao4Soap();
            ((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory", socketFactory);
            br.inf.portalfiscal.nfe.wsdl.nferetautorizacao4.svrs.hom.NfeResultMsg result = port.nfeRetAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetConsReciNFe>) result.getContent().get(0)).getValue();
        }
    }
    
    public TRetConsReciNFe getTRetConsReciNFeSPNFE(final String versao, final String numeroRecibo, final DFAmbiente ambiente, final SSLSocketFactory socketFactory) throws JAXBException, Exception {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nferetautorizacao4.sp.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nferetautorizacao4.sp.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTConsReciNFe(versao, numeroRecibo, ambiente));

            br.inf.portalfiscal.nfe.wsdl.nferetautorizacao4.sp.NFeRetAutorizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nferetautorizacao4.sp.NFeRetAutorizacao4().getNFeRetAutorizacao4Soap12();
            ((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory", socketFactory);
            br.inf.portalfiscal.nfe.wsdl.nferetautorizacao4.sp.NfeResultMsg result = port.nfeRetAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetConsReciNFe>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nferetautorizacao4.sp.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nferetautorizacao4.sp.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTConsReciNFe(versao, numeroRecibo, ambiente));

            br.inf.portalfiscal.nfe.wsdl.nferetautorizacao4.sp.hom.NFeRetAutorizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nferetautorizacao4.sp.hom.NFeRetAutorizacao4().getNFeRetAutorizacao4Soap12();
            ((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory", socketFactory);
            br.inf.portalfiscal.nfe.wsdl.nferetautorizacao4.sp.hom.NfeResultMsg result = port.nfeRetAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetConsReciNFe>) result.getContent().get(0)).getValue();
        }
    }

    private JAXBElement<TConsReciNFe> getTConsReciNFe(final String versao, final String numeroRecibo, final DFAmbiente ambiente) throws JAXBException {
        TConsReciNFe tConsReciNFe = new TConsReciNFe();
        tConsReciNFe.setNRec(numeroRecibo);
        tConsReciNFe.setTpAmb(ambiente.getCodigo());
        tConsReciNFe.setVersao(versao);

        JAXBElement<TConsReciNFe> element = (JAXBElement<TConsReciNFe>) new ObjectFactory().createConsReciNFe(tConsReciNFe);

        return element;
    }

}
