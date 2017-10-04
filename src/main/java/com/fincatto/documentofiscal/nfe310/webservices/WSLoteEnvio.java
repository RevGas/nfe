package com.fincatto.documentofiscal.nfe310.webservices;

import br.inf.portalfiscal.nfe.TEnviNFe;
import java.net.URL;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang3.StringUtils;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;
import org.w3c.dom.Element;

import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.nfe310.NFeConfig;
import com.fincatto.documentofiscal.assinatura.AssinaturaDigital;
import com.fincatto.documentofiscal.nfe310.classes.NFAutorizador31;
import com.fincatto.documentofiscal.nfe310.classes.lote.envio.NFLoteEnvio;
import com.fincatto.documentofiscal.nfe310.classes.lote.envio.NFLoteEnvioRetorno;
import com.fincatto.documentofiscal.nfe310.classes.lote.envio.NFLoteEnvioRetornoDados;
import com.fincatto.documentofiscal.nfe310.classes.nota.NFNota;
import com.fincatto.documentofiscal.nfe310.classes.nota.NFNotaInfoSuplementar;
import com.fincatto.nfe310.converters.ElementStringConverter;
import com.fincatto.documentofiscal.nfe310.utils.NFGeraChave;
import com.fincatto.documentofiscal.nfe310.utils.NFGeraQRCode;

import br.inf.portalfiscal.nfe.TRetEnviNFe;
import br.inf.portalfiscal.nfe.wsdl.nfeautorizacao.svan.NfeAutorizacao;
import br.inf.portalfiscal.nfe.wsdl.nfeautorizacao.svan.NfeAutorizacaoLoteResult;
import br.inf.portalfiscal.nfe.wsdl.nfeautorizacao.svan.NfeAutorizacaoSoap;
import br.inf.portalfiscal.nfe.wsdl.nfeautorizacao.svan.NfeCabecMsg;
import br.inf.portalfiscal.nfe.wsdl.nfeautorizacao.svan.NfeDadosMsg;
import com.fincatto.documentofiscal.parsers.DFParser;
import com.fincatto.documentofiscal.transformers.DFRegistryMatcher;
import com.fincatto.documentofiscal.validadores.xsd.XMLValidador;
import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

class WSLoteEnvio {

    private final NFeConfig config;

    WSLoteEnvio(final NFeConfig config) {
        this.config = config;
    }

    NFLoteEnvioRetorno enviaLoteAssinado(final String loteAssinadoXml, final DFModelo modelo) throws Exception {
        return new Persister(new DFRegistryMatcher(), new Format(0)).read(NFLoteEnvioRetorno.class, this.comunicaLote(loteAssinadoXml, modelo));
    }

    TRetEnviNFe enviaLoteAssinadoSincrono(final String loteAssinadoXml, final DFModelo modelo) throws Exception {
        return this.comunicaLotesincrono(loteAssinadoXml, modelo);
    }

    NFLoteEnvioRetornoDados enviaLote(final NFLoteEnvio lote) throws Exception {
        // adiciona a chave e o dv antes de assinar
        for (final NFNota nota : lote.getNotas()) {
            final NFGeraChave geraChave = new NFGeraChave(nota);
            nota.getInfo().getIdentificacao().setCodigoRandomico(StringUtils.defaultIfBlank(nota.getInfo().getIdentificacao().getCodigoRandomico(), geraChave.geraCodigoRandomico()));
            nota.getInfo().getIdentificacao().setDigitoVerificador(geraChave.getDV());
            nota.getInfo().setIdentificador(geraChave.getChaveAcesso());
        }

        // assina o lote
        final String documentoAssinado = new AssinaturaDigital(this.config).assinarDocumento(lote.toString());
        final NFLoteEnvio loteAssinado = new DFParser().loteParaObjeto(documentoAssinado);

        // verifica se nao tem NFCe junto com NFe no lote e gera qrcode (apos assinar mesmo, eh assim)
        int qtdNF = 0, qtdNFC = 0;
        for (final NFNota nota : loteAssinado.getNotas()) {
            switch (nota.getInfo().getIdentificacao().getModelo()) {
                case NFE:
                    qtdNF++;
                    break;
                case NFCE:
                    final NFGeraQRCode geraQRCode = new NFGeraQRCode(nota, this.config);
                    nota.setInfoSuplementar(new NFNotaInfoSuplementar());
                    nota.getInfoSuplementar().setQrCode(geraQRCode.getQRCode());
                    qtdNFC++;
                    break;
                default:
                    throw new IllegalArgumentException(String.format("Modelo de nota desconhecida: %s", nota.getInfo().getIdentificacao().getModelo()));
            }
        }

        // verifica se todas as notas do lote sao do mesmo modelo
        if ((qtdNF > 0) && (qtdNFC > 0)) {
            throw new IllegalArgumentException("Lote contendo notas de modelos diferentes!");
        }

        // guarda o modelo das notas
        final DFModelo modelo = qtdNFC > 0 ? DFModelo.NFCE : DFModelo.NFE;

        // comunica o lote
        final NFLoteEnvioRetorno loteEnvioRetorno = new Persister(new DFRegistryMatcher(), new Format(0)).read(NFLoteEnvioRetorno.class, this.comunicaLote(loteAssinado.toString(), modelo));
        return new NFLoteEnvioRetornoDados(loteEnvioRetorno, loteAssinado);
    }

    TRetEnviNFe enviaLoteSincrono(final NFLoteEnvio lote) throws Exception {
        // adiciona a chave e o dv antes de assinar
        for (final NFNota nota : lote.getNotas()) {
            final NFGeraChave geraChave = new NFGeraChave(nota);
            nota.getInfo().getIdentificacao().setCodigoRandomico(StringUtils.defaultIfBlank(nota.getInfo().getIdentificacao().getCodigoRandomico(), geraChave.geraCodigoRandomico()));
            nota.getInfo().getIdentificacao().setDigitoVerificador(geraChave.getDV());
            nota.getInfo().setIdentificador(geraChave.getChaveAcesso());
        }

        // assina o lote
        final String documentoAssinado = new AssinaturaDigital(this.config).assinarDocumento(lote.toString());
        final NFLoteEnvio loteAssinado = new DFParser().loteParaObjeto(documentoAssinado);

        // verifica se nao tem NFCe junto com NFe no lote e gera qrcode (apos assinar mesmo, eh assim)
        int qtdNF = 0, qtdNFC = 0;
        for (final NFNota nota : loteAssinado.getNotas()) {
            switch (nota.getInfo().getIdentificacao().getModelo()) {
                case NFE:
                    qtdNF++;
                    break;
                case NFCE:
                    final NFGeraQRCode geraQRCode = new NFGeraQRCode(nota, this.config);
                    nota.setInfoSuplementar(new NFNotaInfoSuplementar());
                    nota.getInfoSuplementar().setQrCode(geraQRCode.getQRCode());
                    qtdNFC++;
                    break;
                default:
                    throw new IllegalArgumentException(String.format("Modelo de nota desconhecida: %s", nota.getInfo().getIdentificacao().getModelo()));
            }
        }

        // verifica se todas as notas do lote sao do mesmo modelo
        if ((qtdNF > 0) && (qtdNFC > 0)) {
            throw new IllegalArgumentException("Lote contendo notas de modelos diferentes!");
        }

        // guarda o modelo das notas
        final DFModelo modelo = qtdNFC > 0 ? DFModelo.NFCE : DFModelo.NFE;

        // comunica o lote
        return this.comunicaLotesincrono(loteAssinado.toString(), modelo);
    }

    private String comunicaLote(final String loteAssinadoXml, final DFModelo modelo) throws Exception {
        //valida o lote assinado, para verificar se o xsd foi satisfeito, antes de comunicar com a sefaz
        XMLValidador.validaLote(loteAssinadoXml);

        //envia o lote para a sefaz
        final NfeCabecMsg nfeCabecMsg = new NfeCabecMsg();
        nfeCabecMsg.setCUF(this.config.getCUF().getCodigoIbge());
        nfeCabecMsg.setVersaoDados(NFeConfig.VERSAO);

        final NfeDadosMsg nfeDadosMsg = new NfeDadosMsg();
        nfeDadosMsg.getContent().add(ElementStringConverter.read(loteAssinadoXml));

        //define o tipo de emissao
        final NFAutorizador31 autorizador = NFAutorizador31.valueOfTipoEmissao(this.config.getTipoEmissao(), this.config.getCUF());

        final String endpoint = DFModelo.NFE.equals(modelo) ? autorizador.getNfeAutorizacao(this.config.getAmbiente()) : autorizador.getNfceAutorizacao(this.config.getAmbiente());
        if (endpoint == null) {
            throw new IllegalArgumentException("Nao foi possivel encontrar URL para Autorizacao " + modelo.name() + ", autorizador " + autorizador.name());
        }

        NfeAutorizacaoSoap port = new NfeAutorizacao(new URL(endpoint)).getNfeAutorizacaoSoap12();
        NfeAutorizacaoLoteResult result = port.nfeAutorizacaoLote(nfeDadosMsg, nfeCabecMsg);

        return ElementStringConverter.write((Element) result.getContent().get(0));
    }

    private TRetEnviNFe comunicaLotesincrono(final String loteAssinadoXml, final DFModelo modelo) throws Exception {
        //valida o lote assinado, para verificar se o xsd foi satisfeito, antes de comunicar com a sefaz
        XMLValidador.validaLote(loteAssinadoXml);

        //envia o lote para a sefaz
        final NfeCabecMsg nfeCabecMsg = new NfeCabecMsg();
        nfeCabecMsg.setCUF(this.config.getCUF().getCodigoIbge());
        nfeCabecMsg.setVersaoDados(NFeConfig.VERSAO);

        // Create the JAXBContext
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.nfe");
        
        Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(loteAssinadoXml);
        JAXBElement<TEnviNFe> tEnviNFe = (JAXBElement<TEnviNFe>) jaxbUnmarshaller.unmarshal(reader);
        
        final NfeDadosMsg nfeDadosMsg = new NfeDadosMsg();
        nfeDadosMsg.getContent().add(tEnviNFe);

        //define o tipo de emissao
        final NFAutorizador31 autorizador = NFAutorizador31.valueOfTipoEmissao(this.config.getTipoEmissao(), this.config.getCUF());

        final String endpoint = DFModelo.NFE.equals(modelo) ? autorizador.getNfeAutorizacao(this.config.getAmbiente()) : autorizador.getNfceAutorizacao(this.config.getAmbiente());
        if (endpoint == null) {
            throw new IllegalArgumentException("Nao foi possivel encontrar URL para Autorizacao " + modelo.name() + ", autorizador " + autorizador.name());
        }

        NfeAutorizacaoSoap port = new NfeAutorizacao(new URL(endpoint)).getNfeAutorizacaoSoap12();
        NfeAutorizacaoLoteResult result = port.nfeAutorizacaoLote(nfeDadosMsg, nfeCabecMsg);

        return ((JAXBElement<TRetEnviNFe>) result.getContent().get(0)).getValue();
    }

}
