package com.fincatto.documentofiscal.mdfe3.webservices;

import com.fincatto.documentofiscal.mdfe3.MDFeConfig;
import com.fincatto.documentofiscal.mdfe3.classes.nota.evento.MDFeDetalhamentoEvento;
import com.fincatto.documentofiscal.mdfe3.classes.nota.evento.MDFeEvento;
import com.fincatto.documentofiscal.mdfe3.classes.nota.evento.MDFeInfoEvento;
import com.fincatto.documentofiscal.mdfe3.classes.nota.evento.MDFeRetorno;
import com.fincatto.documentofiscal.mdfe3.classes.nota.evento.cancelamento.MDFeEnviaEventoCancelamento;
import com.fincatto.documentofiscal.mdfe3.classes.parsers.MDFChaveParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * Created by Eldevan Nery Junior on 17/11/17.
 */
class WSCancelamento {
    private static final String DESCRICAO_EVENTO = "Cancelamento";
    private static final BigDecimal VERSAO_LEIAUTE = new BigDecimal("3.00");
    private static final String EVENTO_CANCELAMENTO = "110111";
    private static final Logger LOGGER = LoggerFactory.getLogger(WSCancelamento.class);
    private final MDFeConfig config;

    WSCancelamento(final MDFeConfig config) {
        this.config = config;
    }

    MDFeRetorno cancelaNotaAssinada(final String chaveAcesso, final String eventoAssinadoXml) throws Exception {
        throw new UnsupportedOperationException("Nao suportado ainda");
    }

    MDFeRetorno cancelaNota(final String chaveAcesso, final String numeroProtocolo, final String motivo) throws Exception {
        throw new UnsupportedOperationException("Nao suportado ainda");
    }

    private String efetuaCancelamento(final String xmlAssinado, final String chaveAcesso) throws Exception {
        throw new UnsupportedOperationException("Nao suportado ainda");
    }

    private MDFeEvento gerarDadosCancelamento(final String chaveAcesso, final String numeroProtocolo, final String motivo) {
        final MDFChaveParser chaveParser = new MDFChaveParser(chaveAcesso);

        final MDFeEnviaEventoCancelamento cancelamento = new MDFeEnviaEventoCancelamento();
        cancelamento.setDescricaoEvento(WSCancelamento.DESCRICAO_EVENTO);
        cancelamento.setJustificativa(motivo.trim());
        cancelamento.setProtocoloAutorizacao(numeroProtocolo);

        MDFeDetalhamentoEvento mdFeDetalhamentoEvento = new MDFeDetalhamentoEvento();
        mdFeDetalhamentoEvento.setMdFeEnviaEventoCancelamento(cancelamento);
        mdFeDetalhamentoEvento.setVersaoEvento(WSCancelamento.VERSAO_LEIAUTE);

        final MDFeInfoEvento infoEvento = new MDFeInfoEvento();
        infoEvento.setAmbiente(this.config.getAmbiente());
        infoEvento.setChave(chaveAcesso);
        infoEvento.setCnpj(chaveParser.getCnpjEmitente());
        infoEvento.setDataHoraEvento(ZonedDateTime.now(this.config.getTimeZone().toZoneId()));
        infoEvento.setId(String.format("ID%s%s0%s", WSCancelamento.EVENTO_CANCELAMENTO, chaveAcesso, "1"));
        infoEvento.setNumeroSequencialEvento(1);
        infoEvento.setOrgao(chaveParser.getNFUnidadeFederativa().getCodigoIbge());
        infoEvento.setCodigoEvento(WSCancelamento.EVENTO_CANCELAMENTO);
        infoEvento.setDetEvento(mdFeDetalhamentoEvento);

        MDFeEvento mdfeEventoCancelamento = new MDFeEvento();
        mdfeEventoCancelamento.setInfoEvento(infoEvento);
        mdfeEventoCancelamento.setVersao(WSCancelamento.VERSAO_LEIAUTE);

        return mdfeEventoCancelamento;
    }
}
