package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.TConsSitNFe;
import br.inf.portalfiscal.nfe.TRetConsSitNFe;
import com.fincatto.documentofiscal.DFAmbiente;
import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.DFUnidadeFederativa;
import java.util.Arrays;
import javax.xml.bind.JAXBElement;
import javax.xml.ws.BindingProvider;

public enum GatewayConsultaProtocolo {

    AM {
        @Override
        public TRetConsSitNFe getTRetConsSitNFe(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFModelo modelo, final DFAmbiente ambiente) throws Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetConsSitNFeAM(tConsSitNFe, ambiente) : getTRetConsSitNFCeAM(tConsSitNFe, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.AM};
        }
    },
    BA {
        @Override
        public TRetConsSitNFe getTRetConsSitNFe(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFModelo modelo, final DFAmbiente ambiente) throws Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetConsSitNFeBA(tConsSitNFe, ambiente) : getTRetConsSitNFCeSVRS(tConsSitNFe, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.BA};
        }
    },
    CE {
        @Override
        public TRetConsSitNFe getTRetConsSitNFe(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFModelo modelo, final DFAmbiente ambiente) throws Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetConsSitNFeCE(tConsSitNFe, ambiente) : getTRetConsSitNFCeCE(tConsSitNFe, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.CE};
        }
    },
    GO {
        @Override
        public TRetConsSitNFe getTRetConsSitNFe(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFModelo modelo, final DFAmbiente ambiente) throws Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetConsSitNFeGO(tConsSitNFe, ambiente) : getTRetConsSitNFCeGO(tConsSitNFe, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.GO};
        }
    },
    MA {
        @Override
        public TRetConsSitNFe getTRetConsSitNFe(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFModelo modelo, final DFAmbiente ambiente) throws Exception {
            return DFModelo.NFE.equals(modelo) ? SVAN.getTRetConsSitNFeSVAN(tConsSitNFe, ambiente) : SVRS.getTRetConsSitNFCeSVRS(tConsSitNFe, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.MA};
        }
    },
    MG {
        @Override
        public TRetConsSitNFe getTRetConsSitNFe(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFModelo modelo, final DFAmbiente ambiente) throws Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetConsSitNFeMG(tConsSitNFe, ambiente) : getTRetConsSitNFCeMG(tConsSitNFe, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.MG};
        }
    },
    MS {
        @Override
        public TRetConsSitNFe getTRetConsSitNFe(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFModelo modelo, final DFAmbiente ambiente) throws Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetConsSitNFeMS(tConsSitNFe, ambiente) : getTRetConsSitNFCeMS(tConsSitNFe, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.MS};
        }
    },
    MT {
        @Override
        public TRetConsSitNFe getTRetConsSitNFe(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFModelo modelo, final DFAmbiente ambiente) throws Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetConsSitNFeMT(tConsSitNFe, ambiente) : getTRetConsSitNFCeMT(tConsSitNFe, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.MT};
        }
    },
    PE {
        @Override
        public TRetConsSitNFe getTRetConsSitNFe(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFModelo modelo, final DFAmbiente ambiente) throws Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetConsSitNFePE(tConsSitNFe, ambiente) : SVRS.getTRetConsSitNFCeSVRS(tConsSitNFe, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.PE};
        }
    },
    PR {
        @Override
        public TRetConsSitNFe getTRetConsSitNFe(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFModelo modelo, final DFAmbiente ambiente) throws Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetConsSitNFePR(tConsSitNFe, ambiente) : getTRetConsSitNFCePR(tConsSitNFe, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.PR};
        }
    },
    RS {
        @Override
        public TRetConsSitNFe getTRetConsSitNFe(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFModelo modelo, final DFAmbiente ambiente) throws Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetConsSitNFeRS(tConsSitNFe, ambiente) : getTRetConsSitNFCeRS(tConsSitNFe, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.RS};
        }
    },
    SP {
        @Override
        public TRetConsSitNFe getTRetConsSitNFe(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFModelo modelo, final DFAmbiente ambiente) throws Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetConsSitNFeSP(tConsSitNFe, ambiente) : getTRetConsSitNFCeSP(tConsSitNFe, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.SP};
        }
    },
    SVAN {
        @Override
        public TRetConsSitNFe getTRetConsSitNFe(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFModelo modelo, final DFAmbiente ambiente) throws Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetConsSitNFeSVAN(tConsSitNFe, ambiente) : null;
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{};
        }
    },
    SVRS {
        @Override
        public TRetConsSitNFe getTRetConsSitNFe(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFModelo modelo, final DFAmbiente ambiente) throws Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetConsSitNFeSVRS(tConsSitNFe, ambiente) : getTRetConsSitNFCeSVRS(tConsSitNFe, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{
                DFUnidadeFederativa.AC, DFUnidadeFederativa.AL, DFUnidadeFederativa.AP, DFUnidadeFederativa.DF,
                DFUnidadeFederativa.ES, DFUnidadeFederativa.PA, DFUnidadeFederativa.PB, DFUnidadeFederativa.PI,
                DFUnidadeFederativa.RJ, DFUnidadeFederativa.RN, DFUnidadeFederativa.RO, DFUnidadeFederativa.RR,
                DFUnidadeFederativa.SC, DFUnidadeFederativa.SE, DFUnidadeFederativa.TO
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

    //Amazonas
    public TRetConsSitNFe getTRetConsSitNFeAM(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFAmbiente ambiente) {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.am.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.am.NfeDadosMsg();
            nfeDadosMsg.getContent().add(tConsSitNFe);

            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.am.NfeConsulta4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.am.NfeConsulta4().getNfeConsulta4Soap12();
            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.am.NfeResultMsg result = port.nfeConsultaNF(nfeDadosMsg);

            return ((JAXBElement<TRetConsSitNFe>) result.getContent().get(0)).getValue();
        } else {
            return null;
        }
    }

    public TRetConsSitNFe getTRetConsSitNFCeAM(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFAmbiente ambiente) {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.am.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.am.NfeDadosMsg();
            nfeDadosMsg.getContent().add(tConsSitNFe);

            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.am.NfeConsulta4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.am.NfeConsulta4().getNfeConsulta4Soap12();
            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.am.NfeResultMsg result = port.nfeConsultaNF(nfeDadosMsg);

            return ((JAXBElement<TRetConsSitNFe>) result.getContent().get(0)).getValue();
        } else {
            return null;
        }
    }

    //Bahia
    public TRetConsSitNFe getTRetConsSitNFeBA(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFAmbiente ambiente) {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.ba.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.ba.NfeDadosMsg();
            nfeDadosMsg.getContent().add(tConsSitNFe);

            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.ba.NFeConsultaProtocolo4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.ba.NFeConsultaProtocolo4().getNFeConsultaProtocolo4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.ba.NfeResultMsg result = port.nfeConsultaNF(nfeDadosMsg);

            return ((JAXBElement<TRetConsSitNFe>) result.getContent().get(0)).getValue();
        } else {
            return null;
        }
    }

    //Ceará
    public TRetConsSitNFe getTRetConsSitNFeCE(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFAmbiente ambiente) {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.ce.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.ce.NfeDadosMsg();
            nfeDadosMsg.getContent().add(tConsSitNFe);

            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.ce.NFeConsultaProtocoloSoap port = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.ce.NFeConsultaProtocolo4().getNFeConsultaProtocoloSoap12();
            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.ce.NfeResultMsg result = port.nfeConsultaNF(nfeDadosMsg);

            return ((JAXBElement<TRetConsSitNFe>) result.getContent().get(0)).getValue();
        } else {
            return null;
        }
    }

    public TRetConsSitNFe getTRetConsSitNFCeCE(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFAmbiente ambiente) {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            return null;
        } else {
            return null;
        }
    }

    //Goiás
    public TRetConsSitNFe getTRetConsSitNFeGO(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFAmbiente ambiente) {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.go.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.go.NfeDadosMsg();
            nfeDadosMsg.getContent().add(tConsSitNFe);

            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.go.NFeConsultaProtocolo4Service port = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.go.NFeConsultaProtocolo4().getNFeConsultaProtocolo4ServicePort();
            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.go.NfeResultMsg result = port.nfeConsultaNF(nfeDadosMsg);

            return ((JAXBElement<TRetConsSitNFe>) result.getContent().get(0)).getValue();
        } else {
            return null;
        }
    }

    public TRetConsSitNFe getTRetConsSitNFCeGO(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFAmbiente ambiente) {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.go.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.go.NfeDadosMsg();
            nfeDadosMsg.getContent().add(tConsSitNFe);

            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.go.NFeConsultaProtocolo4Service port = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.go.NFeConsultaProtocolo4().getNFeConsultaProtocolo4ServicePort();
            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.go.NfeResultMsg result = port.nfeConsultaNF(nfeDadosMsg);

            return ((JAXBElement<TRetConsSitNFe>) result.getContent().get(0)).getValue();
        } else {
            return null;
        }
    }

    //Minas Gerais
    public TRetConsSitNFe getTRetConsSitNFeMG(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFAmbiente ambiente) {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.mg.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.mg.NfeDadosMsg();
            nfeDadosMsg.getContent().add(tConsSitNFe);

            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.mg.NFeConsultaProtocolo4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.mg.NFeConsultaProtocolo4().getNFeConsultaProtocolo4Soap();
            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "https://nfe.fazenda.mg.gov.br/nfe2/services/NFeConsultaProtocolo4");

            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.mg.NfeResultMsg result = port.nfeConsultaNF(nfeDadosMsg);

            return ((JAXBElement<TRetConsSitNFe>) result.getRetConsSitNFe().get(0)).getValue();
        } else {
            return null;
        }
    }

    public TRetConsSitNFe getTRetConsSitNFCeMG(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFAmbiente ambiente) {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.mg.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.mg.NfeDadosMsg();
            nfeDadosMsg.getContent().add(tConsSitNFe);

            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.mg.NFeConsultaProtocolo4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.mg.NFeConsultaProtocolo4().getNFeConsultaProtocolo4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.mg.NfeResultMsg result = port.nfeConsultaNF(nfeDadosMsg);

            return ((JAXBElement<TRetConsSitNFe>) result.getContent().get(0)).getValue();
        } else {
            return null;
        }
    }

    //Mato Grosso do Sul
    public TRetConsSitNFe getTRetConsSitNFeMS(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFAmbiente ambiente) {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.ms.NfeResultMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.ms.NfeResultMsg();
            nfeDadosMsg.getContent().add(tConsSitNFe);

            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.ms.NFeConsultaProtocoloSoap port = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.ms.NFeConsultaProtocolo4().getNfeConsultaProtocoloSoap12();
            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.ms.NfeResultMsg2 result = port.nfeConsultaNF(nfeDadosMsg);

            return ((JAXBElement<TRetConsSitNFe>) result.getContent().get(0)).getValue();
        } else {
            return null;
        }
    }

    public TRetConsSitNFe getTRetConsSitNFCeMS(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFAmbiente ambiente) {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.ms.NfeResultMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.ms.NfeResultMsg();
            nfeDadosMsg.getContent().add(tConsSitNFe);

            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.ms.NFeConsultaProtocoloSoap port = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.ms.NFeConsultaProtocolo4().getNfeConsultaProtocoloSoap12();
            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.ms.NfeResultMsg2 result = port.nfeConsultaNF(nfeDadosMsg);

            return ((JAXBElement<TRetConsSitNFe>) result.getContent().get(0)).getValue();
        } else {
            return null;
        }
    }

    //Mato Grosso
    public TRetConsSitNFe getTRetConsSitNFeMT(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFAmbiente ambiente) {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.mt.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.mt.NfeDadosMsg();
            nfeDadosMsg.getContent().add(tConsSitNFe);

            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.mt.NfeConsulta4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.mt.NfeConsulta4().getNfeConsulta4Soap();
            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "https://nfe.sefaz.mt.gov.br/nfews/v2/services/NfeConsulta4");
            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.mt.NfeResultMsg result = port.nfeConsultaNF(nfeDadosMsg);

            return ((JAXBElement<TRetConsSitNFe>) result.getContent().get(0)).getValue();
        } else {
            return null;
        }
    }

    public TRetConsSitNFe getTRetConsSitNFCeMT(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFAmbiente ambiente) {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.mt.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.mt.NfeDadosMsg();
            nfeDadosMsg.getContent().add(tConsSitNFe);

            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.mt.NfeConsulta4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.mt.NfeConsulta4().getNfeConsulta4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.mt.NfeResultMsg result = port.nfeConsultaNF(nfeDadosMsg);

            return ((JAXBElement<TRetConsSitNFe>) result.getContent().get(0)).getValue();
        } else {
            return null;
        }
    }

    //Pernambuco
    public TRetConsSitNFe getTRetConsSitNFePE(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFAmbiente ambiente) {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.pe.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.pe.NfeDadosMsg();
            nfeDadosMsg.getContent().add(tConsSitNFe);

            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.pe.NFeConsultaProtocolo4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.pe.NFeConsultaProtocolo4().getNFeConsultaProtocolo4ServicePort();
            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.pe.NfeResultMsg result = port.nfeConsultaNF(nfeDadosMsg);

            return ((JAXBElement<TRetConsSitNFe>) result.getContent().get(0)).getValue();
        } else {
            return null;
        }
    }

    //Paraná
    public TRetConsSitNFe getTRetConsSitNFePR(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFAmbiente ambiente) {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.pr.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.pr.NfeDadosMsg();
            nfeDadosMsg.getContent().add(tConsSitNFe);

            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.pr.NFeConsultaProtocolo4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.pr.NFeConsultaProtocolo4().getNFeConsultaProtocolo4ServicePort();
            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.pr.NfeResultMsg result = port.nfeConsultaNF(nfeDadosMsg);

            return ((JAXBElement<TRetConsSitNFe>) result.getContent().get(0)).getValue();
        } else {
            return null;
        }
    }

    public TRetConsSitNFe getTRetConsSitNFCePR(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFAmbiente ambiente) {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.pr.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.pr.NfeDadosMsg();
            nfeDadosMsg.getContent().add(tConsSitNFe);

            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.pr.NFeConsultaProtocolo4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.pr.NFeConsultaProtocolo4().getNFeConsultaProtocolo4ServicePort();
            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.pr.NfeResultMsg result = port.nfeConsultaNF(nfeDadosMsg);

            return ((JAXBElement<TRetConsSitNFe>) result.getContent().get(0)).getValue();
        } else {
            return null;
        }
    }

    //Rio Grande do Sul
    public TRetConsSitNFe getTRetConsSitNFeRS(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFAmbiente ambiente) {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.rs.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.rs.NfeDadosMsg();
            nfeDadosMsg.getContent().add(tConsSitNFe);

            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.rs.NFeConsultaProtocolo4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.rs.NFeConsultaProtocolo4().getNFeConsultaProtocolo4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.rs.NfeResultMsg result = port.nfeConsultaNF(nfeDadosMsg);

            return ((JAXBElement<TRetConsSitNFe>) result.getContent().get(0)).getValue();
        } else {
            return null;
        }
    }

    public TRetConsSitNFe getTRetConsSitNFCeRS(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFAmbiente ambiente) {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.rs.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.rs.NfeDadosMsg();
            nfeDadosMsg.getContent().add(tConsSitNFe);

            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.rs.NFeConsultaProtocolo4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.rs.NFeConsultaProtocolo4().getNFeConsultaProtocolo4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.rs.NfeResultMsg result = port.nfeConsultaNF(nfeDadosMsg);

            return ((JAXBElement<TRetConsSitNFe>) result.getContent().get(0)).getValue();
        } else {
            return null;
        }
    }

    //São Paulo
    public TRetConsSitNFe getTRetConsSitNFeSP(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFAmbiente ambiente) {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.sp.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.sp.NfeDadosMsg();
            nfeDadosMsg.getContent().add(tConsSitNFe);

            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.sp.NFeConsultaProtocolo4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.sp.NFeConsultaProtocolo4().getNFeConsultaProtocolo4Soap12();
            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.sp.NfeResultMsg result = port.nfeConsultaNF(nfeDadosMsg);

            return ((JAXBElement<TRetConsSitNFe>) result.getContent().get(0)).getValue();
        } else {
            return null;
        }
    }

    public TRetConsSitNFe getTRetConsSitNFCeSP(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFAmbiente ambiente) {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.sp.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.sp.NfeDadosMsg();
            nfeDadosMsg.getContent().add(tConsSitNFe);

            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.sp.NFeConsultaProtocolo4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.sp.NFeConsultaProtocolo4().getNFeConsultaProtocolo4Soap12();
            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.nfce.sp.NfeResultMsg result = port.nfeConsultaNF(nfeDadosMsg);

            return ((JAXBElement<TRetConsSitNFe>) result.getContent().get(0)).getValue();
        } else {
            return null;
        }
    }

    //Sistema Virtual do Ambiente Nacional
    public TRetConsSitNFe getTRetConsSitNFeSVAN(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFAmbiente ambiente) {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.svan.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.svan.NfeDadosMsg();
            nfeDadosMsg.getContent().add(tConsSitNFe);
            
            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.svan.NFeConsultaProtocolo4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.svan.NFeConsultaProtocolo4().getNFeConsultaProtocolo4Soap();
            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "https://www.sefazvirtual.fazenda.gov.br/NFeConsultaProtocolo4/NFeConsultaProtocolo4.asmx");
            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.svan.NfeResultMsg result = port.nfeConsultaNF(nfeDadosMsg);

            return ((JAXBElement<TRetConsSitNFe>) result.getContent().get(0)).getValue();
        } else {
            return null;
        }
    }

    //Sistema Virtual do Rio Grande do Sul
    public TRetConsSitNFe getTRetConsSitNFeSVRS(final JAXBElement<TConsSitNFe> tConsSitNFe, final DFAmbiente ambiente) {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.svrs.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.svrs.NfeDadosMsg();
            nfeDadosMsg.getContent().add(tConsSitNFe);

            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.svrs.NFeConsultaProtocolo4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.svrs.NFeConsultaProtocolo4().getNFeConsultaProtocolo4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.svrs.NfeResultMsg result = port.nfeConsultaNF(nfeDadosMsg);

            return ((JAXBElement<TRetConsSitNFe>) result.getContent().get(0)).getValue();
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