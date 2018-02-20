package com.fincatto.documentofiscal.mdfe3.webservices;

import com.fincatto.documentofiscal.mdfe3.MDFeConfig;
import com.fincatto.documentofiscal.mdfe3.classes.consultanaoencerrados.MDFeConsultaNaoEncerrados;
import com.fincatto.documentofiscal.mdfe3.classes.consultanaoencerrados.MDFeConsultaNaoEncerradosRetorno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;

/**
 * Created by Eldevan Nery Junior on 22/11/17.
 *
 * Classe para envio do pedido de Consulta do Serviço de MDF-e's não encerrados.
 *
 */
class WSConsultaNaoEncerrados {

    private static final String NOME_SERVICO = "CONSULTAR NÃO ENCERRADOS";
    private static final Logger LOGGER = LoggerFactory.getLogger(WSConsultaNaoEncerrados.class);
    private final MDFeConfig config;

    WSConsultaNaoEncerrados(final MDFeConfig config) {
        this.config = config;
    }

    MDFeConsultaNaoEncerradosRetorno consultaNaoEncerrados(final String cnpj) throws Exception {
        throw new UnsupportedOperationException("Nao suportado ainda");
    }

    private MDFeConsultaNaoEncerrados gerarDadosConsulta(final String cnpj) {
        final MDFeConsultaNaoEncerrados encerrados = new MDFeConsultaNaoEncerrados();
        encerrados.setAmbiente(this.config.getAmbiente());
        encerrados.setVersao(MDFeConfig.VERSAO);
        encerrados.setCnpj(cnpj);
        encerrados.setServico(WSConsultaNaoEncerrados.NOME_SERVICO);
        return encerrados;
    }

    private String efetuaConsultaStatus(final String omElement) throws RemoteException {
        throw new UnsupportedOperationException("Nao suportado ainda");
    }
}