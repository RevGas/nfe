package com.fincatto.documentofiscal.utils.nf;

import br.inf.portalfiscal.nfe.TNFe;
import com.fincatto.documentofiscal.utils.Util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Created by Eldevan Nery Junior on 10/04/19.
 *
 *  Classe utilitária para criação de hash do CSRT(Código de Segurança do Responsável Técnico).
 *
 */
public class TNFeGeraHashCSRT {

    private final String chaveAcesso;
    private final String csrt;

    public TNFeGeraHashCSRT(final String chaveAcesso, final String csrt) {
        this.chaveAcesso = chaveAcesso;
        this.csrt = csrt;
    }

    public TNFeGeraHashCSRT(final TNFe tnfe) {
        this.chaveAcesso = Util.chaveFromTNFe(tnfe);
        this.csrt = tnfe.getInfNFe().getInfRespTec().getIdCSRT();
    }

    public String getHashCSRT() throws NoSuchAlgorithmException {
      return  base64EncodeToString(this.csrt
              + this.chaveAcesso);
    }

    public static String base64EncodeToString(String toEncode) throws NoSuchAlgorithmException {
        return Base64.getEncoder().encodeToString(getSha1(toEncode));
    }

    public static byte[] getSha1(String toEncode) throws NoSuchAlgorithmException {
        return MessageDigest.
                getInstance("SHA1").digest(toEncode.getBytes());
    }

    public static String getStringSha1(String toEncode) throws NoSuchAlgorithmException {
        return String.format("%040x", new BigInteger(1, TNFeGeraHashCSRT.getSha1(toEncode)));
    }


}