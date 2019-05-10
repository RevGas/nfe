package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.TInutNFe;
import br.inf.portalfiscal.nfe.TRetInutNFe;
import com.fincatto.documentofiscal.DFAmbiente;
import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.DFUnidadeFederativa;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.Arrays;

public enum GatewayInutilizacao {

    BA {
        @Override
        public TRetInutNFe getTRetInutNFe(DFModelo modelo, String xml, DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetInutNFeBANFE(xml, ambiente) : SVRS.getTRetInutNFeSVRSNFCE(xml, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.BA};
        }

    },
    CE {
        @Override
        public TRetInutNFe getTRetInutNFe(DFModelo modelo, String xml, DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetInutNFeCENFE(xml, ambiente) : getTRetInutNFeCENFCE(xml, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.CE};
        }

    },
    PE {
        @Override
        public TRetInutNFe getTRetInutNFe(DFModelo modelo, String xml, DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetInutNFePENFE(xml, ambiente) : SVRS.getTRetInutNFeSVRSNFCE(xml, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.PE};
        }

    },
    PR {
        @Override
        public TRetInutNFe getTRetInutNFe(DFModelo modelo, String xml, DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetInutNFePRNFE(xml, ambiente) : getTRetInutNFePRNFCE(xml, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.PR};
        }

    },
    SVRS {
        @Override
        public TRetInutNFe getTRetInutNFe(final DFModelo modelo, final String xml, final DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetInutNFeSVRSNFE(xml, ambiente) : getTRetInutNFeSVRSNFCE(xml, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]
                {
                    DFUnidadeFederativa.AC, DFUnidadeFederativa.AL, DFUnidadeFederativa.AP, DFUnidadeFederativa.DF,
                    DFUnidadeFederativa.ES, DFUnidadeFederativa.PB, DFUnidadeFederativa.PI, DFUnidadeFederativa.RJ,
                    DFUnidadeFederativa.RN, DFUnidadeFederativa.RO, DFUnidadeFederativa.RR, DFUnidadeFederativa.SC,
                    DFUnidadeFederativa.SE, DFUnidadeFederativa.TO
                };
        }
    };

    public abstract TRetInutNFe getTRetInutNFe(final DFModelo modelo, final String xml, final DFAmbiente ambiente) throws JAXBException, Exception;

    public abstract DFUnidadeFederativa[] getUFs();

    public static GatewayInutilizacao valueOfCodigoUF(final DFUnidadeFederativa uf) {
        for (final GatewayInutilizacao autorizador : GatewayInutilizacao.values()) {
            if (Arrays.asList(autorizador.getUFs()).contains(uf)) {
                return autorizador;
            }
        }
        throw new IllegalStateException(String.format("N\u00e3o existe metodo de inutilizacao para a UF %s", uf.getCodigo()));
    }
    
    public TRetInutNFe getTRetInutNFeBANFE(String xml, DFAmbiente ambiente) throws JAXBException {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.ba.NfeDadosMsg dadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.ba.NfeDadosMsg();
            dadosMsg.getContent().add(getTInutNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.ba.NFeInutilizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.ba.NFeInutilizacao4().getNFeInutilizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.ba.NfeResultMsg result = port.nfeInutilizacaoNF(dadosMsg);

            return ((JAXBElement<TRetInutNFe>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.ba.hom.NfeDadosMsg dadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.ba.hom.NfeDadosMsg();
            dadosMsg.getContent().add(getTInutNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.ba.hom.NFeInutilizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.ba.hom.NFeInutilizacao4().getNFeInutilizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.ba.hom.NfeResultMsg result = port.nfeInutilizacaoNF(dadosMsg);

            return ((JAXBElement<TRetInutNFe>) result.getContent().get(0)).getValue();
        }
    }
    
    public TRetInutNFe getTRetInutNFeCENFE(String xml, DFAmbiente ambiente) throws JAXBException {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.ce.NfeDadosMsg dadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.ce.NfeDadosMsg();
            dadosMsg.getContent().add(getTInutNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.ce.NFeInutilizacaoSoap port = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.ce.NFeInutilizacao4().getNFeInutilizacaoSoap12();
            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.ce.NfeResultMsg result = port.nfeInutilizacaoNF(dadosMsg);

            return ((JAXBElement<TRetInutNFe>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.ce.hom.NfeDadosMsg dadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.ce.hom.NfeDadosMsg();
            dadosMsg.getContent().add(getTInutNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.ce.hom.NFeInutilizacaoSoap port = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.ce.hom.NFeInutilizacao4().getNFeInutilizacaoSoap12();
            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.ce.hom.NfeResultMsg result = port.nfeInutilizacaoNF(dadosMsg);

            return ((JAXBElement<TRetInutNFe>) result.getContent().get(0)).getValue();
        }
    }
    
    public TRetInutNFe getTRetInutNFeCENFCE(String xml, DFAmbiente ambiente) throws JAXBException {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.ce.NfeDadosMsg dadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.ce.NfeDadosMsg();
            dadosMsg.getContent().add(getTInutNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.ce.NFeInutilizacaoSoap port = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.ce.NFeInutilizacao4().getNFeInutilizacaoSoap12();
            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.ce.NfeResultMsg result = port.nfeInutilizacaoNF(dadosMsg);

            return ((JAXBElement<TRetInutNFe>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.ce.hom.NfeDadosMsg dadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.ce.hom.NfeDadosMsg();
            dadosMsg.getContent().add(getTInutNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.ce.hom.NFeInutilizacaoSoap port = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.ce.hom.NFeInutilizacao4().getNFeInutilizacaoSoap12();
            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.ce.hom.NfeResultMsg result = port.nfeInutilizacaoNF(dadosMsg);

            return ((JAXBElement<TRetInutNFe>) result.getContent().get(0)).getValue();
        }
    }
    
    public TRetInutNFe getTRetInutNFePENFE(String xml, DFAmbiente ambiente) throws JAXBException {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.pe.NfeDadosMsg dadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.pe.NfeDadosMsg();
            dadosMsg.getContent().add(getTInutNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.pe.NFeInutilizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.pe.NFeInutilizacao4().getNFeInutilizacao4ServicePort();
            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.pe.NfeResultMsg result = port.nfeInutilizacaoNF(dadosMsg);

            return ((JAXBElement<TRetInutNFe>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.pe.hom.NfeDadosMsg dadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.pe.hom.NfeDadosMsg();
            dadosMsg.getContent().add(getTInutNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.pe.hom.NFeInutilizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.pe.hom.NFeInutilizacao4().getNFeInutilizacao4ServicePort();
            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.pe.hom.NfeResultMsg result = port.nfeInutilizacaoNF(dadosMsg);

            return ((JAXBElement<TRetInutNFe>) result.getContent().get(0)).getValue();
        }
    }
    
    public TRetInutNFe getTRetInutNFePRNFE(String xml, DFAmbiente ambiente) throws JAXBException {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.pr.NfeDadosMsg dadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.pr.NfeDadosMsg();
            dadosMsg.getContent().add(getTInutNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.pr.NFeInutilizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.pr.NFeInutilizacao4().getNFeInutilizacao4ServicePort();
            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.pr.NfeResultMsg result = port.nfeInutilizacaoNF(dadosMsg);

            return ((JAXBElement<TRetInutNFe>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.pr.hom.NfeDadosMsg dadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.pr.hom.NfeDadosMsg();
            dadosMsg.getContent().add(getTInutNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.pr.hom.NFeInutilizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.pr.hom.NFeInutilizacao4().getNFeInutilizacao4ServicePort();
            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.pr.hom.NfeResultMsg result = port.nfeInutilizacaoNF(dadosMsg);

            return ((JAXBElement<TRetInutNFe>) result.getContent().get(0)).getValue();
        }
    }

    public TRetInutNFe getTRetInutNFePRNFCE(String xml, DFAmbiente ambiente) throws JAXBException {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.pr.NfeDadosMsg dadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.pr.NfeDadosMsg();
            dadosMsg.getContent().add(getTInutNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.pr.NFeInutilizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.pr.NFeInutilizacao4().getNFeInutilizacao4ServicePort();
            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.pr.NfeResultMsg result = port.nfeInutilizacaoNF(dadosMsg);

            return ((JAXBElement<TRetInutNFe>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.pr.hom.NfeDadosMsg dadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.pr.hom.NfeDadosMsg();
            dadosMsg.getContent().add(getTInutNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.pr.hom.NFeInutilizacao4Soap12 port = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.pr.hom.NFeInutilizacao4().getNFeInutilizacao4ServicePort();
            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.pr.hom.NfeResultMsg result = port.nfeInutilizacaoNF(dadosMsg);

            return ((JAXBElement<TRetInutNFe>) result.getContent().get(0)).getValue();
        }
    }
    
    public TRetInutNFe getTRetInutNFeSVRSNFE(String xml, DFAmbiente ambiente) throws JAXBException {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.svrs.NfeDadosMsg dadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.svrs.NfeDadosMsg();
            dadosMsg.getContent().add(getTInutNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.svrs.NFeInutilizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.svrs.NFeInutilizacao4().getNFeInutilizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.svrs.NfeResultMsg result = port.nfeInutilizacaoNF(dadosMsg);

            return ((JAXBElement<TRetInutNFe>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.svrs.hom.NfeDadosMsg dadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.svrs.hom.NfeDadosMsg();
            dadosMsg.getContent().add(getTInutNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.svrs.hom.NFeInutilizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.svrs.hom.NFeInutilizacao4().getNFeInutilizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.svrs.hom.NfeResultMsg result = port.nfeInutilizacaoNF(dadosMsg);

            return ((JAXBElement<TRetInutNFe>) result.getContent().get(0)).getValue();
        }
    }

    public TRetInutNFe getTRetInutNFeSVRSNFCE(String xml, DFAmbiente ambiente) throws JAXBException {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            final br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.svrs.NfeDadosMsg dadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.svrs.NfeDadosMsg();
            dadosMsg.getContent().add(getTInutNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.svrs.NFeInutilizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.svrs.NFeInutilizacao4().getNFeInutilizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.svrs.NfeResultMsg result = port.nfeInutilizacaoNF(dadosMsg);

            return ((JAXBElement<TRetInutNFe>) result.getContent().get(0)).getValue();
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.svrs.hom.NfeDadosMsg dadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.svrs.hom.NfeDadosMsg();
            dadosMsg.getContent().add(getTInutNFe(xml));

            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.svrs.hom.NFeInutilizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.svrs.hom.NFeInutilizacao4().getNFeInutilizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.nfce.svrs.hom.NfeResultMsg result = port.nfeInutilizacaoNF(dadosMsg);

            return ((JAXBElement<TRetInutNFe>) result.getContent().get(0)).getValue();
        }
    }

    private JAXBElement<TInutNFe> getTInutNFe(String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.nfe");

        Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(xml);
        JAXBElement<TInutNFe> tInutNFe = (JAXBElement<TInutNFe>) jaxbUnmarshaller.unmarshal(reader);

        return tInutNFe;
    }

}