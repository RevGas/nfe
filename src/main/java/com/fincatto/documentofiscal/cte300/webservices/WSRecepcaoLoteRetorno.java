package com.fincatto.documentofiscal.cte300.webservices;

import com.fincatto.documentofiscal.DFLog;
import com.fincatto.documentofiscal.cte300.CTeConfig;
import com.fincatto.documentofiscal.cte300.classes.enviolote.consulta.CTeConsultaRecLote;
import com.fincatto.documentofiscal.cte300.classes.enviolote.consulta.CTeConsultaRecLoteRet;

import java.rmi.RemoteException;

class WSRecepcaoLoteRetorno implements DFLog {

    private final CTeConfig config;

    WSRecepcaoLoteRetorno(final CTeConfig config) {
        this.config = config;
    }

    CTeConsultaRecLoteRet consultaLote(final String numeroRecibo) throws Exception {
//        final OMElement omElementConsulta = AXIOMUtil.stringToOM(this.gerarDadosConsulta(numeroRecibo).toString());
//        this.getLogger().debug(omElementConsulta.toString());
//
//        final OMElement omElementResult = this.efetuaConsulta(omElementConsulta);
//        this.getLogger().debug(omElementResult.toString());
//
//        return this.config.getPersister().read(CTeConsultaRecLoteRet.class, omElementResult.toString());
        return null;
    }

    private String efetuaConsulta(final String omElement) throws RemoteException {
//        final CteRetRecepcaoStub.CteCabecMsg cabec = new CteRetRecepcaoStub.CteCabecMsg();
//        cabec.setCUF(this.config.getCUF().getCodigoIbge());
//        cabec.setVersaoDados(CTeConfig.VERSAO);
//
//        final CteRetRecepcaoStub.CteCabecMsgE cabecE = new CteRetRecepcaoStub.CteCabecMsgE();
//        cabecE.setCteCabecMsg(cabec);
//
//        final CteRetRecepcaoStub.CteDadosMsg dados = new CteRetRecepcaoStub.CteDadosMsg();
//        dados.setExtraElement(omElement);
//
//        final CTAutorizador31 autorizador = CTAutorizador31.valueOfCodigoUF(this.config.getCUF());
//        final String endpoint = autorizador.getCteRetRecepcao(this.config.getAmbiente());
//        if (endpoint == null) {
//            throw new IllegalArgumentException("Nao foi possivel encontrar URL para RetRecepcao, autorizador " + autorizador.name() + ", UF " + this.config.getCUF().name());
//        }
//        final CteRetRecepcaoResult autorizacaoLoteResult = new CteRetRecepcaoStub(endpoint).cteRetRecepcao(dados, cabecE);
//        return autorizacaoLoteResult.getExtraElement();
        return null;
    }

    private CTeConsultaRecLote gerarDadosConsulta(final String numeroRecibo) {
        final CTeConsultaRecLote consulta = new CTeConsultaRecLote();
        consulta.setNumeroRecebimento(numeroRecibo);
        consulta.setAmbiente(this.config.getAmbiente());
        consulta.setVersao(CTeConfig.VERSAO);
        return consulta;
    }
}
