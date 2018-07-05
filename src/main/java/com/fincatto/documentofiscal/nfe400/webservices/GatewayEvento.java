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
        public TRetEnvEvento getTRetEnvEvento(DFModelo modelo, String xml, DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetEnvEventoBANFE(xml, ambiente) : null;
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
        public TRetEnvEvento getTRetEnvEvento(DFModelo modelo, String xml, DFAmbiente ambiente) throws JAXBException, Exception {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.PR};
        }

    },
    RS {
        @Override
        public TRetEnvEvento getTRetEnvEvento(DFModelo modelo, String xml, DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetEnvEventoRSNFE(xml, ambiente) : getTRetEnvEventoRSNFCE(xml, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.RS};
        }

    },
    SP {
        @Override
        public TRetEnvEvento getTRetEnvEvento(DFModelo modelo, String xml, DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetEnvEventoSPNFE(xml, ambiente) : getTRetEnvEventoSPNFCE(xml, ambiente);
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

    public abstract TRetEnvEvento getTRetEnvEvento(final DFModelo modelo, final String xml, final DFAmbiente ambiente) throws JAXBException, Exception;

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
            final br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.an.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.an.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnvEvento(xml));

            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.an.hom.NFeRecepcaoEvento4Soap port = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.an.hom.NFeRecepcaoEvento4().getNFeRecepcaoEvento4Soap();
            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.an.hom.NfeRecepcaoEventoNFResult result = port.nfeRecepcaoEventoNF(nfeDadosMsg);

            return ((JAXBElement<TRetEnvEvento>) result.getContent().get(0)).getValue();
        }
    }
    
    public TRetEnvEvento getTRetEnvEventoANNFCE(String xml, DFAmbiente ambiente) throws JAXBException {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            return null;
        } else {
            return null;
        }
    }
    
    public TRetEnvEvento getTRetEnvEventoBANFE(String xml, DFAmbiente ambiente) throws JAXBException {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.ba.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.ba.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnvEvento(xml));

            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.ba.NFeRecepcaoEvento4Soap port = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.ba.NFeRecepcaoEvento4().getNFeRecepcaoEvento4Soap();
            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.ba.NfeResultMsg result = port.nfeRecepcaoEventoNF(nfeDadosMsg);

            return ((JAXBElement<TRetEnvEvento>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.ba.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.ba.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnvEvento(xml));

            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.ba.hom.NFeRecepcaoEvento4Soap port = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.ba.hom.NFeRecepcaoEvento4().getNFeRecepcaoEvento4Soap();
            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.ba.hom.NfeResultMsg result = port.nfeRecepcaoEventoNF(nfeDadosMsg);

            return ((JAXBElement<TRetEnvEvento>) result.getContent().get(0)).getValue();
        }
    }

    public TRetEnvEvento getTRetEnvEventoRSNFE(String xml, DFAmbiente ambiente) throws JAXBException {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.rs.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.rs.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnvEvento(xml));

            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.rs.NFeRecepcaoEvento4Soap port = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.rs.NFeRecepcaoEvento4().getNFeRecepcaoEvento4Soap();
            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.rs.NfeResultMsg result = port.nfeRecepcaoEvento(nfeDadosMsg);

            return ((JAXBElement<TRetEnvEvento>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.rs.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.rs.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnvEvento(xml));

            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.rs.hom.NFeRecepcaoEvento4Soap port = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.rs.hom.NFeRecepcaoEvento4().getNFeRecepcaoEvento4Soap();
            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.rs.hom.NfeResultMsg result = port.nfeRecepcaoEvento(nfeDadosMsg);

            return ((JAXBElement<TRetEnvEvento>) result.getContent().get(0)).getValue();
        }
    }
    
    public TRetEnvEvento getTRetEnvEventoRSNFCE(String xml, DFAmbiente ambiente) throws JAXBException {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.nfce.rs.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.nfce.rs.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnvEvento(xml));

            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.nfce.rs.NFeRecepcaoEvento4Soap port = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.nfce.rs.NFeRecepcaoEvento4().getNFeRecepcaoEvento4Soap();
            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.nfce.rs.NfeResultMsg result = port.nfeRecepcaoEvento(nfeDadosMsg);

            return ((JAXBElement<TRetEnvEvento>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.nfce.rs.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.nfce.rs.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnvEvento(xml));

            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.nfce.rs.hom.NFeRecepcaoEvento4Soap port = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.nfce.rs.hom.NFeRecepcaoEvento4().getNFeRecepcaoEvento4Soap();
            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.nfce.rs.hom.NfeResultMsg result = port.nfeRecepcaoEvento(nfeDadosMsg);

            return ((JAXBElement<TRetEnvEvento>) result.getContent().get(0)).getValue();
        }
    }
    
    public TRetEnvEvento getTRetEnvEventoSPNFE(String xml, DFAmbiente ambiente) throws JAXBException {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.sp.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.sp.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnvEvento(xml));

            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.sp.NFeRecepcaoEvento4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.sp.NFeRecepcaoEvento4().getNFeRecepcaoEvento4Soap12();
            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.sp.NfeResultMsg result = port.nfeRecepcaoEvento(nfeDadosMsg);

            return ((JAXBElement<TRetEnvEvento>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.sp.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.sp.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnvEvento(xml));

            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.sp.hom.NFeRecepcaoEvento4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.sp.hom.NFeRecepcaoEvento4().getNFeRecepcaoEvento4Soap12();
            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.sp.hom.NfeResultMsg result = port.nfeRecepcaoEvento(nfeDadosMsg);

            return ((JAXBElement<TRetEnvEvento>) result.getContent().get(0)).getValue();
        }
    }

    public TRetEnvEvento getTRetEnvEventoSPNFCE(String xml, DFAmbiente ambiente) throws JAXBException {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.nfce.sp.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.nfce.sp.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnvEvento(xml));

            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.nfce.sp.NFeRecepcaoEvento4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.nfce.sp.NFeRecepcaoEvento4().getNFeRecepcaoEvento4Soap12();
            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.nfce.sp.NfeResultMsg result = port.nfeRecepcaoEventoNF(nfeDadosMsg);

            return ((JAXBElement<TRetEnvEvento>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.nfce.sp.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.nfce.sp.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnvEvento(xml));

            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.nfce.sp.hom.NFeRecepcaoEvento4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.nfce.sp.hom.NFeRecepcaoEvento4().getNFeRecepcaoEvento4Soap12();
            br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.nfce.sp.hom.NfeResultMsg result = port.nfeRecepcaoEventoNF(nfeDadosMsg);

            return ((JAXBElement<TRetEnvEvento>) result.getContent().get(0)).getValue();
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
