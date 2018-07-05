package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.ObjectFactory;
import br.inf.portalfiscal.nfe.TEnvEvento;
import br.inf.portalfiscal.nfe.TEvento;
import br.inf.portalfiscal.nfe.TRetEnvEvento;
import com.fincatto.documentofiscal.DFUnidadeFederativa;
import com.fincatto.documentofiscal.assinatura.AssinaturaDigital;
import com.fincatto.documentofiscal.nfe.NFeConfig;
import com.fincatto.documentofiscal.nfe400.classes.evento.NFEnviaEventoRetorno;
import com.fincatto.documentofiscal.nfe400.parsers.NotaFiscalChaveParser;
import java.io.StringWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

class WSEvento {
    private static final String EVENTO_CANCELAMENTO = "110111";
    private static final BigDecimal VERSAO_LEIAUTE = new BigDecimal("1.00");
    private static final Logger LOGGER = LoggerFactory.getLogger(WSEvento.class);
    private final NFeConfig config;

    WSEvento(final NFeConfig config) {
        this.config = config;
    }

    NFEnviaEventoRetorno cancelaNotaAssinada(final String chaveAcesso, final String eventoAssinadoXml) throws Exception {
//        final OMElement omElementResult = this.efetuaCancelamento(eventoAssinadoXml, chaveAcesso);
//        return new DFPersister().read(NFEnviaEventoRetorno.class, omElementResult.toString());
        return null;
    }

    TRetEnvEvento enviaEvento(final String descEvento, final String tpEvento, final String chaveAcesso, final String numeroProtocolo, final String motivo) throws Exception {
        String xml = this.gerarDados(descEvento, tpEvento, chaveAcesso, numeroProtocolo, motivo);
        xml = xml.replace("xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", "");
        final String xmlAssinado = new AssinaturaDigital(this.config).assinarDocumento(xml);
        return efetua(tpEvento, xmlAssinado, chaveAcesso);
    }

    private TRetEnvEvento efetua(final String tpEvento, final String xml, final String chaveAcesso) throws Exception {
        final NotaFiscalChaveParser chaveParser = new NotaFiscalChaveParser(chaveAcesso);
        if (tpEvento.equals(EVENTO_CANCELAMENTO)) {
            return com.fincatto.documentofiscal.nfe400.webservices.GatewayEvento.valueOfCodigoUF(chaveParser.getNFUnidadeFederativa()).getTRetEnvEvento(chaveParser.getModelo(), xml, this.config.getAmbiente());
        } else {
            return com.fincatto.documentofiscal.nfe400.webservices.GatewayEvento.AN.getTRetEnvEvento(chaveParser.getModelo(), xml, this.config.getAmbiente());
        }
    }

    private String gerarDados(final String descEvento, final String tpEvento, final String chaveAcesso, final String numeroProtocolo, final String motivo) throws JAXBException {
        final NotaFiscalChaveParser chaveParser = new NotaFiscalChaveParser(chaveAcesso);

        TEvento.InfEvento.DetEvento detEvento = new TEvento.InfEvento.DetEvento();
        detEvento.setDescEvento(descEvento);
        detEvento.setNProt(numeroProtocolo);
        detEvento.setXJust(motivo);
        detEvento.setVersao(WSEvento.VERSAO_LEIAUTE.toString());
        
        final TEvento.InfEvento infoEvento = new TEvento.InfEvento();
        infoEvento.setTpAmb(this.config.getAmbiente().getCodigo());
        infoEvento.setChNFe(chaveAcesso);
        infoEvento.setCNPJ(chaveParser.getCnpjEmitente());
        infoEvento.setDhEvento(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").format(LocalDateTime.now())); //TODO
        infoEvento.setDhEvento(infoEvento.getDhEvento() + "-03:00");
        infoEvento.setId(String.format("ID%s%s0%s", tpEvento, chaveAcesso, "1"));
        infoEvento.setNSeqEvento("1");
        if (tpEvento.equals(EVENTO_CANCELAMENTO)) {
            infoEvento.setCOrgao(chaveParser.getNFUnidadeFederativa().getCodigoIbge());
        } else {
            infoEvento.setCOrgao(DFUnidadeFederativa.RFB.getCodigoIbge());
        }
        infoEvento.setTpEvento(tpEvento);
        infoEvento.setVerEvento(WSEvento.VERSAO_LEIAUTE.toString());
        infoEvento.setDetEvento(detEvento);

        final TEvento evento = new TEvento();
        evento.setInfEvento(infoEvento);
        evento.setVersao(WSEvento.VERSAO_LEIAUTE.toString());

        final TEnvEvento enviaEvento = new TEnvEvento();
        enviaEvento.getEvento().add(evento);
        enviaEvento.setIdLote(Long.toString(ZonedDateTime.now(this.config.getTimeZone().toZoneId()).toInstant().toEpochMilli()));
        enviaEvento.setVersao(WSEvento.VERSAO_LEIAUTE.toString());
        
        JAXBContext context = JAXBContext.newInstance(TEnvEvento.class);
        Marshaller marshaller = context.createMarshaller();

        JAXBElement<TEnvEvento> tEnvEvento = new ObjectFactory().createEnvEvento(enviaEvento);
        
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(tEnvEvento, stringWriter);
        return stringWriter.toString();
    }
}