package com.fincatto.documentofiscal.cte300.webservices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fincatto.documentofiscal.DFUnidadeFederativa;
import com.fincatto.documentofiscal.cte300.CTeConfig;
import com.fincatto.documentofiscal.cte300.classes.consultastatusservico.CTeConsStatServ;
import com.fincatto.documentofiscal.cte300.classes.consultastatusservico.CTeConsStatServRet;

import java.rmi.RemoteException;

class WSStatusConsulta {

	private static final String NOME_SERVICO = "STATUS";
	private static final Logger LOGGER = LoggerFactory.getLogger(WSStatusConsulta.class);
	private final CTeConfig config;

	WSStatusConsulta(final CTeConfig config) {
		this.config = config;
	}

	CTeConsStatServRet consultaStatus(final DFUnidadeFederativa uf) throws Exception {
            throw new UnsupportedOperationException("Nao suportado ainda");
	}

	private CTeConsStatServ gerarDadosConsulta(final DFUnidadeFederativa unidadeFederativa) {
		final CTeConsStatServ consStatServ = new CTeConsStatServ();
		consStatServ.setAmbiente(this.config.getAmbiente());
		consStatServ.setVersao(CTeConfig.VERSAO);
		consStatServ.setServico(WSStatusConsulta.NOME_SERVICO);
		return consStatServ;
	}

	private String efetuaConsultaStatus(final String xml, final DFUnidadeFederativa unidadeFederativa) throws RemoteException {
		throw new UnsupportedOperationException("Nao suportado ainda");
	}
}
