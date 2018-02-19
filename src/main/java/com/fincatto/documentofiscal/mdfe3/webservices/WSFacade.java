package com.fincatto.documentofiscal.mdfe3.webservices;

import com.fincatto.documentofiscal.DFUnidadeFederativa;
import com.fincatto.documentofiscal.SocketFactory;
import com.fincatto.documentofiscal.mdfe3.MDFeConfig;
import com.fincatto.documentofiscal.mdfe3.classes.consultastatusservico.MDFeConsStatServRet;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.HttpsURLConnection;

public class WSFacade {


	private final WSStatusConsulta wsStatusConsulta;
//	private final WSRecepcaoLote wsRecepcaoLote;
//	private final WSNotaConsulta wsNotaConsulta;
//    private final WSCancelamento wsCancelamento;

//	private final WSRecepcaoLoteRetorno wsRecepcaoLoteRetorno;

    public WSFacade(final MDFeConfig config) throws IOException, KeyManagementException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException {
        HttpsURLConnection.setDefaultSSLSocketFactory(new SocketFactory(config).createSSLContext().getSocketFactory());
        this.wsStatusConsulta = new WSStatusConsulta(config);
//        this.wsRecepcaoLote = new WSRecepcaoLote(config);
//        this.wsRecepcaoLoteRetorno = new WSRecepcaoLoteRetorno(config);
//        this.wsNotaConsulta = new WSNotaConsulta(config);
//        this.wsCancelamento = new WSCancelamento(config);
    }

    /**
     * Faz a consulta de status responsavel pela UF, no caso apenas o RS está disponível
     *
     * @param uf uf UF que deseja consultar o status do sefaz responsavel
     * @return dados da consulta de status retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public MDFeConsStatServRet consultaStatus(final DFUnidadeFederativa uf) throws Exception {
        return this.wsStatusConsulta.consultaStatus(uf);
    }

    /**
     * @see #consultaStatus(DFUnidadeFederativa)
     * @return
     * @throws Exception
     */
    public MDFeConsStatServRet consultaStatus() throws Exception {
        return this.wsStatusConsulta.consultaStatus(DFUnidadeFederativa.RS);
    }

}