package com.fincatto.documentofiscal.nfe310.webservices;

import com.fincatto.documentofiscal.DFLog;
import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.nfe.NFeConfig;
import com.fincatto.documentofiscal.nfe310.classes.lote.consulta.NFLoteConsulta;
import com.fincatto.documentofiscal.nfe310.classes.lote.consulta.NFLoteConsultaRetorno;
import java.math.BigDecimal;
import java.rmi.RemoteException;


class WSLoteConsulta implements DFLog {
    
    private final NFeConfig config;
    
    WSLoteConsulta(final NFeConfig config) {
        this.config = config;
    }
    
    NFLoteConsultaRetorno consultaLote(final String numeroRecibo, final DFModelo modelo) throws Exception {
//        final OMElement omElementConsulta = AXIOMUtil.stringToOM(this.gerarDadosConsulta(numeroRecibo).toString());
//        this.getLogger().debug(omElementConsulta.toString());
//        
//        final OMElement omElementResult = this.efetuaConsulta(omElementConsulta, modelo);
//        this.getLogger().debug(omElementResult.toString());
        
//        return this.config.getPersister().read(NFLoteConsultaRetorno.class, omElementResult.toString());
        return null;
    }
    
    private String efetuaConsulta(final String omElement, final DFModelo modelo) throws RemoteException {
//        final NfeRetAutorizacaoStub.NfeCabecMsg cabec = new NfeRetAutorizacaoStub.NfeCabecMsg();
//        cabec.setCUF(this.config.getCUF().getCodigoIbge());
//        cabec.setVersaoDados(this.config.getVersao());
//        
//        final NfeRetAutorizacaoStub.NfeCabecMsgE cabecE = new NfeRetAutorizacaoStub.NfeCabecMsgE();
//        cabecE.setNfeCabecMsg(cabec);
//        
//        final NfeRetAutorizacaoStub.NfeDadosMsg dados = new NfeRetAutorizacaoStub.NfeDadosMsg();
//        dados.setExtraElement(omElement);
//        
//        final NFAutorizador31 autorizador = NFAutorizador31.valueOfTipoEmissao(this.config.getTipoEmissao(), this.config.getCUF());
//        final String endpoint = DFModelo.NFCE.equals(modelo) ? autorizador.getNfceRetAutorizacao(this.config.getAmbiente()) : autorizador.getNfeRetAutorizacao(this.config.getAmbiente());
//        if (endpoint == null) {
//            throw new IllegalArgumentException("Nao foi possivel encontrar URL para RetAutorizacao " + modelo.name() + ", autorizador " + autorizador.name());
//        }
//        
//        final NfeRetAutorizacaoLoteResult autorizacaoLoteResult = new NfeRetAutorizacaoStub(urlWebService).nfeRetAutorizacaoLote(dados, cabecE);
//        return autorizacaoLoteResult.getExtraElement();
        return null;
    }
    
    private NFLoteConsulta gerarDadosConsulta(final String numeroRecibo) {
        final NFLoteConsulta consulta = new NFLoteConsulta();
        consulta.setRecibo(numeroRecibo);
        consulta.setAmbiente(this.config.getAmbiente());
        consulta.setVersao(new BigDecimal(this.config.getVersao()));
        return consulta;
    }

}
