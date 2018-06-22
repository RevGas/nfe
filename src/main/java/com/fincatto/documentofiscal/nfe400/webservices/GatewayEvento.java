package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.TEnvEvento;
import br.inf.portalfiscal.nfe.TRetEnvEvento;
import com.fincatto.documentofiscal.DFAmbiente;
import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.DFUnidadeFederativa;
import java.io.StringReader;
import java.util.Arrays;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public enum GatewayEvento {

    AN {
        @Override
        public TRetEnvEvento getTRetEnvEvento(final DFModelo modelo, final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetEnvEventoANNFE(xml, ambiente) : getTRetEnvEventoANNFCE(xml, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{};
        }
    },
    BA {
        @Override
        public TRetEnvEvento getTRetEnvEvento(DFModelo modelo, String loteAssinado, DFAmbiente ambiente) throws JAXBException, Exception {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.BA};
        }
        
    },
    MA {
        @Override
        public TRetEnvEvento getTRetEnvEvento(final DFModelo modelo, final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? SVAN.getTRetEnvEvento(modelo, xml, ambiente) : SVRS.getTRetEnvEvento(modelo, xml, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.MA};
        }
    },
    PR {
        @Override
        public TRetEnvEvento getTRetEnvEvento(DFModelo modelo, String loteAssinado, DFAmbiente ambiente) throws JAXBException, Exception {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.PR};
        }
        
    },
    RS {
        @Override
        public TRetEnvEvento getTRetEnvEvento(DFModelo modelo, String loteAssinado, DFAmbiente ambiente) throws JAXBException, Exception {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.RS};
        }
        
    },
    SP {
        @Override
        public TRetEnvEvento getTRetEnvEvento(DFModelo modelo, String loteAssinado, DFAmbiente ambiente) throws JAXBException, Exception {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.SP};
        }
        
    },
    SVAN {
        @Override
        public TRetEnvEvento getTRetEnvEvento(final DFModelo modelo, final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetEnvEventoSVANNFE(xml, ambiente) : getTRetEnvEventoSVANNFCE(xml, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.PA};
        }
    },
    SVRS {
        @Override
        public TRetEnvEvento getTRetEnvEvento(final DFModelo modelo, final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetEnvEventoSVRSNFE(xml, ambiente) : getTRetEnvEventoSVRSNFCE(xml, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.PI};
        }
    };

    public abstract TRetEnvEvento getTRetEnvEvento(final DFModelo modelo, final String loteAssinado, final DFAmbiente ambiente) throws JAXBException, Exception;

    public abstract DFUnidadeFederativa[] getUFs();

    public static GatewayEvento valueOfCodigoUF(final DFUnidadeFederativa uf) {
        for (final GatewayEvento autorizador : GatewayEvento.values()) {
            if (Arrays.asList(autorizador.getUFs()).contains(uf)) {
                return autorizador;
            }
        }
        throw new IllegalStateException(String.format("N\u00e3o existe metodo de envio para a UF %s", uf.getCodigo()));
    }

    public TRetEnvEvento getTRetEnvEventoANNFE(String xml, DFAmbiente ambiente) throws JAXBException {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.an.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.an.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnvEvento(xml));

            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.an.NFeRecepcaoEvento4Soap port = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.an.NFeRecepcaoEvento4().getNFeRecepcaoEvento4Soap();
            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.an.NfeRecepcaoEventoNFResult result = port.nfeRecepcaoEventoNF(nfeDadosMsg);

            return ((JAXBElement<TRetEnvEvento>) result.getContent().get(0)).getValue();
        } else {
            return null;
        }
    }
    
    public TRetEnvEvento getTRetEnvEventoANNFCE(String xml, DFAmbiente ambiente) throws JAXBException {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            return null;
        } else {
            return null;
        }
    }
    
    public TRetEnvEvento getTRetEnvEventoSVANNFE(String xml, DFAmbiente ambiente) throws JAXBException {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.svan.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.svan.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnvEvento(xml));

            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.svan.NFeRecepcaoEvento4Soap port = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.svan.NFeRecepcaoEvento4().getNFeRecepcaoEvento4Soap();
            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.svan.NfeRecepcaoEventoResult result = port.nfeRecepcaoEvento(nfeDadosMsg);

            return ((JAXBElement<TRetEnvEvento>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.svan.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.svan.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnvEvento(xml));

            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.svan.hom.NFeRecepcaoEvento4Soap port = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.svan.hom.NFeRecepcaoEvento4().getNFeRecepcaoEvento4Soap();
            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.svan.hom.NfeRecepcaoEventoResult result = port.nfeRecepcaoEvento(nfeDadosMsg);

            return ((JAXBElement<TRetEnvEvento>) result.getContent().get(0)).getValue();
        }
    }
    
     public TRetEnvEvento getTRetEnvEventoSVANNFCE(String xml, DFAmbiente ambiente) throws JAXBException {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
           return null;
        } else {
           return null;
        }
    }

    public TRetEnvEvento getTRetEnvEventoSVRSNFE(String xml, DFAmbiente ambiente) throws JAXBException {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            return null;
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.svrs.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.svrs.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnvEvento(xml));

            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.svrs.hom.NFeRecepcaoEvento4Soap port = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.svrs.hom.NFeRecepcaoEvento4().getNFeRecepcaoEvento4Soap();
            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.svrs.hom.NfeResultMsg result = port.nfeRecepcaoEvento(nfeDadosMsg);

            return ((JAXBElement<TRetEnvEvento>) result.getContent().get(0)).getValue();
        }
    }

    public TRetEnvEvento getTRetEnvEventoSVRSNFCE(String xml, DFAmbiente ambiente) throws JAXBException {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            return null;
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.nfce.svrs.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.nfce.svrs.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnvEvento(xml));

            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.nfce.svrs.hom.NFeRecepcaoEvento4Soap port = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.nfce.svrs.hom.NFeRecepcaoEvento4().getNFeRecepcaoEvento4Soap();
            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.nfce.svrs.hom.NfeResultMsg result = port.nfeRecepcaoEvento(nfeDadosMsg);

            return ((JAXBElement<TRetEnvEvento>) result.getContent().get(0)).getValue();
        }
    }

    private JAXBElement<TEnvEvento> getTEnvEvento(String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.nfe");

        Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(xml);
        JAXBElement<TEnvEvento> tEnvEvento = (JAXBElement<TEnvEvento>) jaxbUnmarshaller.unmarshal(reader);
        return tEnvEvento;
    }

}
