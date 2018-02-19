package com.fincatto.documentofiscal.mdfe3.webservices;

import com.fincatto.documentofiscal.DFUnidadeFederativa;
import com.fincatto.documentofiscal.mdfe3.MDFeConfig;
import com.fincatto.documentofiscal.mdfe3.classes.MDFAutorizador3;
import com.fincatto.documentofiscal.mdfe3.classes.consultastatusservico.MDFeConsStatServ;
import com.fincatto.documentofiscal.mdfe3.classes.consultastatusservico.MDFeConsStatServRet;
import com.fincatto.documentofiscal.transformers.DFRegistryMatcher;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;

/**
 * Created by Eldevan Nery Junior on 10/11/17.
 *
 * Classe para envio do pedido de Consulta do Status do Serviço MDF-e.
 *
 */
class WSStatusConsulta {

    private static final String NOME_SERVICO = "STATUS";
    private static final Logger LOGGER = LoggerFactory.getLogger(WSStatusConsulta.class);
    private final MDFeConfig config;

    WSStatusConsulta(final MDFeConfig config) {
        this.config = config;
    }

    MDFeConsStatServRet consultaStatus(final DFUnidadeFederativa uf) throws Exception {
        throw new UnsupportedOperationException("Nao suportado ainda");
    }

    private MDFeConsStatServ gerarDadosConsulta(final DFUnidadeFederativa unidadeFederativa) {
        final MDFeConsStatServ consStatServ = new MDFeConsStatServ();
        consStatServ.setAmbiente(this.config.getAmbiente());
        consStatServ.setVersao(MDFeConfig.VERSAO);
        consStatServ.setServico(WSStatusConsulta.NOME_SERVICO);
        return consStatServ;
    }

    private String efetuaConsultaStatus(final String omElement, final DFUnidadeFederativa unidadeFederativa) throws RemoteException {
        throw new UnsupportedOperationException("Nao suportado ainda");
    }
}
