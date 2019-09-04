package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.TRetEnviNFe;
import com.fincatto.documentofiscal.DFAmbiente;
import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.DFUnidadeFederativa;
import com.fincatto.documentofiscal.DFLog;
import com.fincatto.documentofiscal.nfe.NFTipoEmissao;
import com.fincatto.documentofiscal.nfe.NFeConfig;
import com.fincatto.documentofiscal.nfe400.classes.lote.envio.NFLoteEnvio;
import com.fincatto.documentofiscal.nfe400.classes.nota.NFNota;
import com.fincatto.documentofiscal.nfe400.classes.nota.NFNotaInfoSuplementar;
import com.fincatto.documentofiscal.nfe400.utils.NFGeraChave;
import com.fincatto.documentofiscal.nfe400.utils.qrcode20.NFGeraQRCode20;
import com.fincatto.documentofiscal.nfe400.utils.qrcode20.NFGeraQRCodeContingenciaOffline20;
import com.fincatto.documentofiscal.nfe400.utils.qrcode20.NFGeraQRCodeEmissaoNormal20;
import com.fincatto.documentofiscal.utils.DFAssinaturaDigital;
import com.fincatto.documentofiscal.utils.Util;
import com.fincatto.documentofiscal.validadores.XMLValidador;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.JAXBException;
import java.net.MalformedURLException;

class WSLoteEnvio implements DFLog {
    
    private static final String NFE_ELEMENTO = "NFe";
    private final NFeConfig config;
    
    WSLoteEnvio(final NFeConfig config) {
        this.config = config;
    }

    TRetEnviNFe enviaLoteAssinado(final String loteAssinadoXml, final DFModelo modelo, final DFAmbiente ambiente) throws Exception {
        return null;
    }

    TRetEnviNFe enviaLote(final NFLoteEnvio lote) throws Exception {
        final NFLoteEnvio loteAssinado = this.getLoteAssinado(lote);
        // comunica o lote
        final TRetEnviNFe loteEnvioRetorno = this.comunicaLote(loteAssinado.toString(), loteAssinado.getNotas().get(0).getInfo().getIdentificacao().getModelo(), loteAssinado.getNotas().get(0).getInfo().getIdentificacao().getAmbiente(),  loteAssinado.getNotas().get(0));
        return loteEnvioRetorno;
    }
    
    /**
     * Retorna o Lote assinado.
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
        final String documentoAssinado = new DFAssinaturaDigital(this.config).assinarDocumento(_lote);
        final NFLoteEnvio loteAssinado = this.config.getPersister().read(NFLoteEnvio.class, documentoAssinado);
        
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

    private TRetEnviNFe comunicaLote(final String loteAssinadoXml, final DFModelo modelo, final DFAmbiente ambiente, final NFNota nota) throws Exception {
        //valida o lote assinado, para verificar se o xsd foi satisfeito, antes de comunicar com a sefaz

        XMLValidador.validaLote400(loteAssinadoXml);

        return getTRetEnviNFe(modelo, this.config.getCUF(), loteAssinadoXml, ambiente, nota);
    }

    private TRetEnviNFe getTRetEnviNFe(final DFModelo modelo, final DFUnidadeFederativa uf, String loteAssinadoXml, DFAmbiente ambiente, NFNota nota) throws MalformedURLException, JAXBException, Exception {
        return com.fincatto.documentofiscal.nfe400.webservices.GatewayLoteEnvio.valueOfTipoEmissao(nota.getInfo().getIdentificacao().getTipoEmissao(), uf).getTRetEnviNFe(modelo, loteAssinadoXml, ambiente);
    }
    
    private NFGeraQRCode20 getNfGeraQRCode20(NFNota nota) {
        if (NFTipoEmissao.EMISSAO_NORMAL.equals(nota.getInfo().getIdentificacao().getTipoEmissao())) {
            return new NFGeraQRCodeEmissaoNormal20(nota, this.config);
        } else if (NFTipoEmissao.CONTIGENCIA_OFFLINE.equals(nota.getInfo().getIdentificacao().getTipoEmissao())) {
            return new NFGeraQRCodeContingenciaOffline20(nota, this.config);
        } else {
            throw new IllegalArgumentException("QRCode 2.0 Tipo Emissao nao implementado: " + nota.getInfo().getIdentificacao().getTipoEmissao().getDescricao());
        }
    }
    
}
