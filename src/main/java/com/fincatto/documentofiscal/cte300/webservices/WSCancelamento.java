package com.fincatto.documentofiscal.cte300.webservices;

import com.fincatto.documentofiscal.cte300.CTeConfig;
import com.fincatto.documentofiscal.cte300.classes.evento.cancelamento.CTeDetalhamentoEventoCancelamento;
import com.fincatto.documentofiscal.cte300.classes.evento.cancelamento.CTeEnviaEventoCancelamento;
import com.fincatto.documentofiscal.cte300.classes.evento.cancelamento.CTeEventoCancelamento;
import com.fincatto.documentofiscal.cte300.classes.evento.cancelamento.CTeInfoEventoCancelamento;
import com.fincatto.documentofiscal.cte300.classes.evento.cancelamento.CTeProtocoloEventoCancelamento;
import com.fincatto.documentofiscal.cte300.classes.evento.cancelamento.CTeRetornoCancelamento;
import com.fincatto.documentofiscal.cte300.parsers.CTChaveParser;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

class WSCancelamento {
    private static final String DESCRICAO_EVENTO = "Cancelamento";
    private static final BigDecimal VERSAO_LEIAUTE = new BigDecimal("3.00");
    private static final String EVENTO_CANCELAMENTO = "110111";
    private static final Logger LOGGER = LoggerFactory.getLogger(WSCancelamento.class);
    private final CTeConfig config;

    WSCancelamento(final CTeConfig config) {
        this.config = config;
    }

    CTeRetornoCancelamento cancelaNotaAssinada(final String chaveAcesso, final String eventoAssinadoXml) throws Exception {
        throw new UnsupportedOperationException("Nao suportado ainda");
    }

    CTeRetornoCancelamento cancelaNota(final String chaveAcesso, final String numeroProtocolo, final String motivo) throws Exception {
        throw new UnsupportedOperationException("Nao suportado ainda");
    }

    private String efetuaCancelamento(final String xmlAssinado, final String chaveAcesso) throws Exception {
        throw new UnsupportedOperationException("Nao suportado ainda");
    }

    private CTeProtocoloEventoCancelamento gerarDadosCancelamento(final String chaveAcesso, final String numeroProtocolo, final String motivo) {
        final CTChaveParser chaveParser = new CTChaveParser(chaveAcesso);
        final CTeEnviaEventoCancelamento cancelamento = new CTeEnviaEventoCancelamento();
        cancelamento.setDescricaoEvento(WSCancelamento.DESCRICAO_EVENTO);
        cancelamento.setJustificativa(motivo);
        cancelamento.setProtocoloAutorizacao(numeroProtocolo);
        CTeDetalhamentoEventoCancelamento cTeDetalhamentoEventoCancelamento = new CTeDetalhamentoEventoCancelamento();
        cTeDetalhamentoEventoCancelamento.setVersaoEvento(WSCancelamento.VERSAO_LEIAUTE);
        cTeDetalhamentoEventoCancelamento.setEventoCancelamento(cancelamento);
//        cancelamento.setVersaoEvento(WSCancelamento.VERSAO_LEIAUTE);
        final CTeInfoEventoCancelamento infoEvento = new CTeInfoEventoCancelamento();
        infoEvento.setAmbiente(this.config.getAmbiente());
        infoEvento.setChave(chaveAcesso);
        infoEvento.setCnpj(chaveParser.getCnpjEmitente());
        infoEvento.setDataHoraEvento(DateTime.now());
        infoEvento.setId(String.format("ID%s%s0%s", WSCancelamento.EVENTO_CANCELAMENTO, chaveAcesso, "1"));
        infoEvento.setNumeroSequencialEvento(1);
        infoEvento.setOrgao(chaveParser.getNFUnidadeFederativa());
        infoEvento.setCodigoEvento(WSCancelamento.EVENTO_CANCELAMENTO);
//        infoEvento.setVersaoEvento(WSCancelamento.VERSAO_LEIAUTE);
        infoEvento.setCancelamento(cTeDetalhamentoEventoCancelamento);

        CTeEventoCancelamento cTeEventoCancelamento = new CTeEventoCancelamento();
        cTeEventoCancelamento.setInfoEvento(infoEvento);
        cTeEventoCancelamento.setVersao(WSCancelamento.VERSAO_LEIAUTE);

        CTeProtocoloEventoCancelamento cTeProtocoloEventoCancelamento = new CTeProtocoloEventoCancelamento();
        cTeProtocoloEventoCancelamento.setVersao(WSCancelamento.VERSAO_LEIAUTE);
        cTeProtocoloEventoCancelamento.setEvento(cTeEventoCancelamento);
//        cTeProtocoloEventoCancelamento.setEventoRetorno();

        return cTeProtocoloEventoCancelamento;
    }
}
