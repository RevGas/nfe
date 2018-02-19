package com.fincatto.documentofiscal.mdfe3.webservices;

import com.fincatto.documentofiscal.mdfe3.MDFeConfig;
import com.fincatto.documentofiscal.mdfe3.classes.nota.consulta.MDFeNotaConsulta;
import com.fincatto.documentofiscal.mdfe3.classes.nota.consulta.MDFeNotaConsultaRetorno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

class WSNotaConsulta {
    private static final String NOME_SERVICO = "CONSULTAR";
    private static final String VERSAO_SERVICO = "3.00";
    private final static Logger LOGGER = LoggerFactory.getLogger(WSNotaConsulta.class);
    private final MDFeConfig config;

    WSNotaConsulta(final MDFeConfig config) {
        this.config = config;
    }

    public MDFeNotaConsultaRetorno consultaNota(final String chaveDeAcesso) throws Exception {
        throw new UnsupportedOperationException("Nao suportado ainda");
    }

    private String efetuaConsulta(final String omElementConsulta, final String chaveDeAcesso) throws Exception {
        throw new UnsupportedOperationException("Nao suportado ainda");
    }

    private MDFeNotaConsulta gerarDadosConsulta(final String chaveDeAcesso) {
        final MDFeNotaConsulta notaConsulta = new MDFeNotaConsulta();
        notaConsulta.setAmbiente(this.config.getAmbiente());
        notaConsulta.setChave(chaveDeAcesso);
        notaConsulta.setServico(WSNotaConsulta.NOME_SERVICO);
        notaConsulta.setVersao(new BigDecimal(WSNotaConsulta.VERSAO_SERVICO));
        return notaConsulta;
    }
}