package com.fincatto.documentofiscal.mdfe3.webservices;

import com.fincatto.documentofiscal.DFUnidadeFederativa;
import com.fincatto.documentofiscal.mdfe3.MDFeConfig;
import com.fincatto.documentofiscal.mdfe3.classes.nota.evento.MDFeDetalhamentoEvento;
import com.fincatto.documentofiscal.mdfe3.classes.nota.evento.MDFeEnviaEventoEncerramento;
import com.fincatto.documentofiscal.mdfe3.classes.nota.evento.MDFeEvento;
import com.fincatto.documentofiscal.mdfe3.classes.nota.evento.MDFeInfoEvento;
import com.fincatto.documentofiscal.mdfe3.classes.nota.evento.MDFeRetorno;
import com.fincatto.documentofiscal.mdfe3.classes.parsers.MDFChaveParser;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * Created by Eldevan Nery Junior on 17/11/17.
 */
class WSEncerramento {
    private static final String DESCRICAO_EVENTO = "Encerramento";
    private static final BigDecimal VERSAO_LEIAUTE = new BigDecimal("3.00");
    private static final String EVENTO_ENCERRAMENTO = "110112";
    private static final Logger LOGGER = LoggerFactory.getLogger(WSEncerramento.class);
    private final MDFeConfig config;

    WSEncerramento(final MDFeConfig config) {
        this.config = config;
    }

    MDFeRetorno encerramentoMdfeAssinado(final String chaveAcesso, final String eventoAssinadoXml) throws Exception {
        throw new UnsupportedOperationException("Nao suportado ainda");
    }

    MDFeRetorno encerraMdfe(final String chaveAcesso, final String numeroProtocolo
            , final String codigoMunicipio, final LocalDate dataEncerramento, final DFUnidadeFederativa unidadeFederativa) throws Exception {
        throw new UnsupportedOperationException("Nao suportado ainda");
    }

    private String efetuaEncerramento(final String xmlAssinado, final String chaveAcesso) throws Exception {
        throw new UnsupportedOperationException("Nao suportado ainda");
    }

    private MDFeEvento gerarDadosEncerramento(final String chaveAcesso, final String numeroProtocolo
            , final String codigoMunicipio, final LocalDate dataEncerramento, final DFUnidadeFederativa unidadeFederativa) {

        final MDFChaveParser chaveParser = new MDFChaveParser(chaveAcesso);

        final MDFeEnviaEventoEncerramento encerramento = new MDFeEnviaEventoEncerramento();
        encerramento.setDescricaoEvento(WSEncerramento.DESCRICAO_EVENTO);
        encerramento.setCodigoMunicipio(codigoMunicipio);
        encerramento.setDataEncerramento(dataEncerramento);
        encerramento.setUf(unidadeFederativa);
        encerramento.setProtocoloAutorizacao(numeroProtocolo);

        final MDFeDetalhamentoEvento mdFeDetalhamentoEvento = new MDFeDetalhamentoEvento();
        mdFeDetalhamentoEvento.setEnviaEventoEncerramento(encerramento);
        mdFeDetalhamentoEvento.setVersaoEvento(WSEncerramento.VERSAO_LEIAUTE);

        final MDFeInfoEvento infoEvento = new MDFeInfoEvento();
        infoEvento.setAmbiente(this.config.getAmbiente());
        infoEvento.setChave(chaveAcesso);
        infoEvento.setCnpj(chaveParser.getCnpjEmitente());
        infoEvento.setDataHoraEvento(DateTime.now());
        infoEvento.setId(String.format("ID%s%s0%s", WSEncerramento.EVENTO_ENCERRAMENTO, chaveAcesso, "1"));
        infoEvento.setNumeroSequencialEvento(1);
        infoEvento.setOrgao(chaveParser.getNFUnidadeFederativa().getCodigoIbge());
        infoEvento.setCodigoEvento(WSEncerramento.EVENTO_ENCERRAMENTO);
        infoEvento.setDetEvento(mdFeDetalhamentoEvento);

        final MDFeEvento mdfeEventoEncerramento = new MDFeEvento();
        mdfeEventoEncerramento.setInfoEvento(infoEvento);
        mdfeEventoEncerramento.setVersao(WSEncerramento.VERSAO_LEIAUTE);

        return mdfeEventoEncerramento;
    }
}
