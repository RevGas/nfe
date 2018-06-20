package com.fincatto.documentofiscal.nfe400.classes;

import br.inf.portalfiscal.nfe.TEnviNFe;
import br.inf.portalfiscal.nfe.TProtNFe;
import br.inf.portalfiscal.nfe.TRetEnviNFe;
import com.fincatto.documentofiscal.DFAmbiente;
import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.DFUnidadeFederativa;
import com.fincatto.documentofiscal.nfe400.classes.lote.envio.NFLoteEnvioRetorno;
import com.fincatto.documentofiscal.persister.DFPersister;
import com.fincatto.documentofiscal.transformers.DFLocalDateTimeTransformer;
import com.fincatto.nfe310.converters.ElementStringConverter;
import java.io.StringReader;
import java.util.Arrays;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.w3c.dom.Element;

public enum NFLoteEnvio {

    MA {
        @Override
        public TRetEnviNFe getTRetEnviNFe(final DFModelo modelo, final String loteAssinado, final DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? SVAN.getTRetEnviNFe(modelo, loteAssinado, ambiente) : SVRS.getTRetEnviNFe(modelo, loteAssinado, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.MA};
        }
    },
    SVAN {
        @Override
        public TRetEnviNFe getTRetEnviNFe(final DFModelo modelo, final String loteAssinado, final DFAmbiente ambiente) throws JAXBException, Exception {
            return getTRetEnviNFeSVANNFE(loteAssinado);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.PA};
        }
    },
    SVRS {
        @Override
        public TRetEnviNFe getTRetEnviNFe(final DFModelo modelo, final String loteAssinado, final DFAmbiente ambiente) throws JAXBException, Exception {
            return DFModelo.NFE.equals(modelo) ? getTRetEnviNFeSVRSNFE(loteAssinado) : getTRetEnviNFeSVRSNFCE(loteAssinado, ambiente);
        }

        @Override
        public DFUnidadeFederativa[] getUFs() {
            return new DFUnidadeFederativa[]{DFUnidadeFederativa.PI};
        }
    };

    public abstract TRetEnviNFe getTRetEnviNFe(final DFModelo modelo, final String loteAssinado, final DFAmbiente ambiente) throws JAXBException, Exception;

    public abstract DFUnidadeFederativa[] getUFs();

    public static NFLoteEnvio valueOfCodigoUF(final DFUnidadeFederativa uf) {
        for (final NFLoteEnvio autorizador : NFLoteEnvio.values()) {
            if (Arrays.asList(autorizador.getUFs()).contains(uf)) {
                return autorizador;
            }
        }
        throw new IllegalStateException(String.format("N\u00e3o existe metodo de envio para a UF %s", uf.getCodigo()));
    }

    public TRetEnviNFe getTRetEnviNFeSVANNFE(String loteAssinado) throws JAXBException, Exception {
        // Create the JAXBContext
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.nfe");

        final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.hom.NfeDadosMsg();
        nfeDadosMsg.getContent().add(ElementStringConverter.read(loteAssinado));

        br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.hom.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.hom.NFeAutorizacao4().getNFeAutorizacao4Soap();
        br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.hom.NfeAutorizacaoLoteResult result = port.nfeAutorizacaoLote(nfeDadosMsg);
        NFLoteEnvioRetorno nFLoteEnvioRetorno = new DFPersister().read(NFLoteEnvioRetorno.class, ElementStringConverter.write((Element) result.getContent().get(0)));

        TRetEnviNFe tRetEnviNFe = new TRetEnviNFe();
        tRetEnviNFe.setCStat(nFLoteEnvioRetorno.getStatus());
        tRetEnviNFe.setCUF(nFLoteEnvioRetorno.getUf().getCodigo());
        tRetEnviNFe.setDhRecbto(new DFLocalDateTimeTransformer().write(nFLoteEnvioRetorno.getDataRecebimento()));
        TRetEnviNFe.InfRec infRec = new TRetEnviNFe.InfRec();
        infRec.setNRec(nFLoteEnvioRetorno.getInfoRecebimento() == null ? null : nFLoteEnvioRetorno.getInfoRecebimento().getRecibo());
        infRec.setTMed(nFLoteEnvioRetorno.getInfoRecebimento() == null ? null : nFLoteEnvioRetorno.getInfoRecebimento().getTempoMedio());
        tRetEnviNFe.setInfRec(infRec);
        if (nFLoteEnvioRetorno.getProtocoloInfo() != null) {
            TProtNFe tProtNFe = new TProtNFe();
            TProtNFe.InfProt infProt = new TProtNFe.InfProt();
            infProt.setCStat(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : nFLoteEnvioRetorno.getProtocoloInfo().getStatus());
            infProt.setChNFe(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : nFLoteEnvioRetorno.getProtocoloInfo().getChave());
            infProt.setDhRecbto(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : new DFLocalDateTimeTransformer().write(nFLoteEnvioRetorno.getProtocoloInfo().getDataRecebimento()));
            infProt.setDigVal(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : nFLoteEnvioRetorno.getProtocoloInfo().getValidador().getBytes());
            infProt.setId(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : nFLoteEnvioRetorno.getProtocoloInfo().getIdentificador());
            infProt.setNProt(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : nFLoteEnvioRetorno.getProtocoloInfo().getNumeroProtocolo());
            infProt.setTpAmb(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : nFLoteEnvioRetorno.getProtocoloInfo().getAmbiente().getCodigo());
            infProt.setVerAplic(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : nFLoteEnvioRetorno.getProtocoloInfo().getVersaoAplicacao());
            infProt.setXMotivo(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : nFLoteEnvioRetorno.getProtocoloInfo().getMotivo());
            tProtNFe.setInfProt(infProt);
            tProtNFe.setSignature(null);
            tProtNFe.setVersao(nFLoteEnvioRetorno.getVersaoAplicacao());
            tRetEnviNFe.setProtNFe(tProtNFe);
        }
        tRetEnviNFe.setTpAmb(nFLoteEnvioRetorno.getAmbiente().getCodigo());
        tRetEnviNFe.setVerAplic(nFLoteEnvioRetorno.getVersaoAplicacao());
        tRetEnviNFe.setVersao(nFLoteEnvioRetorno.getVersao());
        tRetEnviNFe.setXMotivo(nFLoteEnvioRetorno.getMotivo());

        return tRetEnviNFe;
    }

    public TRetEnviNFe getTRetEnviNFeSVRSNFE(String loteAssinado) throws JAXBException, Exception {
        // Create the JAXBContext
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.nfe");

        final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svrs.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svrs.hom.NfeDadosMsg();
        nfeDadosMsg.getContent().add(ElementStringConverter.read(loteAssinado));

        br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svrs.hom.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svrs.hom.NFeAutorizacao4().getNFeAutorizacao4Soap();
        br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svrs.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
        NFLoteEnvioRetorno nFLoteEnvioRetorno = new DFPersister().read(NFLoteEnvioRetorno.class, ElementStringConverter.write((Element) result.getContent().get(0)));

        TRetEnviNFe tRetEnviNFe = new TRetEnviNFe();
        tRetEnviNFe.setCStat(nFLoteEnvioRetorno.getStatus());
        tRetEnviNFe.setCUF(nFLoteEnvioRetorno.getUf().getCodigo());
        tRetEnviNFe.setDhRecbto(new DFLocalDateTimeTransformer().write(nFLoteEnvioRetorno.getDataRecebimento()));
        TRetEnviNFe.InfRec infRec = new TRetEnviNFe.InfRec();
        infRec.setNRec(nFLoteEnvioRetorno.getInfoRecebimento() == null ? null : nFLoteEnvioRetorno.getInfoRecebimento().getRecibo());
        infRec.setTMed(nFLoteEnvioRetorno.getInfoRecebimento() == null ? null : nFLoteEnvioRetorno.getInfoRecebimento().getTempoMedio());
        tRetEnviNFe.setInfRec(infRec);
        if (nFLoteEnvioRetorno.getProtocoloInfo() != null) {
            TProtNFe tProtNFe = new TProtNFe();
            TProtNFe.InfProt infProt = new TProtNFe.InfProt();
            infProt.setCStat(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : nFLoteEnvioRetorno.getProtocoloInfo().getStatus());
            infProt.setChNFe(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : nFLoteEnvioRetorno.getProtocoloInfo().getChave());
            infProt.setDhRecbto(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : new DFLocalDateTimeTransformer().write(nFLoteEnvioRetorno.getProtocoloInfo().getDataRecebimento()));
            infProt.setDigVal(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : nFLoteEnvioRetorno.getProtocoloInfo().getValidador().getBytes());
            infProt.setId(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : nFLoteEnvioRetorno.getProtocoloInfo().getIdentificador());
            infProt.setNProt(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : nFLoteEnvioRetorno.getProtocoloInfo().getNumeroProtocolo());
            infProt.setTpAmb(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : nFLoteEnvioRetorno.getProtocoloInfo().getAmbiente().getCodigo());
            infProt.setVerAplic(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : nFLoteEnvioRetorno.getProtocoloInfo().getVersaoAplicacao());
            infProt.setXMotivo(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : nFLoteEnvioRetorno.getProtocoloInfo().getMotivo());
            tProtNFe.setInfProt(infProt);
            tProtNFe.setSignature(null);
            tProtNFe.setVersao(nFLoteEnvioRetorno.getVersaoAplicacao());
            tRetEnviNFe.setProtNFe(tProtNFe);
        }
        tRetEnviNFe.setTpAmb(nFLoteEnvioRetorno.getAmbiente().getCodigo());
        tRetEnviNFe.setVerAplic(nFLoteEnvioRetorno.getVersaoAplicacao());
        tRetEnviNFe.setVersao(nFLoteEnvioRetorno.getVersao());
        tRetEnviNFe.setXMotivo(nFLoteEnvioRetorno.getMotivo());

        return tRetEnviNFe;
    }

    public TRetEnviNFe getTRetEnviNFeSVRSNFCE(String loteAssinado, DFAmbiente ambiente) throws JAXBException {
        if (DFAmbiente.PRODUCAO.equals(ambiente)) {
            return null;
        } else {
            final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.svrs.hom.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.svrs.hom.NfeDadosMsg();
            nfeDadosMsg.getContent().add(getTEnviNFe(loteAssinado));

            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.svrs.hom.NFeAutorizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.svrs.hom.NFeAutorizacao4().getNFeAutorizacao4Soap();
            br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.nfce.svrs.hom.NfeResultMsg result = port.nfeAutorizacaoLote(nfeDadosMsg);
            return ((JAXBElement<TRetEnviNFe>) result.getContent().get(0)).getValue();
        }
    }

    private JAXBElement<TEnviNFe> getTEnviNFe(String loteAssinado) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.nfe");

        Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(loteAssinado);
        JAXBElement<TEnviNFe> tEnviNFe = (JAXBElement<TEnviNFe>) jaxbUnmarshaller.unmarshal(reader);
        return tEnviNFe;
    }

}
