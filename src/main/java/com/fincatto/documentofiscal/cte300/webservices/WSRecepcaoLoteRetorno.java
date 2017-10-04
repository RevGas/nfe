package com.fincatto.documentofiscal.cte300.webservices;

import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fincatto.documentofiscal.cte300.CTeConfig;
import com.fincatto.documentofiscal.cte300.classes.enviolote.consulta.CTeConsultaRecLote;
import com.fincatto.documentofiscal.cte300.classes.enviolote.consulta.CTeConsultaRecLoteRet;

public class WSRecepcaoLoteRetorno {

    private static final Logger LOGGER = LoggerFactory.getLogger(WSRecepcaoLoteRetorno.class);
    private final CTeConfig config;

    WSRecepcaoLoteRetorno(final CTeConfig config) {
        this.config = config;
    }

    CTeConsultaRecLoteRet consultaLote(final String numeroRecibo) throws Exception {
        throw new UnsupportedOperationException("Nao suportado ainda");
    }

    private String efetuaConsulta(final String xml) throws RemoteException {
        throw new UnsupportedOperationException("Nao suportado ainda");
    }

    private CTeConsultaRecLote gerarDadosConsulta(final String numeroRecibo) {
        final CTeConsultaRecLote consulta = new CTeConsultaRecLote();
        consulta.setNumeroRecebimento(numeroRecibo);
        consulta.setAmbiente(this.config.getAmbiente());
        consulta.setVersao(CTeConfig.VERSAO);
        return consulta;
    }
}
