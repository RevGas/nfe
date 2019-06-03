package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.model.evento_manifesta_destinatario.Evento_ManifestaDest_PL_v101.ObjectFactory;
import br.inf.portalfiscal.nfe.model.evento_manifesta_destinatario.Evento_ManifestaDest_PL_v101.TEnvEvento;
import br.inf.portalfiscal.nfe.model.evento_manifesta_destinatario.Evento_ManifestaDest_PL_v101.TEvento;
import br.inf.portalfiscal.nfe.model.evento_manifesta_destinatario.Evento_ManifestaDest_PL_v101.TRetEnvEvento;
import com.fincatto.documentofiscal.DFLog;
import com.fincatto.documentofiscal.DFUnidadeFederativa;
import com.fincatto.documentofiscal.nfe.NFeConfig;
import com.fincatto.documentofiscal.nfe400.NotaFiscalChaveParser;
import com.fincatto.documentofiscal.utils.DFAssinaturaDigital;
import java.io.StringWriter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class WSManifestacaoDestinatario implements DFLog {
    
    private static final BigDecimal VERSAO_LEIAUTE = new BigDecimal("1.00");
    private final NFeConfig config;
    
    public WSManifestacaoDestinatario(final NFeConfig config) {
        this.config = config;
    }

    TRetEnvEvento manifestaDestinatarioNota(final String chNFe, final String descEvento, final String tpEvento, final String xJust, final String CNPJ) throws Exception {
        String xml = this.gerarDadosManifestacaoDestinatario(chNFe, descEvento, tpEvento, xJust, CNPJ);
        xml = xml.replace("xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", "");
        final String xmlAssinado = new DFAssinaturaDigital(this.config).assinarDocumento(xml);
        return efetuaManifestacaoDestinatario(xmlAssinado, chNFe);
    }

    private br.inf.portalfiscal.nfe.model.evento_manifesta_destinatario.Evento_ManifestaDest_PL_v101.TRetEnvEvento efetuaManifestacaoDestinatario(final String xml, final String chaveAcesso) throws Exception {
        final NotaFiscalChaveParser chaveParser = new NotaFiscalChaveParser(chaveAcesso);
        return com.fincatto.documentofiscal.nfe400.webservices.GatewayManifestaDestinatario.AN.getTRetEnvEvento(chaveParser.getModelo(), xml, this.config.getAmbiente());
    }

    private String gerarDadosManifestacaoDestinatario(final String chNFe, final String descEvento, final String tpEvento, final String xJust, final String CNPJ) throws JAXBException {
        TEvento.InfEvento.DetEvento detEvento = new TEvento.InfEvento.DetEvento();
        detEvento.setVersao(WSManifestacaoDestinatario.VERSAO_LEIAUTE.toString());
        detEvento.setDescEvento(descEvento);
        detEvento.setXJust(xJust);
        
        
        final TEvento.InfEvento infoEvento = new TEvento.InfEvento();
        infoEvento.setTpAmb(this.config.getAmbiente().getCodigo());
        infoEvento.setChNFe(chNFe);
        infoEvento.setCNPJ(CNPJ);
        infoEvento.setDhEvento(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").format(LocalDateTime.now())); //TODO
        infoEvento.setDhEvento(infoEvento.getDhEvento() + "-03:00");
        infoEvento.setId(String.format("ID%s%s0%s", tpEvento, chNFe, "1"));
        infoEvento.setNSeqEvento("1");
        infoEvento.setCOrgao(DFUnidadeFederativa.RFB.getCodigoIbge());
        infoEvento.setTpEvento(tpEvento);
        infoEvento.setVerEvento(WSManifestacaoDestinatario.VERSAO_LEIAUTE.toString());
        infoEvento.setDetEvento(detEvento);

        final TEvento evento = new TEvento();
        evento.setInfEvento(infoEvento);
        evento.setVersao(WSManifestacaoDestinatario.VERSAO_LEIAUTE.toString());

        final TEnvEvento enviaEvento = new TEnvEvento();
        enviaEvento.getEvento().add(evento);
        enviaEvento.setIdLote(Long.toString(ZonedDateTime.now(this.config.getTimeZone().toZoneId()).toInstant().toEpochMilli()));
        enviaEvento.setVersao(WSManifestacaoDestinatario.VERSAO_LEIAUTE.toString());

        JAXBContext context = JAXBContext.newInstance(TEnvEvento.class);
        Marshaller marshaller = context.createMarshaller();

        JAXBElement<TEnvEvento> tEnvEvento = new ObjectFactory().createEnvEvento(enviaEvento);

        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(tEnvEvento, stringWriter);
        return stringWriter.toString();
    }

}
