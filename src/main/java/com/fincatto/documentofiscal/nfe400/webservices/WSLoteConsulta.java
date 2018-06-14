package com.fincatto.documentofiscal.nfe400.webservices;

import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.nfe.NFeConfig;
import com.fincatto.documentofiscal.nfe400.classes.NFAutorizador400;
import com.fincatto.documentofiscal.nfe400.classes.lote.consulta.NFLoteConsulta;
import com.fincatto.documentofiscal.nfe400.classes.lote.consulta.NFLoteConsultaRetorno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.rmi.RemoteException;

class WSLoteConsulta {

    final private static Logger LOGGER = LoggerFactory.getLogger(WSLoteConsulta.class);
    private final NFeConfig config;

    WSLoteConsulta(final NFeConfig config) {
        this.config = config;
    }

    NFLoteConsultaRetorno consultaLote(final String numeroRecibo, final DFModelo modelo) throws Exception {
//        final OMElement omElementConsulta = AXIOMUtil.stringToOM(this.gerarDadosConsulta(numeroRecibo).toString());
//        WSLoteConsulta.LOGGER.debug(omElementConsulta.toString());
//
//        final OMElement omElementResult = this.efetuaConsulta(omElementConsulta, modelo);
//        WSLoteConsulta.LOGGER.debug(omElementResult.toString());
//
//        return new Persister(new DFRegistryMatcher(), new Format(0)).read(NFLoteConsultaRetorno.class, omElementResult.toString());
        return null;
    }

    private String efetuaConsulta(final String omElement, final DFModelo modelo) throws RemoteException {
//        final NFeRetAutorizacao4Stub.NfeDadosMsg dados = new NFeRetAutorizacao4Stub.NfeDadosMsg();
//        dados.setExtraElement(omElement);

        final NFAutorizador400 autorizador = NFAutorizador400.valueOfTipoEmissao(this.config.getTipoEmissao(), this.config.getCUF());
        final String urlWebService = DFModelo.NFCE.equals(modelo) ? autorizador.getNfceRetAutorizacao(this.config.getAmbiente()) : autorizador.getNfeRetAutorizacao(this.config.getAmbiente());
        if (urlWebService == null) {
            throw new IllegalArgumentException("Nao foi possivel encontrar URL para RetAutorizacao " + modelo.name() + ", autorizador " + autorizador.name());
        }
//        final NfeResultMsg autorizacaoLoteResult = new NFeRetAutorizacao4Stub(urlWebService).nfeRetAutorizacaoLote(dados);
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
