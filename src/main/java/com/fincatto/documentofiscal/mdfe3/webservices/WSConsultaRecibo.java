package com.fincatto.documentofiscal.mdfe3.webservices;

import com.fincatto.documentofiscal.mdfe3.MDFeConfig;
import com.fincatto.documentofiscal.mdfe3.classes.consultaRecibo.MDFeConsultaRecibo;
import com.fincatto.documentofiscal.mdfe3.classes.consultaRecibo.MDFeConsultaReciboRetorno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;

/**
 * Created by Eldevan Nery Junior on 30/11/17.
 *
 * Classe para envio do pedido de Consulta do recibo MDF-e.
 *
 */
class WSConsultaRecibo {

    private static final Logger LOGGER = LoggerFactory.getLogger(WSConsultaRecibo.class);
    private final MDFeConfig config;

    WSConsultaRecibo(final MDFeConfig config) {
        this.config = config;
    }

    MDFeConsultaReciboRetorno consultaRecibo(final String numeroRecibo) throws Exception {
        throw new UnsupportedOperationException("Nao suportado ainda");
    }

    private MDFeConsultaRecibo gerarDadosConsulta(final String numeroRecibo) {
        final MDFeConsultaRecibo consultaRecibo = new MDFeConsultaRecibo();
        consultaRecibo.setNumeroRecibo(numeroRecibo);
        consultaRecibo.setAmbiente(this.config.getAmbiente());
        consultaRecibo.setVersao(MDFeConfig.VERSAO);
        return consultaRecibo;
    }

    private String efetuaConsultaRecibo(final String omElement) throws RemoteException {
        throw new UnsupportedOperationException("Nao suportado ainda");
    }
}
