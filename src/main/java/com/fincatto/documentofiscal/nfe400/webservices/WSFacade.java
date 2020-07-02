package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.*;

import com.fincatto.documentofiscal.DFAmbiente;

import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.DFSocketFactory;
import com.fincatto.documentofiscal.DFUnidadeFederativa;
import com.fincatto.documentofiscal.nfe.NFeConfig;
import com.fincatto.documentofiscal.nfe.classes.distribuicao.NFDistribuicaoIntRetorno;
import com.fincatto.documentofiscal.nfe400.classes.evento.NFEnviaEventoRetorno;
import com.fincatto.documentofiscal.nfe400.classes.evento.inutilizacao.NFRetornoEventoInutilizacao;
import com.fincatto.documentofiscal.nfe400.classes.lote.envio.NFLoteEnvio;
import com.fincatto.documentofiscal.nfe400.classes.statusservico.consulta.NFStatusServicoConsultaRetorno;
import java.io.IOException;

import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.HttpsURLConnection;


public class WSFacade {

    private final WSLoteEnvio wsLoteEnvio;
    private final WSLoteConsulta wsLoteConsulta;
    private final WSStatusConsulta wsStatusConsulta;
    private final WSNotaConsulta wsNotaConsulta;
    private final WSCartaCorrecao wsCartaCorrecao;
    private final WSEvento wsEvento;
    private final WSConsultaCadastro wsConsultaCadastro;
    private final WSInutilizacao wsInutilizacao;
    private final WSManifestacaoDestinatario wSManifestacaoDestinatario;
//    private final WSDistribuicaoNFe wSDistribuicaoNFe;
    private final WSCancelamento wSCancelamento;

    public WSFacade(final NFeConfig config) throws IOException, KeyManagementException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, GeneralSecurityException {
        HttpsURLConnection.setDefaultSSLSocketFactory(new DFSocketFactory(config).createSSLContext().getSocketFactory());

        HttpsURLConnection.setFollowRedirects(true);
        
        // inicia os servicos disponiveis da nfe
        this.wsLoteEnvio = new WSLoteEnvio(config);
        this.wsLoteConsulta = new WSLoteConsulta(config);
        this.wsStatusConsulta = new WSStatusConsulta(config);
        this.wsNotaConsulta = new WSNotaConsulta(config);
        this.wsCartaCorrecao = new WSCartaCorrecao(config);
        this.wsEvento = new WSEvento(config);
        this.wsConsultaCadastro = new WSConsultaCadastro(config);
        this.wsInutilizacao = new WSInutilizacao(config);
        this.wSManifestacaoDestinatario = new WSManifestacaoDestinatario(config);
//        this.wSDistribuicaoNFe = new WSDistribuicaoNFe(config);
        this.wSCancelamento = new WSCancelamento(config);
    }
    
    /**
     * Faz o envio de lote para a Sefaz.
     *
     * @param lote o lote a ser enviado para a Sefaz
     * @return dados do lote retornado pelo webservice, alem do lote assinado
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public TRetEnviNFe enviaLote(final NFLoteEnvio lote) throws Exception {
        return this.wsLoteEnvio.enviaLote(lote);
    }
    
    public TRetEnviNFe enviaLote(final String xml) throws Exception {
        return this.wsLoteEnvio.enviaLote(xml);
    }
    
    public NFLoteEnvio getLoteAssinado(final NFLoteEnvio lote) throws Exception {
        return this.wsLoteEnvio.getLoteAssinado(lote);
    }
    
    /**
     * Faz o envio assinado para a Sefaz de NF-e e NFC-e ATENCAO: Esse metodo deve ser utilizado para assinaturas A3.
     *
     * @param loteAssinadoXml lote assinado no formato XML
     * @param modelo modelo da nota (NF-e ou NFC-e)
     * @return dados do lote retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public TRetEnviNFe enviaLoteAssinado(final String loteAssinadoXml, final DFModelo modelo, final DFAmbiente ambiente) throws Exception {
        return this.wsLoteEnvio.enviaLoteAssinado(loteAssinadoXml, modelo, ambiente);
    }
    
    /**
     * Faz a consulta do lote na Sefaz (NF-e e NFC-e).
     *
     * @param numeroRecibo numero do recibo do processamento
     * @param modelo modelo da nota (NF-e ou NFC-e)
     * @return dados de consulta de lote retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public TRetConsReciNFe consultaLote(final String numeroRecibo, final DFModelo modelo) throws Exception {
        return this.wsLoteConsulta.consultaLote(numeroRecibo, modelo);
    }
    
    /**
     * Faz a consulta de status responsavel pela UF.
     *
     * @param uf uf UF que deseja consultar o status do sefaz responsavel
     * @param modelo modelo da nota (NF-e ou NFC-e)
     * @return dados da consulta de status retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public NFStatusServicoConsultaRetorno consultaStatus(final DFUnidadeFederativa uf, final DFModelo modelo) throws Exception {
        return this.wsStatusConsulta.consultaStatus(uf, modelo);
    }
    
    /**
     * Faz a consulta da nota.
     *
     * @param chaveDeAcesso chave de acesso da nota
     * @return dados da consulta da nota retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public TRetConsSitNFe consultaProtocolo(final String chaveDeAcesso) throws Exception {
        return this.wsNotaConsulta.consultaProtocolo(chaveDeAcesso);
    }
    
    /**
     * Faz a correcao da nota
     * @param chNFe chave de acesso da nota
     * @param xCorrecao texto de correcao
     * @param nSeqEvento numero sequencial de evento, esse numero nao pode ser repetido!
     * @return dados da correcao da nota retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public br.inf.portalfiscal.nfe.model.evento_carta_correcao.Evento_CCe_PL_v101.TRetEnvEvento corrigeNota(final String chNFe, final String xCorrecao, final int nSeqEvento) throws Exception {
        return this.wsCartaCorrecao.corrigeNota(chNFe, xCorrecao, nSeqEvento);
    }
    
    /**
     * Faz a correcao da nota com o evento ja assinado ATENCAO: Esse metodo deve ser utilizado para assinaturas A3
     * @param eventoAssinadoXml evento ja assinado em formato XML
     * @return dados da correcao da nota retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public NFEnviaEventoRetorno corrigeNotaAssinada(final String eventoAssinadoXml) throws Exception {
        return this.wsCartaCorrecao.corrigeNotaAssinada(eventoAssinadoXml);
    }

    /**
     * Faz o envio de eventos da nota
     * @param descEvento
     * @param tpEvento
     * @param chave chave de acesso da nota
     * @param numeroProtocolo numero do protocolo da nota
     * @param motivo motivo do cancelamento
     * @param nSeqEvento
     * @return dados do evento da nota retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public br.inf.portalfiscal.nfe.model.evento_generico.Evento_Generico_PL_v101.TRetEnvEvento enviaEvento(final String descEvento, final String tpEvento, final String chave, final String numeroProtocolo, final String motivo, final String nSeqEvento, final String cnpj) throws Exception {
        return this.wsEvento.enviaEvento(descEvento, tpEvento, chave, numeroProtocolo, motivo, nSeqEvento, cnpj);
    }
    
    /**
     * Faz o envio do cancelamento da nota
     * @param chNFe de Acesso da NF-e vinculada ao Evento
     * @param nProt Informar o número do Protocolo de Autorização da NF-e a ser Cancelada.
     * @param xJust Informar a justificativa do cancelamento
     * @return dados do cancelamento da nota retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public br.inf.portalfiscal.nfe.model.evento_cancelamento.Evento_Canc_PL_v101.TProcEvento cancelaNota(final String chNFe, final String nProt, final String xJust) throws Exception {
        return this.wSCancelamento.cancelaNota(chNFe, nProt, xJust);
    }
    
    /**
     * Faz o cancelamento da nota com evento ja assinado ATENCAO: Esse metodo deve ser utilizado para assinaturas A3
     * @param chave chave de acesso da nota
     * @param eventoAssinadoXml evento ja assinado em formato XML
     * @return dados do cancelamento da nota retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public NFEnviaEventoRetorno cancelaNotaAssinada(final String chave, final String eventoAssinadoXml) throws Exception {
        return this.wsEvento.cancelaNotaAssinada(chave, eventoAssinadoXml);
    }
    
    /**
     * Inutiliza a nota com o evento assinado ATENCAO: Esse metodo deve ser utilizado para assinaturas A3.
     *
     * @param eventoAssinadoXml evento assinado em XML
     * @param modelo modelo da nota (NF-e ou NFC-e)
     * @return dados da inutilizacao da nota retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public NFRetornoEventoInutilizacao inutilizaNotaAssinada(final String eventoAssinadoXml, final DFModelo modelo) throws Exception {
        return this.wsInutilizacao.inutilizaNotaAssinada(eventoAssinadoXml, modelo);
    }
    
    /**
     * Inutiliza a nota.
     *
     * @param anoInutilizacaoNumeracao ano de inutilizacao
     * @param cnpjEmitente CNPJ emitente da nota
     * @param serie serie da nota
     * @param numeroInicial numero inicial da nota
     * @param numeroFinal numero final da nota
     * @param justificativa justificativa da inutilizacao
     * @param modelo modelo da nota (NF-e ou NFC-e)
     * @return dados da inutilizacao da nota retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public TRetInutNFe inutilizaNota(final int anoInutilizacaoNumeracao, final String cnpjEmitente, final String serie, final String numeroInicial, final String numeroFinal, final String justificativa, final DFModelo modelo) throws Exception {
        return this.wsInutilizacao.inutilizaNota(anoInutilizacaoNumeracao, cnpjEmitente, serie, numeroInicial, numeroFinal, justificativa, modelo);
    }
    
    /**
     * Realiza a consulta de cadastro de pessoa juridica com inscricao estadual.
     *
     * @param cnpj CNPJ da pessoa juridica
     * @param uf UF da pessoa juridica
     * @return dados da consulta da pessoa juridica retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public br.inf.portalfiscal.nfe.model.consulta_cadastro.PL_006v.TRetConsCad consultaCadastro(final String cnpj, final DFUnidadeFederativa uf) throws Exception {
        return this.wsConsultaCadastro.consultaCadastro(cnpj, uf);
    }
    
    /**
     * Faz a manifestação do destinatário da nota
     * @param chNFe chave de acesso da nota
     * @param descEvento Informar a descrição do evento: Confirmacao da Operacao, Ciencia da Operacao, Desconhecimento da Operacao, Operacao nao Realizada
     * @param tpEvento tipo do evento da manifestacao do destinatario
     * @param xJust motivo do cancelamento
     * @param CNPJ cnpj do autor do evento
     * @return dados da manifestacao do destinatario da nota retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public br.inf.portalfiscal.nfe.model.evento_manifesta_destinatario.Evento_ManifestaDest_PL_v101.TRetEnvEvento manifestaDestinatarioNota(final String chNFe, final String descEvento, final String tpEvento, final String xJust, final String CNPJ) throws Exception {
        return this.wSManifestacaoDestinatario.manifestaDestinatarioNota(chNFe, descEvento, tpEvento, xJust, CNPJ);
    }
    
    /**
     * Faz consulta de distribuicao das notas fiscais. Pode ser feita pela chave de acesso ou utilizando o NSU (numero sequencial unico) da receita.
     * @param cnpj CPF ou CNPJ da pessoa fisica ou juridica a consultar
     * @param uf Unidade federativa da pessoa juridica a consultar
     * @param chaveAcesso
     * @param nsu Número Sequencial Único. Geralmente esta consulta será utilizada quando identificado pelo interessado um NSU faltante.
     *            O Web Service retornará o documento ou informará que o NSU não existe no Ambiente Nacional. Assim, esta
     *            consulta fechará a lacuna do NSU identificado como faltante.
     * @param ultNsu Último NSU recebido pelo ator. Caso seja informado com zero, ou com um NSU muito antigo, a consulta retornará unicamente as
     *               informações resumidas e documentos fiscais eletrônicos que tenham sido recepcionados pelo
     *               Ambiente Nacional nos últimos 3 meses.
     * @return dados da consulta retornado pelo webservice limitando um total de 50 registros
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public NFDistribuicaoIntRetorno consultarDistribuicaoDFe(final String cnpj, final DFUnidadeFederativa uf, final String chaveAcesso, final String nsu, final String ultNsu) throws Exception {
//        return this.wSDistribuicaoNFe.consultar(cnpj, uf, chaveAcesso, nsu, ultNsu);
        return null;
    }
}