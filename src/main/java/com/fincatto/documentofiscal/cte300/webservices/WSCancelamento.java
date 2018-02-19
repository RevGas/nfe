package com.fincatto.documentofiscal.cte300.webservices;

import com.fincatto.documentofiscal.cte300.CTeConfig;
import com.fincatto.documentofiscal.cte300.classes.evento.cancelamento.CTeEnviaEventoCancelamento;
import com.fincatto.documentofiscal.cte300.classes.evento.cancelamento.CTeRetornoCancelamento;
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

    private CTeEnviaEventoCancelamento gerarDadosCancelamento(final String chaveAcesso, final String numeroProtocolo, final String motivo) {
        final CTeEnviaEventoCancelamento cancelamento = new CTeEnviaEventoCancelamento();
        cancelamento.setDescricaoEvento(WSCancelamento.DESCRICAO_EVENTO);
        cancelamento.setJustificativa(motivo);
        cancelamento.setProtocoloAutorizacao(numeroProtocolo);
        return cancelamento;
    }
}
