package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.TEnviNFe;
import br.inf.portalfiscal.nfe.TRetEnviNFe;
import com.fincatto.documentofiscal.DFAmbiente;
import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.DFUnidadeFederativa;
import com.fincatto.documentofiscal.nfe400.classes.lote.envio.NFLoteEnvio;
import com.fincatto.documentofiscal.nfe400.parsers.DFParser;
import java.io.StringReader;
import java.util.Arrays;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.BindingProvider;

public enum GatewayLoteEnvio {

    BA {
        @Override
        public TRetEnviNFe getTRetEnviNFe(DFModelo modelo, String xml, DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetEnviNFeBANFE(xml, ambiente) : SVRS.getTRetEnviNFeSVRSNFCE(xml, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.BA};
        }
    },
    GO {
        @Override
        public TRetEnviNFe getTRetEnviNFe(DFModelo modelo, String xml, DFAmbiente ambiente) throws JAXBException, Exception {
            return getTRetEnviNFeGONFE(xml, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.GO};
        }
    },
    MA {
        @Override
        public TRetEnviNFe getTRetEnviNFe(final DFModelo modelo, final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? SVAN.getTRetEnviNFe(modelo, xml, ambiente) : SVRS.getTRetEnviNFeSVRSNFCE(xml, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.MA};
        }
    },
    PE {
        @Override
        public TRetEnviNFe getTRetEnviNFe(DFModelo modelo, String xml, DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetEnviNFePENFE(xml, ambiente): SVRS.getTRetEnviNFeSVRSNFCE(xml, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.PE};
        }
    },
    PR {
        @Override
        public TRetEnviNFe getTRetEnviNFe(DFModelo modelo, String xml, DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetEnviNFePRNFE(xml, ambiente) : getTRetEnviNFePRNFCE(xml, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.PR};
        }
    },
    RS {
        @Override
        public TRetEnviNFe getTRetEnviNFe(DFModelo modelo, String xml, DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetEnviNFeRSNFE(xml, ambiente) : getTRetEnviNFeRSNFCE(xml, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.RS};
        }
    },
    SP {
        @Override
        public TRetEnviNFe getTRetEnviNFe(DFModelo modelo, String xml, DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetEnviNFeSPNFE(xml, ambiente) : getTRetEnviNFeSPNFCE(xml, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.SP};
        }
    },
    SVAN {
        @Override
        public TRetEnviNFe getTRetEnviNFe(final DFModelo modelo, final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetEnviNFeSVANNFE(xml, ambiente) : getTRetEnviNFeSVANNFCE(xml, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.PA};
        }
    },
    SVRS {
        @Override
        public TRetEnviNFe getTRetEnviNFe(final DFModelo modelo, final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetEnviNFeSVRSNFE(xml, ambiente) : getTRetEnviNFeSVRSNFCE(xml, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{
                DFUnidadeFederativa.AC, DFUnidadeFederativa.AL, DFUnidadeFederativa.AP, DFUnidadeFederativa.DF,
                DFUnidadeFederativa.ES, DFUnidadeFederativa.PB, DFUnidadeFederativa.PI, DFUnidadeFederativa.RJ,
                DFUnidadeFederativa.RN, DFUnidadeFederativa.RO, DFUnidadeFederativa.RR, DFUnidadeFederativa.SC,
                DFUnidadeFederativa.SE, DFUnidadeFederativa.TO
            };
        }
    };

    public abstract TRetEnviNFe getTRetEnviNFe(final DFModelo modelo, final String xml, final DFAmbiente ambiente) throws JAXBException, Exception;

    public abstract DFUnidadeFederativa[] getUFs();

    public static GatewayLoteEnvio valueOfCodigoUF(final DFUnidadeFederativa uf) {
        for (final GatewayLoteEnvio autorizador : GatewayLoteEnvio.values()) {
            if (Arrays.asList(autorizador.getUFs()).contains(uf)) {
                return autorizador;
            }
        }
        throw new IllegalStateException(String.format("N\u00e3o existe metodo de envio para a UF %s", uf.getCodigo()));
    }

    public TRetEnviNFe getTRetEnviNFeBANFE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ba.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ba.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ba.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ba.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ba.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetEnviNFe>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ba.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ba.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ba.hom.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ba.hom.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ba.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetEnviNFe>) result.getContent().get(0)).getValue();
        }
    }

    public TRetEnviNFe getTRetEnviNFeGONFE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.go.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.go.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.go.NFeAutorizacao4Service port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.go.NFeAutorizacao4().getNFeAutorizacao4Port();
            ((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "https://nfe.sefaz.go.gov.br/nfe/services/NFeAutorizacao4");
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.go.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetEnviNFe>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.go.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.go.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.go.hom.NFeAutorizacao4Service port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.go.hom.NFeAutorizacao4().getNFeAutorizacao4Port();
            ((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "https://homolog.sefaz.go.gov.br/nfe/services/NFeAutorizacao4");
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.go.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetEnviNFe>) result.getContent().get(0)).getValue();
        }
    }

    public TRetEnviNFe getTRetEnviNFePENFE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pe.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pe.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pe.NFeAutorizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pe.NFeAutorizacao4().getNFeAutorizacao4ServicePort();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pe.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetEnviNFe>) result.getContent().get(0)).getValue();
        } else {
            return null;
        }
    }
    
    public TRetEnviNFe getTRetEnviNFePRNFE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pr.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pr.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pr.NFeAutorizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pr.NFeAutorizacao4().getNFeAutorizacao4ServicePort();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pr.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetEnviNFe>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pr.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pr.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pr.hom.NFeAutorizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pr.hom.NFeAutorizacao4().getNFeAutorizacao4ServicePort();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pr.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetEnviNFe>) result.getContent().get(0)).getValue();
        }
    }

    public TRetEnviNFe getTRetEnviNFePRNFCE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.pr.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.pr.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.pr.NFeAutorizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.pr.NFeAutorizacao4().getNFeAutorizacao4ServicePort();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.pr.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetEnviNFe>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.pr.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.pr.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.pr.hom.NFeAutorizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.pr.hom.NFeAutorizacao4().getNFeAutorizacao4ServicePort();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.pr.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetEnviNFe>) result.getContent().get(0)).getValue();
        }
    }

    public TRetEnviNFe getTRetEnviNFeRSNFE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.rs.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.rs.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.rs.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.rs.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.rs.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetEnviNFe>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.rs.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.rs.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.rs.hom.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.rs.hom.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.rs.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetEnviNFe>) result.getContent().get(0)).getValue();
        }
    }

    public TRetEnviNFe getTRetEnviNFeRSNFCE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.rs.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.rs.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.rs.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.rs.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.rs.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetEnviNFe>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.rs.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.rs.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.rs.hom.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.rs.hom.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.rs.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetEnviNFe>) result.getContent().get(0)).getValue();
        }
    }

    public TRetEnviNFe getTRetEnviNFeSPNFE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.sp.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.sp.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.sp.NFeAutorizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.sp.NFeAutorizacao4().getNFeAutorizacao4Soap12();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.sp.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetEnviNFe>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.sp.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.sp.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.sp.hom.NFeAutorizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.sp.hom.NFeAutorizacao4().getNFeAutorizacao4Soap12();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.sp.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetEnviNFe>) result.getContent().get(0)).getValue();
        }
    }

    public TRetEnviNFe getTRetEnviNFeSPNFCE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.sp.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.sp.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.sp.NFeAutorizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.sp.NFeAutorizacao4().getNFeAutorizacao4Soap12();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.sp.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetEnviNFe>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.sp.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.sp.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.sp.hom.NFeAutorizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.sp.hom.NFeAutorizacao4().getNFeAutorizacao4Soap12();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.sp.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetEnviNFe>) result.getContent().get(0)).getValue();
        }
    }

    public TRetEnviNFe getTRetEnviNFeSVANNFE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.NfeAutorizacaoLoteResult result = port.nfeAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetEnviNFe>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.hom.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.hom.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.hom.NfeAutorizacaoLoteResult result = port.nfeAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetEnviNFe>) result.getContent().get(0)).getValue();
        }
    }

    public TRetEnviNFe getTRetEnviNFeSVANNFCE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        NFLoteEnvio loteEnvio = new DFParser().loteParaObjeto(xml);
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            return null;
        } else {
            return null;
        }
    }

    public TRetEnviNFe getTRetEnviNFeSVRSNFE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svrs.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svrs.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svrs.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svrs.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svrs.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetEnviNFe>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svrs.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svrs.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svrs.hom.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svrs.hom.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svrs.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetEnviNFe>) result.getContent().get(0)).getValue();
        }
    }

    public TRetEnviNFe getTRetEnviNFeSVRSNFCE(String xml, DFAmbiente ambiente) throws JAXBException {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.svrs.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.svrs.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.svrs.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.svrs.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.svrs.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetEnviNFe>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.svrs.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.svrs.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.svrs.hom.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.svrs.hom.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.svrs.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetEnviNFe>) result.getContent().get(0)).getValue();
        }
    }

    private JAXBElement<TEnviNFe> getTEnviNFe(String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.nfe");

        Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(xml);
        JAXBElement<TEnviNFe> tEnviNFe = (JAXBElement<TEnviNFe>) jaxbUnmarshaller.unmarshal(reader);
        return tEnviNFe;
    }

}
