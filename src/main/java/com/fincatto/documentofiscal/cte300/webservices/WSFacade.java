package com.fincatto.documentofiscal.cte300.webservices;

import br.inf.portalfiscal.cte.TEnviCTe;
import br.inf.portalfiscal.cte.TRetConsReciCTe;
import br.inf.portalfiscal.cte.TRetConsStatServ;
import br.inf.portalfiscal.cte.TRetEnviCTe;
import br.inf.portalfiscal.cte.TRetEvento;
import com.fincatto.documentofiscal.cte300.CTeConfig;

import com.fincatto.documentofiscal.cte300.classes.nota.consulta.CTeNotaConsultaRetorno;
import com.fincatto.documentofiscal.utils.DFSocketFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.HttpsURLConnection;

public class WSFacade {

    private final WSStatusConsulta wsStatusConsulta;
    private final WSRecepcaoLote wsRecepcaoLote;
    private final WSNotaConsulta wsNotaConsulta;
    private final WSCancelamento wsCancelamento;

    private final WSRecepcaoLoteRetorno wsRecepcaoLoteRetorno;

    public WSFacade(final CTeConfig config) throws KeyManagementException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, GeneralSecurityException {
        HttpsURLConnection.setDefaultSSLSocketFactory(new DFSocketFactory(config).createSSLContext().getSocketFactory());
        this.wsStatusConsulta = new WSStatusConsulta(config);
        this.wsRecepcaoLote = new WSRecepcaoLote(config);
        this.wsRecepcaoLoteRetorno = new WSRecepcaoLoteRetorno(config);
        this.wsNotaConsulta = new WSNotaConsulta(config);
        this.wsCancelamento = new WSCancelamento(config);
    }

    /**
     * Faz a consulta de status responsavel pela UF
     *
     * @return dados da consulta de status retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com
     * o sefaz
     */
    public TRetConsStatServ consultaStatus() throws Exception {
        return this.wsStatusConsulta.consultaStatus();
    }

    /**
     * Faz o envio do lote para a SEFAZ de forma assíncrona
     *
     * @param tEnviCTe a ser eviado para a SEFAZ
     * @return dados do retorno do envio do lote e o xml assinado
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public TRetEnviCTe envioRecepcaoLote(TEnviCTe tEnviCTe) throws Exception {
        return this.wsRecepcaoLote.envioRecepcao(tEnviCTe);
    }
    
    /**
     * Faz o envio do lote para a SEFAZ de forma síncrona
     *
     * @param tEnviCTe a ser eviado para a SEFAZ
     * @return dados do retorno do envio do lote e o xml assinado
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public TRetEnviCTe envioRecepcaoLoteSinc(TEnviCTe tEnviCTe) throws Exception {
        return this.wsRecepcaoLote.envioRecepcaoSinc(tEnviCTe);
    }

    /**
     * Faz a consulta do processamento do lote na SEFAZ
     *
     * @param nRec do recebimento do lote
     * @return dados da consulta do processamento do lote
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com
     * o sefaz
     *
     */
    public TRetConsReciCTe consultaEnvioRecepcaoLote(String nRec) throws Exception {
        return this.wsRecepcaoLoteRetorno.consultaLote(nRec);
    }

    /**
     * Faz a consulta do CTe
     *
     * @param chaveDeAcesso chave de acesso do cte
     * @return dados da consulta da nota retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public CTeNotaConsultaRetorno consultaNota(final String chaveDeAcesso) throws Exception {
        return this.wsNotaConsulta.consultaNota(chaveDeAcesso);
    }

    /**
     * Faz o cancelamento do CTe
     * @param chCTe chave de acesso do CTe
     * @param nProt numero do protocolo do CTe
     * @param XJus motivo do cancelamento do CTe
     * @return dados do cancelamento da nota retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public TRetEvento cancelaCTe(final String chCTe, final String nProt, final String XJus) throws Exception {
        return this.wsCancelamento.cancelaCTe(chCTe, nProt, XJus);
    }

}
