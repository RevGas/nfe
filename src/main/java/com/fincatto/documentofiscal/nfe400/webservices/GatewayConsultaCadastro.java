package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.model.consulta_cadastro.PL_006v.TConsCad;
import br.inf.portalfiscal.nfe.model.consulta_cadastro.PL_006v.TRetConsCad;
import com.fincatto.documentofiscal.DFUnidadeFederativa;
import java.util.Arrays;
import javax.net.ssl.SSLSocketFactory;
import javax.xml.bind.JAXBElement;
import javax.xml.ws.BindingProvider;

public enum GatewayConsultaCadastro {
    
    PI {
        @Override
        public TRetConsCad getTRetConsCad(JAXBElement<TConsCad> tConsCad, final String codigoOrgao, final SSLSocketFactory socketFactory) throws Exception {
            return getTRetConsCadPI(tConsCad, codigoOrgao, socketFactory);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{
            };
        }
    },
    SVRS {
        @Override
        public TRetConsCad getTRetConsCad(JAXBElement<TConsCad> tConsCad, final String codigoOrgao, final SSLSocketFactory socketFactory) throws Exception {
            return getTRetConsCadSVRS(tConsCad, codigoOrgao, socketFactory);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{
                DFUnidadeFederativa.AC, DFUnidadeFederativa.RN, 
                DFUnidadeFederativa.PB, DFUnidadeFederativa.SC
            };
        }
    };
    
    public abstract TRetConsCad getTRetConsCad(final JAXBElement<TConsCad> tConsCad, final String codigoOrgao, final SSLSocketFactory socketFactory) throws Exception;

    public abstract DFUnidadeFederativa[] getUFs();

    public static GatewayConsultaCadastro valueOfCodigoUF(final DFUnidadeFederativa uf) {
        for (final GatewayConsultaCadastro autorizador : GatewayConsultaCadastro.values()) {
            if (Arrays.asList(autorizador.getUFs()).contains(uf)) {
                return autorizador;
            }
        }
        throw new IllegalStateException(String.format("N\u00e3o existe metodo de envio para a UF %s", uf.getCodigo()));
    }
    
    public TRetConsCad getTRetConsCadPI(final JAXBElement<TConsCad> tConsCad, final String codigoOrgao, final SSLSocketFactory socketFactory) {
        final br.inf.portalfiscal.nfe.wsdl.cadconsultacadastro2.pi.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.cadconsultacadastro2.pi.NfeDadosMsg();
        nfeDadosMsg.getContent().add(tConsCad);
        
        final br.inf.portalfiscal.nfe.wsdl.cadconsultacadastro2.pi.NfeCabecMsg nfeCabecMsg = new br.inf.portalfiscal.nfe.wsdl.cadconsultacadastro2.pi.NfeCabecMsg();
        nfeCabecMsg.setCUF(tConsCad.getValue().getInfCons().getUF().value());
        nfeCabecMsg.setCodigoOrgao(codigoOrgao);
        nfeCabecMsg.setToken("");

        br.inf.portalfiscal.nfe.wsdl.cadconsultacadastro2.pi.CadConsultaCadastro2_Service port = new br.inf.portalfiscal.nfe.wsdl.cadconsultacadastro2.pi.CadConsultaCadastro2_Service();
        ((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory", socketFactory);
        br.inf.portalfiscal.nfe.wsdl.cadconsultacadastro2.pi.ConsultaCadastro2Result result = port.getCadConsultaCadastro2Port().consultaCadastro2(nfeDadosMsg, nfeCabecMsg);
        
        return ((JAXBElement<TRetConsCad>) result.getContent().get(0)).getValue();
    }
    
    public TRetConsCad getTRetConsCadSVRS(final JAXBElement<TConsCad> tConsCad, final String codigoOrgao, final SSLSocketFactory socketFactory) {
        final br.inf.portalfiscal.nfe.wsdl.cadconsultacadastro4.svrs.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.cadconsultacadastro4.svrs.NfeDadosMsg();
        nfeDadosMsg.getContent().add(tConsCad);

        br.inf.portalfiscal.nfe.wsdl.cadconsultacadastro4.svrs.CadConsultaCadastro4Soap port = new br.inf.portalfiscal.nfe.wsdl.cadconsultacadastro4.svrs.CadConsultaCadastro4().getCadConsultaCadastro4Soap();
        ((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory", socketFactory);
        br.inf.portalfiscal.nfe.wsdl.cadconsultacadastro4.svrs.NfeResultMsg result = port.consultaCadastro(nfeDadosMsg);
        
        return ((JAXBElement<TRetConsCad>) result.getContent().get(0)).getValue();
    }
    
}
