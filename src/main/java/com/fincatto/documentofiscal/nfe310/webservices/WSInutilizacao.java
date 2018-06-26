package com.fincatto.documentofiscal.nfe310.webservices;

import br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao.ba.NfeInutilizacaoNFResult;
import java.math.BigDecimal;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.assinatura.AssinaturaDigital;
import com.fincatto.documentofiscal.nfe310.classes.NFAutorizador31;
import com.fincatto.documentofiscal.nfe310.classes.evento.inutilizacao.NFEnviaEventoInutilizacao;
import com.fincatto.documentofiscal.nfe310.classes.evento.inutilizacao.NFEventoInutilizacaoDados;
import com.fincatto.documentofiscal.nfe310.classes.evento.inutilizacao.NFRetornoEventoInutilizacao;
import com.fincatto.nfe310.converters.ElementStringConverter;
import com.fincatto.documentofiscal.persister.DFPersister;

import com.fincatto.documentofiscal.nfe.NFeConfig;
import javax.xml.ws.Holder;

class WSInutilizacao {

    private static final String VERSAO_SERVICO = "3.10";
    private static final String NOME_SERVICO = "INUTILIZAR";
    private final NFeConfig config;

    WSInutilizacao(final NFeConfig config) {
        this.config = config;
    }

    NFRetornoEventoInutilizacao inutilizaNotaAssinada(final String eventoAssinadoXml, final DFModelo modelo) throws Exception {
        return new DFPersister().read(NFRetornoEventoInutilizacao.class, efetuaInutilizacao(eventoAssinadoXml, modelo));
    }

    NFRetornoEventoInutilizacao inutilizaNota(final int anoInutilizacaoNumeracao, final String cnpjEmitente, final String serie, final String numeroInicial, final String numeroFinal, final String justificativa, final DFModelo modelo) throws Exception {
        final String inutilizacaoXML = this.geraDadosInutilizacao(anoInutilizacaoNumeracao, cnpjEmitente, serie, numeroInicial, numeroFinal, justificativa, modelo).toString();
        final String inutilizacaoXMLAssinado = new AssinaturaDigital(this.config).assinarDocumento(inutilizacaoXML);
        return new DFPersister().read(NFRetornoEventoInutilizacao.class, efetuaInutilizacao(inutilizacaoXMLAssinado, modelo));
    }

    private NFEnviaEventoInutilizacao geraDadosInutilizacao(final int anoInutilizacaoNumeracao, final String cnpjEmitente, final String serie, final String numeroInicial, final String numeroFinal, final String justificativa, final DFModelo modelo) {
        final NFEnviaEventoInutilizacao inutilizacao = new NFEnviaEventoInutilizacao();
        final NFEventoInutilizacaoDados dados = new NFEventoInutilizacaoDados();
        dados.setAmbiente(this.config.getAmbiente());
        dados.setAno(anoInutilizacaoNumeracao);
        dados.setCnpj(cnpjEmitente);
        dados.setJustificativa(justificativa);
        dados.setModeloDocumentoFiscal(modelo.getCodigo());
        dados.setNomeServico(WSInutilizacao.NOME_SERVICO);
        dados.setNumeroNFInicial(numeroInicial);
        dados.setNumeroNFFinal(numeroFinal);
        dados.setSerie(serie);
        dados.setUf(this.config.getCUF());
        final String numeroInicialTamanhoMaximo = StringUtils.leftPad(numeroInicial, 9, "0");
        final String numeroFinalTamanhoMaximo = StringUtils.leftPad(numeroFinal, 9, "0");
        final String serieTamanhoMaximo = StringUtils.leftPad(serie, 3, "0");
        dados.setIdentificador("ID" + this.config.getCUF().getCodigoIbge() + String.valueOf(anoInutilizacaoNumeracao) + cnpjEmitente + modelo.getCodigo() + serieTamanhoMaximo + numeroInicialTamanhoMaximo + numeroFinalTamanhoMaximo);

        inutilizacao.setVersao(new BigDecimal(WSInutilizacao.VERSAO_SERVICO));
        inutilizacao.setDados(dados);
        return inutilizacao;
    }

    private String efetuaInutilizacao(final String xml, final DFModelo modelo) throws Exception {
        Holder<br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao.ba.NfeCabecMsg> nfeCabecMsg = new Holder<>();
        nfeCabecMsg.value = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao.ba.NfeCabecMsg();
        nfeCabecMsg.value.setCUF(this.config.getCUF().getCodigoIbge());
        nfeCabecMsg.value.setVersaoDados(WSInutilizacao.VERSAO_SERVICO);
        
        final br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao.ba.NfeDadosMsg nfeDadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao.ba.NfeDadosMsg();
        nfeDadosMsg.getContent().add(ElementStringConverter.read(xml));

        final NFAutorizador31 autorizador = NFAutorizador31.valueOfCodigoUF(this.config.getCUF());
        final String endpoint = DFModelo.NFE.equals(modelo) ? autorizador.getNfeInutilizacao(this.config.getAmbiente()) : autorizador.getNfceInutilizacao(this.config.getAmbiente());
        if (endpoint == null) {
            throw new IllegalArgumentException("Nao foi possivel encontrar URL para Inutilizacao, autorizador " + autorizador.name());
        }

        br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao.ba.NfeInutilizacaoSoap port = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao.ba.NfeInutilizacao(new URL(endpoint)).getNfeInutilizacaoSoap();
        NfeInutilizacaoNFResult result = port.nfeInutilizacaoNF(nfeDadosMsg, nfeCabecMsg);

        return ElementStringConverter.write((Element) result.getContent().get(0));
    }

}
