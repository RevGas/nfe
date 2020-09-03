package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.TEnviNFe;
import br.inf.portalfiscal.nfe.TRetEnviNFe;
import com.fincatto.documentofiscal.DFAmbiente;
import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.DFUnidadeFederativa;
import com.fincatto.documentofiscal.S3;
import com.fincatto.documentofiscal.nfe.NFTipoEmissao;
import com.fincatto.documentofiscal.utils.Util;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.BindingProvider;

public enum GatewayLoteEnvio {

    AM {
        @Override
        public TRetEnviNFe getTRetEnviNFe(DFModelo modelo, String xml, DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetEnviNFeAMNFE(xml, ambiente) : getTRetEnviNFeAMNFCE(xml, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.AM};
        }
    },
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
    CE {
        @Override
        public TRetEnviNFe getTRetEnviNFe(DFModelo modelo, String xml, DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetEnviNFeCENFE(xml, ambiente) : getTRetEnviNFeCENFCE(xml, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.CE};
        }
    },
    GO {
        @Override
        public TRetEnviNFe getTRetEnviNFe(DFModelo modelo, String xml, DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetEnviNFeGONFE(xml, ambiente) : getTRetEnviNFeGONFCE(xml, ambiente);
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
    MG {
        @Override
        public TRetEnviNFe getTRetEnviNFe(final DFModelo modelo, final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetEnviNFeMGNFE(xml, ambiente) : getTRetEnviNFeMGNFCE(xml, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.MG};
        }
    },
    MS {
        @Override
        public TRetEnviNFe getTRetEnviNFe(final DFModelo modelo, final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetEnviNFeMSNFE(xml, ambiente) : getTRetEnviNFeMSNFCE(xml, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.MS};
        }
    },
    MT {
        @Override
        public TRetEnviNFe getTRetEnviNFe(final DFModelo modelo, final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetEnviNFeMTNFE(xml, ambiente) : getTRetEnviNFeMTNFCE(xml, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.MT};
        }
    },
    PE {
        @Override
        public TRetEnviNFe getTRetEnviNFe(DFModelo modelo, String xml, DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetEnviNFePENFE(xml, ambiente) : SVRS.getTRetEnviNFeSVRSNFCE(xml, ambiente);
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
            return new DFUnidadeFederativa[]{};
        }
    },
    SVCAN {
        @Override
        public TRetEnviNFe getTRetEnviNFe(final DFModelo modelo, final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetEnviNFeSVCANNFE(xml, ambiente) : null;
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{};
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
                    DFUnidadeFederativa.ES, DFUnidadeFederativa.PA, DFUnidadeFederativa.PB, DFUnidadeFederativa.PI,
                    DFUnidadeFederativa.RJ, DFUnidadeFederativa.RN, DFUnidadeFederativa.RO, DFUnidadeFederativa.RR,
                    DFUnidadeFederativa.SC, DFUnidadeFederativa.SE, DFUnidadeFederativa.TO
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

    public static GatewayLoteEnvio valueOfTipoEmissao(final NFTipoEmissao tpEmissao, final DFUnidadeFederativa uf) {
        switch (tpEmissao) {
            case CONTIGENCIA_OFFLINE:
            case CONTINGENCIA_FSDA:
            case EMISSAO_NORMAL:
                return GatewayLoteEnvio.valueOfCodigoUF(uf);
            case CONTINGENCIA_SVCAN:
                return GatewayLoteEnvio.SVCAN;
            case CONTINGENCIA_SVCRS:
                return GatewayLoteEnvio.SVRS;
            default:
                throw new IllegalArgumentException("N\u00e3o ha implementac\u00e3o para o tipo de emiss\u00e3o: " + tpEmissao.getDescricao());
        }
    }

    public TRetEnviNFe getTRetEnviNFeAMNFE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        Object retorno;
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.am.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.am.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.am.NfeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.am.NfeAutorizacao4().getNfeAutorizacao4Soap12();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.am.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        } else {
            return null;
        }
        sendRetEnviNFe(retorno);
        return ((JAXBElement<TRetEnviNFe>) retorno).getValue();
    }

    public TRetEnviNFe getTRetEnviNFeAMNFCE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        Object retorno;
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.am.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.am.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.am.NfeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.am.NfeAutorizacao4().getNfeAutorizacao4Soap12();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.am.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        } else {
            return null;
        }
        sendRetEnviNFe(retorno);
        return ((JAXBElement<TRetEnviNFe>) retorno).getValue();
    }

    public TRetEnviNFe getTRetEnviNFeBANFE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        Object retorno;
        TEnviNFe enviNFe = (TEnviNFe) Util.unmarshler(TEnviNFe.class, xml);
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ba.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ba.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ba.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ba.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ba.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ba.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ba.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ba.hom.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ba.hom.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ba.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        }
        sendRetEnviNFe(retorno, Util.chaveFromTNFe(enviNFe.getNFe().get(0)));
        return ((JAXBElement<TRetEnviNFe>) retorno).getValue();
    }

    public TRetEnviNFe getTRetEnviNFeCENFE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        Object retorno;
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ce.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ce.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ce.NFeAutorizacaoSoap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ce.NFeAutorizacao4().getNFeAutorizacaoSoap12();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ce.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ce.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ce.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ce.hom.NFeAutorizacaoSoap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ce.hom.NFeAutorizacao4().getNFeAutorizacaoSoap12();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ce.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        }
        sendRetEnviNFe(retorno);
        return ((JAXBElement<TRetEnviNFe>) retorno).getValue();
    }

    public TRetEnviNFe getTRetEnviNFeCENFCE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        Object retorno;
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.ce.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.ce.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.ce.NFeAutorizacaoSoap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.ce.NFeAutorizacao4().getNFeAutorizacaoSoap12();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.ce.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.ce.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.ce.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.ce.hom.NFeAutorizacaoSoap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.ce.hom.NFeAutorizacao4().getNFeAutorizacaoSoap12();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.ce.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        }
        sendRetEnviNFe(retorno);
        return ((JAXBElement<TRetEnviNFe>) retorno).getValue();
    }

    public TRetEnviNFe getTRetEnviNFeGONFE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        Object retorno;
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.go.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.go.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.go.NFeAutorizacao4Service port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.go.NFeAutorizacao4().getNFeAutorizacao4Port();
            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "https://nfe.sefaz.go.gov.br/nfe/services/NFeAutorizacao4");
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.go.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.go.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.go.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.go.hom.NFeAutorizacao4Service port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.go.hom.NFeAutorizacao4().getNFeAutorizacao4Port();
            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "https://homolog.sefaz.go.gov.br/nfe/services/NFeAutorizacao4");
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.go.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        }
        sendRetEnviNFe(retorno);
        return ((JAXBElement<TRetEnviNFe>) retorno).getValue();
    }

    public TRetEnviNFe getTRetEnviNFeGONFCE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        Object retorno;
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.go.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.go.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.go.NFeAutorizacao4Service port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.go.NFeAutorizacao4().getNFeAutorizacao4Port();
            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "https://nfe.sefaz.go.gov.br/nfe/services/NFeAutorizacao4");
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.go.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        } else {
            return null;
        }
        sendRetEnviNFe(retorno);
        return ((JAXBElement<TRetEnviNFe>) retorno).getValue();
    }

    public TRetEnviNFe getTRetEnviNFeMGNFE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        Object retorno;
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.mg.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.mg.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.mg.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.mg.NFeAutorizacao4().getNFeAutorizacao4Soap12();
            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "https://nfe.fazenda.mg.gov.br/nfe2/services/NFeAutorizacao4");
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.mg.NFeAutorizacao4LoteResult result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getRetEnviNFe().get(0);
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.mg.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.mg.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.mg.hom.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.mg.hom.NFeAutorizacao4().getNFeAutorizacao4Soap12();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.mg.hom.NFeAutorizacao4LoteResult result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getRetEnviNFe().get(0);
        }
        sendRetEnviNFe(retorno);
        return ((JAXBElement<TRetEnviNFe>) retorno).getValue();
    }

    public TRetEnviNFe getTRetEnviNFeMGNFCE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        Object retorno;
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.mg.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.mg.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.mg.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.mg.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.mg.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.mg.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.mg.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.mg.hom.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.mg.hom.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.mg.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        }
        sendRetEnviNFe(retorno);
        return ((JAXBElement<TRetEnviNFe>) retorno).getValue();
    }

    public TRetEnviNFe getTRetEnviNFeMSNFE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        Object retorno;
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ms.NfeResultMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ms.NfeResultMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ms.NFeAutorizacaoSoap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ms.NFeAutorizacao4().getNfeAutorizacaoSoap12();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ms.NfeResultMsg2 result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ms.hom.NfeResultMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ms.hom.NfeResultMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ms.hom.NFeAutorizacaoSoap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ms.hom.NFeAutorizacao4().getNfeAutorizacaoSoap12();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.ms.hom.NfeResultMsg2 result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        }
        sendRetEnviNFe(retorno);
        return ((JAXBElement<TRetEnviNFe>) retorno).getValue();
    }

    public TRetEnviNFe getTRetEnviNFeMSNFCE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        Object retorno;
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.ms.NfeResultMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.ms.NfeResultMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.ms.NFeAutorizacaoSoap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.ms.NFeAutorizacao4().getNfeAutorizacaoSoap12();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.ms.NfeResultMsg2 result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.ms.hom.NfeResultMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.ms.hom.NfeResultMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.ms.hom.NFeAutorizacaoSoap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.ms.hom.NFeAutorizacao4().getNfeAutorizacaoSoap12();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.ms.hom.NfeResultMsg2 result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        }
        sendRetEnviNFe(retorno);
        return ((JAXBElement<TRetEnviNFe>) retorno).getValue();
    }

    public TRetEnviNFe getTRetEnviNFeMTNFE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        Object retorno;
        TEnviNFe enviNFe = (TEnviNFe) Util.unmarshler(TEnviNFe.class, xml);
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.mt.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.mt.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.mt.NfeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.mt.NfeAutorizacao4().getNfeAutorizacao4Soap12();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.mt.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.mt.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.mt.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.mt.hom.NfeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.mt.hom.NfeAutorizacao4().getNfeAutorizacao4Soap12();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.mt.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        }
        sendRetEnviNFe(retorno, Util.chaveFromTNFe(enviNFe.getNFe().get(0)));
        return ((JAXBElement<TRetEnviNFe>) retorno).getValue();
    }

    public TRetEnviNFe getTRetEnviNFeMTNFCE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        Object retorno;
        TEnviNFe enviNFe = (TEnviNFe) Util.unmarshler(TEnviNFe.class, xml);
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.mt.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.mt.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.mt.NfeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.mt.NfeAutorizacao4().getNfeAutorizacao4Soap();
            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "https://nfce.sefaz.mt.gov.br/nfcews/services/NfeAutorizacao4");
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.mt.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.mt.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.mt.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.mt.hom.NfeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.mt.hom.NfeAutorizacao4().getNfeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.mt.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        }
        sendRetEnviNFe(retorno, Util.chaveFromTNFe(enviNFe.getNFe().get(0)));
        return ((JAXBElement<TRetEnviNFe>) retorno).getValue();
    }

    public TRetEnviNFe getTRetEnviNFePENFE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        Object retorno;
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pe.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pe.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pe.NFeAutorizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pe.NFeAutorizacao4().getNFeAutorizacao4ServicePort();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pe.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pe.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pe.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pe.hom.NFeAutorizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pe.hom.NFeAutorizacao4().getNFeAutorizacao4ServicePort();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pe.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        }
        sendRetEnviNFe(retorno);
        return ((JAXBElement<TRetEnviNFe>) retorno).getValue();
    }

    public TRetEnviNFe getTRetEnviNFePRNFE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        Object retorno;
        TEnviNFe enviNFe = (TEnviNFe) Util.unmarshler(TEnviNFe.class, xml);
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pr.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pr.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pr.NFeAutorizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pr.NFeAutorizacao4().getNFeAutorizacao4ServicePort();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pr.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pr.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pr.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pr.hom.NFeAutorizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pr.hom.NFeAutorizacao4().getNFeAutorizacao4ServicePort();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.pr.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        }
        sendRetEnviNFe(retorno, Util.chaveFromTNFe(enviNFe.getNFe().get(0)));
        return ((JAXBElement<TRetEnviNFe>) retorno).getValue();
    }

    public TRetEnviNFe getTRetEnviNFePRNFCE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        Object retorno;
        TEnviNFe enviNFe = (TEnviNFe) Util.unmarshler(TEnviNFe.class, xml);
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.pr.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.pr.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.pr.NFeAutorizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.pr.NFeAutorizacao4().getNFeAutorizacao4ServicePort();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.pr.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.pr.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.pr.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.pr.hom.NFeAutorizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.pr.hom.NFeAutorizacao4().getNFeAutorizacao4ServicePort();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.pr.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        }
        sendRetEnviNFe(retorno, Util.chaveFromTNFe(enviNFe.getNFe().get(0)));
        return ((JAXBElement<TRetEnviNFe>) retorno).getValue();
    }

    public TRetEnviNFe getTRetEnviNFeRSNFE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        Object retorno;
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.rs.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.rs.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.rs.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.rs.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.rs.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.rs.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.rs.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.rs.hom.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.rs.hom.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.rs.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        }
        sendRetEnviNFe(retorno);
        return ((JAXBElement<TRetEnviNFe>) retorno).getValue();
    }

    public TRetEnviNFe getTRetEnviNFeRSNFCE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        Object retorno;
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.rs.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.rs.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.rs.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.rs.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.rs.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.rs.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.rs.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.rs.hom.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.rs.hom.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.rs.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        }
        sendRetEnviNFe(retorno);
        return ((JAXBElement<TRetEnviNFe>) retorno).getValue();
    }

    public TRetEnviNFe getTRetEnviNFeSPNFE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        Object retorno;
        TEnviNFe enviNFe = (TEnviNFe) Util.unmarshler(TEnviNFe.class, xml);
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.sp.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.sp.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.sp.NFeAutorizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.sp.NFeAutorizacao4().getNFeAutorizacao4Soap12();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.sp.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.sp.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.sp.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.sp.hom.NFeAutorizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.sp.hom.NFeAutorizacao4().getNFeAutorizacao4Soap12();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.sp.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        }
        sendRetEnviNFe(retorno, Util.chaveFromTNFe(enviNFe.getNFe().get(0)));
        return ((JAXBElement<TRetEnviNFe>) retorno).getValue();
    }

    public TRetEnviNFe getTRetEnviNFeSPNFCE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        Object retorno;
        TEnviNFe enviNFe = (TEnviNFe) Util.unmarshler(TEnviNFe.class, xml);
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.sp.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.sp.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.sp.NFeAutorizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.sp.NFeAutorizacao4().getNFeAutorizacao4Soap12();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.sp.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.sp.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.sp.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.sp.hom.NFeAutorizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.sp.hom.NFeAutorizacao4().getNFeAutorizacao4Soap12();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.sp.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        }
        sendRetEnviNFe(retorno, Util.chaveFromTNFe(enviNFe.getNFe().get(0)));
        return ((JAXBElement<TRetEnviNFe>) retorno).getValue();
    }

    public TRetEnviNFe getTRetEnviNFeSVANNFE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        Object retorno;
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.NfeAutorizacaoLoteResult result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.hom.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.hom.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.hom.NfeAutorizacaoLoteResult result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        }
        sendRetEnviNFe(retorno);
        return ((JAXBElement<TRetEnviNFe>) retorno).getValue();
    }

    public TRetEnviNFe getTRetEnviNFeSVANNFCE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            return null;
        } else {
            return null;
        }
    }

    public TRetEnviNFe getTRetEnviNFeSVCANNFE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        Object retorno;
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svcan.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svcan.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svcan.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svcan.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svcan.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svcan.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svcan.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svcan.hom.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svcan.hom.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svcan.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        }
        sendRetEnviNFe(retorno);
        return ((JAXBElement<TRetEnviNFe>) retorno).getValue();
    }

    public TRetEnviNFe getTRetEnviNFeSVRSNFE(final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
        Object retorno;
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svrs.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svrs.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svrs.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svrs.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svrs.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svrs.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svrs.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svrs.hom.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svrs.hom.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svrs.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        }

        sendRetEnviNFe(retorno);
        return ((JAXBElement<TRetEnviNFe>) retorno).getValue();
    }

    public TRetEnviNFe getTRetEnviNFeSVRSNFCE(String xml, DFAmbiente ambiente) throws JAXBException, IOException {
        Object retorno;
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.svrs.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.svrs.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.svrs.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.svrs.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.svrs.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.svrs.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.svrs.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.svrs.hom.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.svrs.hom.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.svrs.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            retorno = result.getContent().get(0);
        }
        sendRetEnviNFe(retorno);
        return ((JAXBElement<TRetEnviNFe>) retorno).getValue();
    }

    public static JAXBElement<TEnviNFe> getTEnviNFe(String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.nfe");
        Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(xml);
        JAXBElement<TEnviNFe> tEnviNFe = (JAXBElement<TEnviNFe>) jaxbUnmarshaller.unmarshal(reader);
        return tEnviNFe;
    }

    public static void sendRetEnviNFe(Object retorno) throws JAXBException, IOException {
        new S3().sendRetEnviNFe(Util.marshllerRetEnviNFe((JAXBElement<TRetEnviNFe>) retorno), ((JAXBElement<TRetEnviNFe>) retorno).getValue()); //Tentar enviar para o S3
    }

    public static void sendRetEnviNFe(Object retorno, String chaveNFe) throws JAXBException, IOException {
        new S3().sendRetEnviNFe(Util.marshllerRetEnviNFe((JAXBElement<TRetEnviNFe>) retorno), ((JAXBElement<TRetEnviNFe>) retorno).getValue(), chaveNFe); //Tentar enviar para o S3
    }

}
