package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.model.evento_carta_correcao.Evento_CCe_PL_v101.ObjectFactory;
import br.inf.portalfiscal.nfe.model.evento_carta_correcao.Evento_CCe_PL_v101.TEnvEvento;
import br.inf.portalfiscal.nfe.model.evento_carta_correcao.Evento_CCe_PL_v101.TEvento;
import br.inf.portalfiscal.nfe.model.evento_carta_correcao.Evento_CCe_PL_v101.TRetEnvEvento;
import com.fincatto.documentofiscal.assinatura.AssinaturaDigital;
import com.fincatto.documentofiscal.nfe.NFeConfig;
import com.fincatto.documentofiscal.nfe400.classes.evento.NFEnviaEventoRetorno;
import com.fincatto.documentofiscal.nfe400.parsers.NotaFiscalChaveParser;
import java.io.StringWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

class WSCartaCorrecao {
    private static final BigDecimal VERSAO_LEIAUTE = new BigDecimal("1.00");
    private static final String EVENTO_CODIGO = "110110";
    private static final String EVENTO_DESCRICAO = "Carta de Correcao";
    private static final String EVENTO_CONDICAO_USO = "A Carta de Correcao e disciplinada pelo paragrafo 1o-A do art. 7o do Convenio S/N, de 15 de dezembro de 1970 e pode ser utilizada para regularizacao de erro ocorrido na emissao de documento fiscal, desde que o erro nao esteja relacionado com: I - as variaveis que determinam o valor do imposto tais como: base de calculo, aliquota, diferenca de preco, quantidade, valor da operacao ou da prestacao; II - a correcao de dados cadastrais que implique mudanca do remetente ou do destinatario; III - a data de emissao ou de saida.";
    private final static Logger LOGGER = LoggerFactory.getLogger(WSCartaCorrecao.class);
    private final NFeConfig config;

    WSCartaCorrecao(final NFeConfig config) {
        this.config = config;
    }

    NFEnviaEventoRetorno corrigeNotaAssinada(final String xmlAssinado) throws Exception {
//        final OMElement omElementResult = this.efetuaCorrecao(xmlAssinado, new DFParser()
//                .enviaEventoCartaCorrecaoParaObjeto(xmlAssinado).getEvento().get(0).getInfoEvento().getChave());
//        return new Persister(new DFRegistryMatcher(), new Format(0)).read(NFEnviaEventoRetorno.class, omElementResult.toString());
        return null;
    }

    TRetEnvEvento corrigeNota(final String chNFe, final String xCorrecao, final int nSeqEvento) throws Exception {
        String xml = this.gerarDados(chNFe, xCorrecao);
        xml = xml.replace("xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", "");
        final String xmlAssinado = new AssinaturaDigital(this.config).assinarDocumento(xml);
        return efetua(xmlAssinado, chNFe);
    }

    private TRetEnvEvento efetua(final String xml, final String chaveAcesso) throws Exception {
        final NotaFiscalChaveParser chaveParser = new NotaFiscalChaveParser(chaveAcesso);
        return com.fincatto.documentofiscal.nfe400.webservices.GatewayCartaCorrecao.valueOfCodigoUF(chaveParser.getNFUnidadeFederativa()).getTRetEnvEvento(chaveParser.getModelo(), xml, this.config.getAmbiente());
    }

    private String gerarDados(final String chaveAcesso, final String motivo) throws JAXBException {
        final NotaFiscalChaveParser chaveParser = new NotaFiscalChaveParser(chaveAcesso);

        TEvento.InfEvento.DetEvento detEvento = new TEvento.InfEvento.DetEvento();
        detEvento.setVersao(WSCartaCorrecao.VERSAO_LEIAUTE.toString());
        detEvento.setDescEvento(WSCartaCorrecao.EVENTO_DESCRICAO);
        detEvento.setXCondUso(EVENTO_CONDICAO_USO);
        detEvento.setXCorrecao(motivo);
                
        final TEvento.InfEvento infoEvento = new TEvento.InfEvento();
        infoEvento.setTpAmb(this.config.getAmbiente().getCodigo());
        infoEvento.setChNFe(chaveAcesso);
        infoEvento.setCNPJ(chaveParser.getCnpjEmitente());
        infoEvento.setDhEvento(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").format(LocalDateTime.now())); //TODO
        infoEvento.setDhEvento(infoEvento.getDhEvento() + "-03:00");
        infoEvento.setId(String.format("ID%s%s0%s", WSCartaCorrecao.EVENTO_CODIGO, chaveAcesso, "1"));
        infoEvento.setNSeqEvento("1");
        infoEvento.setCOrgao(chaveParser.getNFUnidadeFederativa().getCodigoIbge());
        infoEvento.setTpEvento(WSCartaCorrecao.EVENTO_CODIGO);
        infoEvento.setVerEvento(WSCartaCorrecao.VERSAO_LEIAUTE.toString());
        infoEvento.setDetEvento(detEvento);

        final TEvento evento = new TEvento();
        evento.setInfEvento(infoEvento);
        evento.setVersao(WSCartaCorrecao.VERSAO_LEIAUTE.toString());

        final TEnvEvento enviaEvento = new TEnvEvento();
        enviaEvento.getEvento().add(evento);
        enviaEvento.setIdLote(Long.toString(ZonedDateTime.now(this.config.getTimeZone().toZoneId()).toInstant().toEpochMilli()));
        enviaEvento.setVersao(WSCartaCorrecao.VERSAO_LEIAUTE.toString());
        
        JAXBContext context = JAXBContext.newInstance(TEnvEvento.class);
        Marshaller marshaller = context.createMarshaller();

        JAXBElement<TEnvEvento> tEnvEvento = new ObjectFactory().createEnvEvento(enviaEvento);
        
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(tEnvEvento, stringWriter);
        return stringWriter.toString();
    }    
}
