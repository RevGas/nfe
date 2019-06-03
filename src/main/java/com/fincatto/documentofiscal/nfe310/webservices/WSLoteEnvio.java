package com.fincatto.documentofiscal.nfe310.webservices;

import br.inf.portalfiscal.nfe.TRetEnviNFe;
import com.fincatto.documentofiscal.DFLog;
import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.nfe.NFeConfig;
import com.fincatto.documentofiscal.nfe310.classes.NFAutorizador31;
import com.fincatto.documentofiscal.nfe310.classes.lote.envio.NFLoteEnvio;
import com.fincatto.documentofiscal.nfe310.classes.lote.envio.NFLoteEnvioRetorno;
import com.fincatto.documentofiscal.nfe310.classes.lote.envio.NFLoteEnvioRetornoDados;
import com.fincatto.documentofiscal.nfe310.classes.nota.NFNota;
import com.fincatto.documentofiscal.nfe310.classes.nota.NFNotaInfoSuplementar;
import com.fincatto.documentofiscal.nfe310.utils.NFGeraChave;
import com.fincatto.documentofiscal.nfe310.utils.NFGeraQRCode;

import com.fincatto.documentofiscal.utils.DFAssinaturaDigital;
import com.fincatto.documentofiscal.validadores.XMLValidador;
import org.apache.commons.lang3.StringUtils;

import org.simpleframework.xml.core.Persister;

class WSLoteEnvio implements DFLog {
    
    private static final String NFE_ELEMENTO = "NFe";
    private final NFeConfig config;
    
    WSLoteEnvio(final NFeConfig config) {
        this.config = config;
    }
    
//    NFLoteEnvioRetorno enviaLoteAssinado(final String loteAssinadoXml, final DFModelo modelo) throws Exception {
//        return new Persister(new DFRegistryMatcher(), new Format(0)).read(NFLoteEnvioRetorno.class, this.comunicaLote(loteAssinadoXml, modelo));
//    }

//    TRetEnviNFe enviaLoteAssinadoSincrono(final String loteAssinadoXml, final DFModelo modelo) throws Exception {
//        return this.comunicaLotesincrono(loteAssinadoXml, modelo);
//    }
    
    NFLoteEnvioRetornoDados enviaLote(final NFLoteEnvio lote) throws Exception {
        // adiciona a chave e o dv antes de assinar
        for (final NFNota nota : lote.getNotas()) {
            final NFGeraChave geraChave = new NFGeraChave(nota);
            nota.getInfo().getIdentificacao().setCodigoRandomico(StringUtils.defaultIfBlank(nota.getInfo().getIdentificacao().getCodigoRandomico(), geraChave.geraCodigoRandomico()));
            nota.getInfo().getIdentificacao().setDigitoVerificador(geraChave.getDV());
            nota.getInfo().setIdentificador(geraChave.getChaveAcesso());
        }
        // assina o lote
        final String documentoAssinado = new DFAssinaturaDigital(this.config).assinarDocumento(lote.toString());
        final NFLoteEnvio loteAssinado = new Persister().read(NFLoteEnvio.class, documentoAssinado);

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
//        final NFLoteEnvioRetorno loteEnvioRetorno = new Persister(new DFRegistryMatcher(), new Format(0)).read(NFLoteEnvioRetorno.class, this.comunicaLote(loteAssinado.toString(), modelo));
//        return new NFLoteEnvioRetornoDados(loteEnvioRetorno, loteAssinado);
        return null;
    }
    
    /**
     * Retorna o Lote assinado.
     */
    TRetEnviNFe getLoteAssinado(final NFLoteEnvio lote) throws Exception {
        // adiciona a chave e o dv antes de assinar
        for (final NFNota nota : lote.getNotas()) {
            final NFGeraChave geraChave = new NFGeraChave(nota);
            nota.getInfo().getIdentificacao().setCodigoRandomico(StringUtils.defaultIfBlank(nota.getInfo().getIdentificacao().getCodigoRandomico(), geraChave.geraCodigoRandomico()));
            nota.getInfo().getIdentificacao().setDigitoVerificador(geraChave.getDV());
            nota.getInfo().setIdentificador(geraChave.getChaveAcesso());
        }

        // assina o lote
        final String documentoAssinado = new DFAssinaturaDigital(this.config).assinarDocumento(lote.toString());
        //final NFLoteEnvio loteAssinado = new DFParser().loteParaObjeto(documentoAssinado);
        final NFLoteEnvio loteAssinado = this.config.getPersister().read(NFLoteEnvio.class, documentoAssinado);
        
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
//        return this.comunicaLotesincrono(loteAssinado.toString(), modelo);
        return null;
    }
    
    private NFLoteEnvioRetorno comunicaLote(final String loteAssinadoXml, final DFModelo modelo) throws Exception {
        // valida o lote assinado, para verificar se o xsd foi satisfeito, antes de comunicar com a sefaz
        XMLValidador.validaLote(loteAssinadoXml);
        
        // envia o lote para a sefaz
//        final OMElement omElement = this.nfeToOMElement(loteAssinadoXml);
        
//        final NfeDadosMsg dados = new NfeDadosMsg();
//        dados.setExtraElement(omElement);
        
//        final NfeCabecMsgE cabecalhoSOAP = this.getCabecalhoSOAP();
//        this.getLogger().debug(omElement.toString());
        
        // define o tipo de emissao
        final NFAutorizador31 autorizador = NFAutorizador31.valueOfTipoEmissao(this.config.getTipoEmissao(), this.config.getCUF());
        
        final String endpoint = DFModelo.NFE.equals(modelo) ? autorizador.getNfeAutorizacao(this.config.getAmbiente()) : autorizador.getNfceAutorizacao(this.config.getAmbiente());
        if (endpoint == null) {
            throw new IllegalArgumentException("Nao foi possivel encontrar URL para Autorizacao " + modelo.name() + ", autorizador " + autorizador.name());
        }
        
//        final NfeAutorizacaoLoteResult autorizacaoLoteResult = new NfeAutorizacaoStub(endpoint).nfeAutorizacaoLote(dados, cabecalhoSOAP);
//        final NFLoteEnvioRetorno loteEnvioRetorno = this.config.getPersister().read(NFLoteEnvioRetorno.class, autorizacaoLoteResult.getExtraElement().toString());
//        this.getLogger().debug(loteEnvioRetorno.toString());
//        return loteEnvioRetorno;
        return null;
    }

}
