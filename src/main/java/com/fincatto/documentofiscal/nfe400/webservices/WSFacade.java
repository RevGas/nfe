package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.TRetConsReciNFe;
import br.inf.portalfiscal.nfe.TRetEnvEvento;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import br.inf.portalfiscal.nfe.TRetEnviNFe;
import br.inf.portalfiscal.nfe.TRetInutNFe;
import com.fincatto.documentofiscal.DFAmbiente;
import com.fincatto.documentofiscal.nfe400.classes.evento.cartacorrecao.NFProtocoloEventoCartaCorrecao;

import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.DFSocketFactory;
import com.fincatto.documentofiscal.DFUnidadeFederativa;
import com.fincatto.documentofiscal.nfe.NFeConfig;
import com.fincatto.documentofiscal.nfe.classes.distribuicao.NFDistribuicaoIntRetorno;
import com.fincatto.documentofiscal.nfe400.classes.cadastro.NFRetornoConsultaCadastro;
import com.fincatto.documentofiscal.nfe400.classes.evento.NFEnviaEventoRetorno;
import com.fincatto.documentofiscal.nfe400.classes.evento.inutilizacao.NFRetornoEventoInutilizacao;
import com.fincatto.documentofiscal.nfe400.classes.evento.manifestacaodestinatario.NFTipoEventoManifestacaoDestinatario;
import com.fincatto.documentofiscal.nfe400.classes.lote.envio.NFLoteEnvio;
import com.fincatto.documentofiscal.nfe400.classes.nota.consulta.NFNotaConsultaRetorno;
import com.fincatto.documentofiscal.nfe400.classes.statusservico.consulta.NFStatusServicoConsultaRetorno;
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

    public WSFacade(final NFeConfig config) throws IOException, KeyManagementException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException {
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
    }

    /**
     * Faz o envio de lote para a Sefaz
     * @param lote o lote a ser enviado para a Sefaz
     * @return dados do lote retornado pelo webservice, alem do lote assinado
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public TRetEnviNFe enviaLote(final NFLoteEnvio lote) throws Exception {
        return this.wsLoteEnvio.enviaLote(lote);
    }

    public NFLoteEnvio getLoteAssinado(final NFLoteEnvio lote) throws Exception {
        return this.wsLoteEnvio.getLoteAssinado(lote);
    }

    /**
     * Faz o envio assinado para a Sefaz de NF-e e NFC-e ATENCAO: Esse metodo deve ser utilizado para assinaturas A3
     * @param loteAssinadoXml lote assinado no formato XML
     * @param modelo modelo da nota (NF-e ou NFC-e)
     * @return dados do lote retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public TRetEnviNFe enviaLoteAssinado(final String loteAssinadoXml, final DFModelo modelo, final DFAmbiente ambiente) throws Exception {
        return this.wsLoteEnvio.enviaLoteAssinado(loteAssinadoXml, modelo, ambiente);
    }

    /**
     * Faz a consulta do lote na Sefaz (NF-e e NFC-e)
     * @param numeroRecibo numero do recibo do processamento
     * @param modelo modelo da nota (NF-e ou NFC-e)
     * @return dados de consulta de lote retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public TRetConsReciNFe consultaLote(final String numeroRecibo, final DFModelo modelo) throws Exception {
        return this.wsLoteConsulta.consultaLote(numeroRecibo, modelo);
    }

    /**
     * Faz a consulta de status responsavel pela UF
     * @param uf uf UF que deseja consultar o status do sefaz responsavel
     * @param modelo modelo da nota (NF-e ou NFC-e)
     * @return dados da consulta de status retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public NFStatusServicoConsultaRetorno consultaStatus(final DFUnidadeFederativa uf, final DFModelo modelo) throws Exception {
        return this.wsStatusConsulta.consultaStatus(uf, modelo);
    }

    /**
     * Faz a consulta da nota
     * @param chaveDeAcesso chave de acesso da nota
     * @return dados da consulta da nota retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public NFNotaConsultaRetorno consultaNota(final String chaveDeAcesso) throws Exception {
        return this.wsNotaConsulta.consultaNota(chaveDeAcesso);
    }

    /**
     * Faz a correcao da nota
     * @param chaveDeAcesso chave de acesso da nota
     * @param textoCorrecao texto de correcao
     * @param numeroSequencialEvento numero sequencial de evento, esse numero nao pode ser repetido!
     * @return dados da correcao da nota retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public NFEnviaEventoRetorno corrigeNota(final String chaveDeAcesso, final String textoCorrecao, final int numeroSequencialEvento) throws Exception {
        return this.wsCartaCorrecao.corrigeNota(chaveDeAcesso, textoCorrecao, numeroSequencialEvento);
    }

    /**
     * Faz a correcao da nota com o evento ja assinado ATENCAO: Esse metodo deve ser utilizado para assinaturas A3
     * @param chave chave de acesso da nota
     * @param eventoAssinadoXml evento ja assinado em formato XML
     * @return dados da correcao da nota retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public NFEnviaEventoRetorno corrigeNotaAssinada(final String chave, final String eventoAssinadoXml) throws Exception {
        return this.wsCartaCorrecao.corrigeNotaAssinada(chave, eventoAssinadoXml);
    }

    /**
     * Faz a correcao da nota com o evento ja assinado.
     * @param eventoAssinadoXml evento ja assinado em formato XML
     * @return dados da correcao da nota retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public NFEnviaEventoRetorno corrigeNotaAssinada(final String eventoAssinadoXml) throws Exception {
        return this.wsCartaCorrecao.corrigeNotaAssinada(eventoAssinadoXml);
    }

    public NFProtocoloEventoCartaCorrecao corrigeNotaAssinadaProtocolo(final String eventoAssinadoXml) throws Exception {
        return this.wsCartaCorrecao.corrigeNotaAssinadaProtocolo(eventoAssinadoXml);
    }

    public NFProtocoloEventoCartaCorrecao corrigeNotaAssinadaProtocolo(final String chaveDeAcesso, final String textoCorrecao, final int numeroSequencialEvento) throws Exception {
        return this.wsCartaCorrecao.corrigeNotaAssinadaProtocolo(getXmlAssinado(chaveDeAcesso, textoCorrecao, numeroSequencialEvento));
    }

    public String getXmlAssinado(final String chaveDeAcesso, final String textoCorrecao, final int numeroSequencialEvento) throws Exception {
        return this.wsCartaCorrecao.getXmlAssinado(chaveDeAcesso, textoCorrecao, numeroSequencialEvento);
    }

    /**
     * Faz o envio de eventos da nota
     * @param descEvento
     * @param tpEvento
     * @param chave chave de acesso da nota
     * @param numeroProtocolo numero do protocolo da nota
     * @param motivo motivo do cancelamento
     * @return dados do cancelamento da nota retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public TRetEnvEvento enviaEvento(final String descEvento, final String tpEvento, final String chave, final String numeroProtocolo, final String motivo, final String nSeqEvento) throws Exception {
        return this.wsEvento.enviaEvento(descEvento, tpEvento, chave, numeroProtocolo, motivo, nSeqEvento);
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
     * Inutiliza a nota com o evento assinado ATENCAO: Esse metodo deve ser utilizado para assinaturas A3
     * @param eventoAssinadoXml evento assinado em XML
     * @param modelo modelo da nota (NF-e ou NFC-e)
     * @return dados da inutilizacao da nota retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public NFRetornoEventoInutilizacao inutilizaNotaAssinada(final String eventoAssinadoXml, final DFModelo modelo) throws Exception {
        return this.wsInutilizacao.inutilizaNotaAssinada(eventoAssinadoXml, modelo);
    }

    /**
     * Inutiliza a nota
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
     * Realiza a consulta de cadastro de pessoa juridica com inscricao estadual
     * @param cnpj CNPJ da pessoa juridica
     * @param uf UF da pessoa juridica
     * @return dados da consulta da pessoa juridica retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public NFRetornoConsultaCadastro consultaCadastro(final String cnpj, final DFUnidadeFederativa uf) throws Exception {
        return this.wsConsultaCadastro.consultaCadastro(cnpj, uf);
    }

    /**
     * Faz a manifestação do destinatário da nota
     * @param chave chave de acesso da nota
     * @param tipoEvento tipo do evento da manifestacao do destinatario
     * @param motivo motivo do cancelamento
     * @param cnpj cnpj do autor do evento
     * @return dados da manifestacao do destinatario da nota retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public NFEnviaEventoRetorno manifestaDestinatarioNota(final String chave, final NFTipoEventoManifestacaoDestinatario tipoEvento, final String motivo, final String cnpj) throws Exception {
        return this.wSManifestacaoDestinatario.manifestaDestinatarioNota(chave, tipoEvento, motivo, cnpj);
    }

    /**
     * Faz a manifestação do destinatário da nota com evento ja assinado ATENCAO: Esse metodo deve ser utilizado para assinaturas A3
     * @param chave chave de acesso da nota
     * @param eventoAssinadoXml evento ja assinado em formato XML
     * @return dados da manifestacao do destinatario da nota retornado pelo webservice
     * @throws Exception caso nao consiga gerar o xml ou problema de conexao com o sefaz
     */
    public NFEnviaEventoRetorno manifestaDestinatarioNotaAssinada(final String chave, final String eventoAssinadoXml) throws Exception {
        return this.wSManifestacaoDestinatario.manifestaDestinatarioNotaAssinada(chave, eventoAssinadoXml);
    }

    /**
     * Faz consulta de distribuicao das notas fiscais. Pode ser feita pela chave de acesso ou utilizando o NSU (numero sequencial unico) da receita.
     * @param cnpj CNPJ da pessoa juridica a consultar
     * @param uf Unidade federativa da pessoa juridica a consultar
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