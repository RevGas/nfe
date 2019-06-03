package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.TRetEnviNFe;
import com.fincatto.documentofiscal.DFAmbiente;
import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.DFUnidadeFederativa;
import com.fincatto.documentofiscal.assinatura.AssinaturaDigital;
import com.fincatto.documentofiscal.nfe.NFTipoEmissao;
import com.fincatto.documentofiscal.nfe.NFeConfig;
import com.fincatto.documentofiscal.nfe400.classes.lote.envio.NFLoteEnvio;
import com.fincatto.documentofiscal.nfe400.classes.nota.NFNota;
import com.fincatto.documentofiscal.nfe400.classes.nota.NFNotaInfoSuplementar;
import com.fincatto.documentofiscal.nfe400.parsers.DFParser;
import com.fincatto.documentofiscal.nfe400.utils.NFGeraChave;
import com.fincatto.documentofiscal.nfe400.utils.qrcode20.NFGeraQRCode20;
import com.fincatto.documentofiscal.nfe400.utils.qrcode20.NFGeraQRCodeContingenciaOffline20;
import com.fincatto.documentofiscal.nfe400.utils.qrcode20.NFGeraQRCodeEmissaoNormal20;
import com.fincatto.documentofiscal.utils.Util;
import com.fincatto.documentofiscal.validadores.xsd.XMLValidador;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import java.net.MalformedURLException;

class WSLoteEnvio {

    private static final String NFE_ELEMENTO = "NFe";
    private static final Logger LOGGER = LoggerFactory.getLogger(WSLoteEnvio.class);
    private final NFeConfig config;

    WSLoteEnvio(final NFeConfig config) {
        this.config = config;
    }

    TRetEnviNFe enviaLoteAssinado(final String loteAssinadoXml, final DFModelo modelo, final DFAmbiente ambiente) throws Exception {
        return this.comunicaLote(loteAssinadoXml, modelo, ambiente);
    }

    TRetEnviNFe enviaLote(final NFLoteEnvio lote) throws Exception {
        final NFLoteEnvio loteAssinado = this.getLoteAssinado(lote);
        // comunica o lote
        final TRetEnviNFe loteEnvioRetorno = this.comunicaLote(loteAssinado.toString(), loteAssinado.getNotas().get(0).getInfo().getIdentificacao().getModelo(), loteAssinado.getNotas().get(0).getInfo().getIdentificacao().getAmbiente());
        return loteEnvioRetorno;
    }

    /**
     * Retorna o Lote assinado.
     * @param lote
     * @return
     * @throws Exception
     */
    NFLoteEnvio getLoteAssinado(final NFLoteEnvio lote) throws Exception {
        // adiciona a chave e o dv antes de assinar
        for (final NFNota nota : lote.getNotas()) {
            final NFGeraChave geraChave = new NFGeraChave(nota);
            nota.getInfo().getIdentificacao().setCodigoRandomico(StringUtils.defaultIfBlank(nota.getInfo().getIdentificacao().getCodigoRandomico(), geraChave.geraCodigoRandomico()));
            nota.getInfo().getIdentificacao().setDigitoVerificador(geraChave.getDV());
            nota.getInfo().setIdentificador(geraChave.getChaveAcesso());
        }
        //Remover caracteres especiais do xml para o autorizador MT
        String _lote = lote.toString();
        if (lote.getNotas().get(0).getInfo().getEmitente().getEndereco().getUf().equals(DFUnidadeFederativa.MT.getCodigo())) {
             _lote = Util.convertToASCII2(_lote);
        }
        // assina o lote
        final String documentoAssinado = new AssinaturaDigital(this.config).assinarDocumento(_lote);
        final NFLoteEnvio loteAssinado = new DFParser().loteParaObjeto(documentoAssinado);

        // verifica se nao tem NFCe junto com NFe no lote e gera qrcode (apos assinar mesmo, eh assim)
        int qtdNF = 0, qtdNFC = 0;
        for (final NFNota nota : loteAssinado.getNotas()) {
            switch (nota.getInfo().getIdentificacao().getModelo()) {
                case NFE:
                    qtdNF++;
                    break;
                case NFCE:
                    NFGeraQRCode20 geraQRCode = getNfGeraQRCode20(nota);

                    nota.setInfoSuplementar(new NFNotaInfoSuplementar());
                    nota.getInfoSuplementar().setUrlConsultaChaveAcesso(geraQRCode.urlConsultaChaveAcesso());
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
        return loteAssinado;
    }

    private TRetEnviNFe comunicaLote(final String loteAssinadoXml, final DFModelo modelo, final DFAmbiente ambiente) throws Exception {
        //valida o lote assinado, para verificar se o xsd foi satisfeito, antes de comunicar com a sefaz

        XMLValidador.validaLote400(loteAssinadoXml);

        return getTRetEnviNFe(modelo, this.config.getCUF(), loteAssinadoXml, ambiente);
    }

    private TRetEnviNFe getTRetEnviNFe(final DFModelo modelo, final DFUnidadeFederativa uf, String loteAssinadoXml, DFAmbiente ambiente) throws MalformedURLException, JAXBException, Exception {
        return com.fincatto.documentofiscal.nfe400.webservices.GatewayLoteEnvio.valueOfCodigoUF(uf).getTRetEnviNFe(modelo, loteAssinadoXml, ambiente);
    }
    
    private NFGeraQRCode20 getNfGeraQRCode20(NFNota nota) {

        NFGeraQRCode20 geraQRCode;

        if (NFTipoEmissao.EMISSAO_NORMAL.equals(nota.getInfo().getIdentificacao().getTipoEmissao())) {
            geraQRCode = new NFGeraQRCodeEmissaoNormal20(nota, this.config);
        } else if (NFTipoEmissao.CONTIGENCIA_OFFLINE.equals(nota.getInfo().getIdentificacao().getTipoEmissao())){
            geraQRCode = new NFGeraQRCodeContingenciaOffline20(nota, this.config);
        }else {
            throw new IllegalArgumentException("QRCode 2.0 Tipo Emissao não implementado: " + nota.getInfo().getIdentificacao().getTipoEmissao().getDescricao() );
        }
        return geraQRCode;
    }
    
}
