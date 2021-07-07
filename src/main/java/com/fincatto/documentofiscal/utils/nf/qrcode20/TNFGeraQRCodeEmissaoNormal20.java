package com.fincatto.documentofiscal.utils.nf.qrcode20;

import br.inf.portalfiscal.nfe.TNFe;
import com.fincatto.documentofiscal.nfe.NFeConfig;
import com.fincatto.documentofiscal.nfe400.classes.nota.NFNota;
import com.fincatto.documentofiscal.utils.Util;

import java.security.NoSuchAlgorithmException;

/**
 * Implementação geração qrcode emissão normal (1).
 */
public class TNFGeraQRCodeEmissaoNormal20 extends TNFGeraQRCode20 {

    public TNFGeraQRCodeEmissaoNormal20(final TNFe tnfe, String csc, String idCSC) {
        super(tnfe, csc, idCSC);
    }

    public String getQRCode() throws NoSuchAlgorithmException {
        String url = getUrlQRCode();

        final StringBuilder parametros = new StringBuilder();
        parametros.append(Util.chaveFromTNFe(tnfe)).append("|");    // Chave de Acesso da NFC-e
        parametros.append(VERSAO_QRCODE).append("|");                      // Versao do QRCode
        parametros.append(tnfe.getInfNFe().getIde().getTpAmb()).append("|");   // Tipo de Ambiente Homolog / Producao
        parametros.append(idCSC);      // Identificador do CSC – Codigo de Seguranca do Contribuinte no Banco de Dados da SEFAZ

        return url + "?p=" + parametros.toString() + "|" + createHash(parametros.toString(), csc);
    }

}