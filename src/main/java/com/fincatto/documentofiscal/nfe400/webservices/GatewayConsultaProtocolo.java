package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.TConsSitNFe;
import br.inf.portalfiscal.nfe.TRetConsSitNFe;
import com.fincatto.documentofiscal.DFAmbiente;
import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.DFUnidadeFederativa;
import java.util.Arrays;
import javax.xml.bind.JAXBElement;

public enum GatewayConsultaProtocolo {
    
    SVRS {
        @Override
        public TRetConsSitNFe getTRetConsSitNFe(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFModelo modelo, final DFAmbiente ambiente) throws Exception {
            return DFModelo.NFE.equals(modelo) ?getTRetConsSitNFeSVRS(tConsSitNFe, ambiente) : getTRetConsSitNFCeSVRS(tConsSitNFe, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{
                DFUnidadeFederativa.PI
            };
        }

    };
    
    public abstract TRetConsSitNFe getTRetConsSitNFe(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFModelo modelo, final DFAmbiente ambiente) throws Exception;

    public abstract DFUnidadeFederativa[] getUFs();

    public static GatewayConsultaProtocolo valueOfCodigoUF(final DFUnidadeFederativa uf) {
        for (final GatewayConsultaProtocolo autorizador : GatewayConsultaProtocolo.values()) {
            if (Arrays.asList(autorizador.getUFs()).contains(uf)) {
                return autorizador;
            }
        }
        throw new IllegalStateException(String.format("N\u00e3o existe metodo de envio para a UF %s", uf.getCodigo()));
    }
    
    public TRetConsSitNFe getTRetConsSitNFeSVRS(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFAmbiente ambiente) {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.svrs.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.svrs.NfeDadosMsg();
            nfeDadosMsg.getContent().add(tConsSitNFe);

            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.svrs.NFeConsultaProtocolo4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.svrs.NFeConsultaProtocolo4().getNFeConsultaProtocolo4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.svrs.NfeResultMsg result = port.nfeConsultaNF(nfeDadosMsg);

            return null;
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.svrs.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.svrs.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(tConsSitNFe);

            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.svrs.hom.NFeConsultaProtocolo4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.svrs.hom.NFeConsultaProtocolo4().getNFeConsultaProtocolo4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.svrs.hom.NfeResultMsg result = port.nfeConsultaNF(nfeDadosMsg);

            return ((JAXBElement<TRetConsSitNFe>) result.getContent().get(0)).getValue();
        }
    }
    
    public TRetConsSitNFe getTRetConsSitNFCeSVRS(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFAmbiente ambiente) {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.svrs.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.svrs.NfeDadosMsg();
            nfeDadosMsg.getContent().add(tConsSitNFe);

            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.svrs.NFeConsultaProtocolo4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.svrs.NFeConsultaProtocolo4().getNFeConsultaProtocolo4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.svrs.NfeResultMsg result = port.nfeConsultaNF(nfeDadosMsg);

            return ((JAXBElement<TRetConsSitNFe>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.svrs.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.svrs.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(tConsSitNFe);

            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.svrs.hom.NFeConsultaProtocolo4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.svrs.hom.NFeConsultaProtocolo4().getNFeConsultaProtocolo4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.svrs.hom.NfeResultMsg result = port.nfeConsultaNF(nfeDadosMsg);

            return ((JAXBElement<TRetConsSitNFe>) result.getContent().get(0)).getValue();
        }
    }
    
}