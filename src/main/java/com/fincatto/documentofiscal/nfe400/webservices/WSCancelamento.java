package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.model.evento_cancelamento.Evento_Canc_PL_v101.ObjectFactory;
import br.inf.portalfiscal.nfe.model.evento_cancelamento.Evento_Canc_PL_v101.TEnvEvento;
import br.inf.portalfiscal.nfe.model.evento_cancelamento.Evento_Canc_PL_v101.TEvento;
import br.inf.portalfiscal.nfe.model.evento_cancelamento.Evento_Canc_PL_v101.TProcEvento;
import br.inf.portalfiscal.nfe.model.evento_cancelamento.Evento_Canc_PL_v101.TRetEnvEvento;
import com.fincatto.documentofiscal.DFLog;
import com.fincatto.documentofiscal.nfe.NFeConfig;
import com.fincatto.documentofiscal.nfe400.NotaFiscalChaveParser;
import com.fincatto.documentofiscal.utils.DFAssinaturaDigital;
import java.io.StringReader;
import java.io.StringWriter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

class WSCancelamento implements DFLog {
    
    private static final String DESCRICAO_EVENTO = "Cancelamento";
    private static final BigDecimal VERSAO_LEIAUTE = new BigDecimal("1.00");
    private static final String EVENTO_CANCELAMENTO = "110111";
    private final NFeConfig config;
    
    WSCancelamento(final NFeConfig config) {
        this.config = config;
    }

    String cancelaNotaAssinada(final String chaveAcesso, final String eventoAssinadoXml) throws Exception {
//        final OMElement omElementResult = this.efetuaCancelamento(eventoAssinadoXml, chaveAcesso);
//        return new DFPersister().read(NFEnviaEventoRetorno.class, omElementResult.toString());
        return null;
    }

    TProcEvento cancelaNota(final String chNFe, final String nProt, final String xJust) throws Exception {
        String xml = this.gerarDados(chNFe, nProt, xJust);
        xml = xml.replace("xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", "");
        final String xmlAssinado = new DFAssinaturaDigital(this.config).assinarDocumento(xml);
        
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.nfe.model.evento_cancelamento.Evento_Canc_PL_v101");
        Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(xmlAssinado);
        JAXBElement<TEnvEvento> tEnvEvento = (JAXBElement<TEnvEvento>) jaxbUnmarshaller.unmarshal(reader);
       
        TProcEvento procEvento = new TProcEvento();
        procEvento.setEvento(tEnvEvento.getValue().getEvento().get(0));
        
        TRetEnvEvento retEnvEvento;
        try {
            retEnvEvento = efetuaCancelamento(xmlAssinado, chNFe);
            procEvento.setRetEvento(retEnvEvento.getRetEvento().get(0));
            procEvento.setVersao(VERSAO_LEIAUTE.toString());
        } catch (Exception e) {
            DFLog.getLogger(WSCancelamento.class);
        }
        return procEvento;
    }

    private TRetEnvEvento efetuaCancelamento(final String xmlAssinado, final String chaveAcesso) throws Exception {
        final NotaFiscalChaveParser chaveParser = new NotaFiscalChaveParser(chaveAcesso);
        return com.fincatto.documentofiscal.nfe400.webservices.GatewayCancelamento.valueOfCodigoUF(chaveParser.getNFUnidadeFederativa()).getTRetEnvEvento(chaveParser.getModelo(), xmlAssinado, this.config.getAmbiente());
    }

    private String gerarDados(final String chaveAcesso, final String numeroProtocolo, final String motivo) throws JAXBException {
        final NotaFiscalChaveParser chaveParser = new NotaFiscalChaveParser(chaveAcesso);

        TEvento.InfEvento.DetEvento detEvento = new TEvento.InfEvento.DetEvento();
        detEvento.setVersao(WSCancelamento.VERSAO_LEIAUTE.toString());
        detEvento.setDescEvento(WSCancelamento.DESCRICAO_EVENTO);
        detEvento.setNProt(numeroProtocolo);
        detEvento.setXJust(motivo);

        final TEvento.InfEvento infoEvento = new TEvento.InfEvento();
        infoEvento.setTpAmb(this.config.getAmbiente().getCodigo());
        infoEvento.setChNFe(chaveAcesso);
        infoEvento.setCNPJ(chaveParser.getCnpjEmitente());
        infoEvento.setDhEvento(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").format(LocalDateTime.now())); //TODO
        infoEvento.setDhEvento(infoEvento.getDhEvento() + "-03:00");
        infoEvento.setId(String.format("ID%s%s0%s", WSCancelamento.EVENTO_CANCELAMENTO, chaveAcesso, "1"));
        infoEvento.setNSeqEvento("1");
        infoEvento.setCOrgao(chaveParser.getNFUnidadeFederativa().getCodigoIbge());
        infoEvento.setTpEvento(WSCancelamento.EVENTO_CANCELAMENTO);
        infoEvento.setVerEvento(WSCancelamento.VERSAO_LEIAUTE.toString());
        infoEvento.setDetEvento(detEvento);

        final TEvento evento = new TEvento();
        evento.setInfEvento(infoEvento);
        evento.setVersao(WSCancelamento.VERSAO_LEIAUTE.toString());

        final TEnvEvento enviaEvento = new TEnvEvento();
        enviaEvento.getEvento().add(evento);
        enviaEvento.setIdLote(Long.toString(ZonedDateTime.now(this.config.getTimeZone().toZoneId()).toInstant().toEpochMilli()));
        enviaEvento.setVersao(WSCancelamento.VERSAO_LEIAUTE.toString());

        JAXBContext context = JAXBContext.newInstance(TEnvEvento.class);
        Marshaller marshaller = context.createMarshaller();

        JAXBElement<TEnvEvento> tEnvEvento = new ObjectFactory().createEnvEvento(enviaEvento);

        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(tEnvEvento, stringWriter);
        return stringWriter.toString();
    }
}
