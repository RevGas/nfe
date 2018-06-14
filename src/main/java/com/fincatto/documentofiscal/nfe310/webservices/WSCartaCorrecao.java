package com.fincatto.documentofiscal.nfe310.webservices;

import java.net.MalformedURLException;
import java.net.URL;

import org.w3c.dom.Element;

import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.assinatura.AssinaturaDigital;
import com.fincatto.documentofiscal.nfe310.classes.NFAutorizador31;
import com.fincatto.documentofiscal.nfe310.classes.evento.NFEnviaEventoRetorno;
import com.fincatto.documentofiscal.nfe310.classes.evento.NFEvento;
import com.fincatto.documentofiscal.nfe310.classes.evento.NFInfoEvento;
import com.fincatto.documentofiscal.nfe310.classes.evento.NFTipoEvento;
import com.fincatto.documentofiscal.nfe310.classes.evento.cartacorrecao.NFEnviaEventoCartaCorrecao;
import com.fincatto.nfe310.converters.ElementStringConverter;
import com.fincatto.documentofiscal.nfe310.parsers.NotaFiscalChaveParser;
import com.fincatto.documentofiscal.persister.DFPersister;
import com.fincatto.documentofiscal.transformers.DFRegistryMatcher;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;

import javax.xml.stream.XMLStreamException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.time.ZonedDateTime;
import java.util.Collections;

import br.inf.portalfiscal.nfe.wsdl.recepcaoevento.svan.NfeCabecMsg;
import br.inf.portalfiscal.nfe.wsdl.recepcaoevento.svan.NfeDadosMsg;
import br.inf.portalfiscal.nfe.wsdl.recepcaoevento.svan.NfeRecepcaoEventoResult;
import br.inf.portalfiscal.nfe.wsdl.recepcaoevento.svan.RecepcaoEvento;
import br.inf.portalfiscal.nfe.wsdl.recepcaoevento.svan.RecepcaoEventoSoap;
import com.fincatto.documentofiscal.nfe.NFeConfig;

class WSCartaCorrecao {

    private static final BigDecimal VERSAO_LEIAUTE = new BigDecimal("1.00");
    private static final String EVENTO_CODIGO = "110110";
    private static final String EVENTO_DESCRICAO = "Carta de Correcao";
    private static final String EVENTO_CONDICAO_USO = "A Carta de Correcao e disciplinada pelo paragrafo 1o-A do art. 7o do Convenio S/N, de 15 de dezembro de 1970 e pode ser utilizada para regularizacao de erro ocorrido na emissao de documento fiscal, desde que o erro nao esteja relacionado com: I - as variaveis que determinam o valor do imposto tais como: base de calculo, aliquota, diferenca de preco, quantidade, valor da operacao ou da prestacao; II - a correcao de dados cadastrais que implique mudanca do remetente ou do destinatario; III - a data de emissao ou de saida.";
    private final NFeConfig config;

    WSCartaCorrecao(final NFeConfig config) {
        this.config = config;
    }

    NFEnviaEventoRetorno corrigeNota(final String chaveAcesso, final String textoCorrecao, final int numeroSequencialEvento) throws Exception {
        final String cartaCorrecaoXML = this.gerarDadosCartaCorrecao(chaveAcesso, textoCorrecao, numeroSequencialEvento).toString();
        final String xmlAssinado = new AssinaturaDigital(this.config).assinarDocumento(cartaCorrecaoXML);
        return new Persister(new DFRegistryMatcher(), new Format(0)).read(NFEnviaEventoRetorno.class, efetuaCorrecao(xmlAssinado, chaveAcesso));
    }

    NFEnviaEventoRetorno corrigeNotaAssinada(final String eventoAssinadoXml, final String chaveAcesso) throws Exception {
        return new DFPersister().read(NFEnviaEventoRetorno.class, efetuaCorrecao(eventoAssinadoXml, chaveAcesso));
    }

    private NFEnviaEventoCartaCorrecao gerarDadosCartaCorrecao(final String chaveAcesso, final String textoCorrecao, final int numeroSequencialEvento) {
        final NotaFiscalChaveParser chaveParser = new NotaFiscalChaveParser(chaveAcesso);

        final NFTipoEvento cartaCorrecao = new NFTipoEvento();
        cartaCorrecao.setVersao(WSCartaCorrecao.VERSAO_LEIAUTE);
        cartaCorrecao.setDescricaoEvento(WSCartaCorrecao.EVENTO_DESCRICAO);
        cartaCorrecao.setCondicaoUso(WSCartaCorrecao.EVENTO_CONDICAO_USO);
        cartaCorrecao.setTextoCorrecao(textoCorrecao);

        final NFInfoEvento infoEvento = new NFInfoEvento();
        infoEvento.setAmbiente(this.config.getAmbiente());
        infoEvento.setDadosEvento(cartaCorrecao);
        infoEvento.setChave(chaveAcesso);
        infoEvento.setCnpj(chaveParser.getCnpjEmitente());
        infoEvento.setDataHoraEvento(ZonedDateTime.now(this.config.getTimeZone().toZoneId()));
        infoEvento.setId(String.format("ID%s%s%02d", WSCartaCorrecao.EVENTO_CODIGO, chaveAcesso, numeroSequencialEvento));
        infoEvento.setNumeroSequencialEvento(numeroSequencialEvento);
        infoEvento.setOrgao(chaveParser.getNFUnidadeFederativa());
        infoEvento.setTipoEvento(WSCartaCorrecao.EVENTO_CODIGO);
        infoEvento.setVersaoEvento(WSCartaCorrecao.VERSAO_LEIAUTE);

        final NFEvento evento = new NFEvento();
        evento.setInfoEvento(infoEvento);
        evento.setVersao(WSCartaCorrecao.VERSAO_LEIAUTE);

        final NFEnviaEventoCartaCorrecao enviaEvento = new NFEnviaEventoCartaCorrecao();
        enviaEvento.setEvento(Collections.singletonList(evento));
        enviaEvento.setIdLote(Long.toString(ZonedDateTime.now(this.config.getTimeZone().toZoneId()).toInstant().toEpochMilli()));
        enviaEvento.setVersao(WSCartaCorrecao.VERSAO_LEIAUTE);
        return enviaEvento;
    }

    private String efetuaCorrecao(final String xml, final String chaveAcesso) throws XMLStreamException, RemoteException, MalformedURLException {
        final NfeCabecMsg nfeCabecMsg = new NfeCabecMsg();
        nfeCabecMsg.setCUF(this.config.getCUF().getCodigoIbge());
        nfeCabecMsg.setVersaoDados(WSCartaCorrecao.VERSAO_LEIAUTE.toPlainString());

        final NfeDadosMsg nfeDadosMsg = new NfeDadosMsg();
        nfeDadosMsg.getContent().add(ElementStringConverter.read(xml));

        final NotaFiscalChaveParser parser = new NotaFiscalChaveParser(chaveAcesso);

        final NFAutorizador31 autorizacao = NFAutorizador31.valueOfCodigoUF(this.config.getCUF());
        final String endpoint = DFModelo.NFCE.equals(parser.getModelo()) ? autorizacao.getNfceRecepcaoEvento(this.config.getAmbiente()) : autorizacao.getRecepcaoEvento(this.config.getAmbiente());
        if (endpoint == null) {
            throw new IllegalArgumentException("Nao foi possivel encontrar URL para RecepcaoEvento " + parser.getModelo().name() + ", autorizador " + autorizacao.name());
        }

        RecepcaoEventoSoap port = new RecepcaoEvento(new URL(endpoint)).getRecepcaoEventoSoap12();
        NfeRecepcaoEventoResult result = port.nfeRecepcaoEvento(nfeDadosMsg, nfeCabecMsg);

        return ElementStringConverter.write((Element) result.getContent().get(0));
    }
}
