package com.fincatto.documentofiscal.utils.nf.qrcode20;

import br.inf.portalfiscal.nfe.TNFe;
import com.fincatto.documentofiscal.nfe.NFeConfig;
import com.fincatto.documentofiscal.nfe400.classes.nota.NFNota;
import com.fincatto.documentofiscal.utils.Util;

import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Implementação QRCode contingência offline (9) 2.0.
 */
public class TNFGeraQRCodeContingenciaOffline20 extends TNFGeraQRCode20 {

    public TNFGeraQRCodeContingenciaOffline20(final TNFe tnfe, String csc, String idCSC) {
        super(tnfe, csc, idCSC);
    }

    public String getQRCode() throws NoSuchAlgorithmException {
        String url = getUrlQRCode();

        // Monta os parametros do qrcode: http://www.sefazexemplo.gov.br/nfce/qrcode?p=28170800156225000131650110000151349562040824|2|1|02|60.90|797a4759685578312f5859597a6b7357422b6650523351633530633d|1|4615A93BB0D7C4E780F8D30EE77EDD5BA55C7D66
        final StringBuilder parametros = new StringBuilder();
        parametros.append(Util.chaveFromTNFe(tnfe)).append("|");                 // Chave de Acesso da NFC-e
        parametros.append(VERSAO_QRCODE).append("|");                                   // Versao do QRCode 2 = 2.0
        parametros.append(tnfe.getInfNFe().getIde().getTpAmb()).append("|");                // Ambiente
        parametros.append(tnfe.getInfNFe().getIde().getDhEmi()).append("|");                                      // Data e Hora de Emissão da NFC-e
        parametros.append(tnfe.getInfNFe().getTotal().getICMSTot().getVNF()).append("|"); // Valor Total da NFC-e
        parametros.append(toHex(new String(tnfe.getSignature().getSignedInfo().getReference().getDigestValue()))).append("|");// Digest Value da NFC-e
        parametros.append(idCSC);                   // Identificador do CSC – Codigo de Seguranca do Contribuinte no Banco de Dados da SEFAZ

        return url + "?p=" + parametros.toString() + "|" + createHash(parametros.toString(), csc);
    }

}