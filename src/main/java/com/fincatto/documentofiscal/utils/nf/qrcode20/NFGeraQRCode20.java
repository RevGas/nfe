package com.fincatto.documentofiscal.utils.nf.qrcode20;

import br.inf.portalfiscal.nfe.TNFe;
import com.fincatto.documentofiscal.DFAmbiente;
import com.fincatto.documentofiscal.DFUnidadeFederativa;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe abstrata para a implementação da geração do QRCode 2.0.
 *
 * Deve ser feita a implementação para emissão normal (1) e para contingência offline (9).
 */
public abstract class NFGeraQRCode20 {

    public static final String VERSAO_QRCODE = "2";

    protected final TNFe tnfe;
    protected final String csc;
    protected final String idCSC;

    public NFGeraQRCode20(TNFe tnfe, String csc, String idCSC) {
        this.tnfe = tnfe;
        this.csc = csc;
        this.idCSC = idCSC;
    }

    /**
     * Método responsável pela geração do qrcode.
     *
     * @return URL para consulta da nota via qrcode20.
     * @throws NoSuchAlgorithmException
     */
    public abstract String getQRCode() throws NoSuchAlgorithmException;

    public String getUrlQRCode(){
        String url = this.tnfe.getInfNFe().getIde().getTpAmb().equals(DFAmbiente.PRODUCAO.getCodigo()) ? DFUnidadeFederativa.valueOfCodigo(this.tnfe.getInfNFe().getIde().getCUF()).getQrCodeProducao() : DFUnidadeFederativa.valueOfCodigo(this.tnfe.getInfNFe().getIde().getCUF()).getQrCodeHomologacao();

        if (StringUtils.isBlank(url)) {
            throw new IllegalArgumentException("URL para consulta do QRCode nao informada para uf " + DFUnidadeFederativa.valueOfCodigo(this.tnfe.getInfNFe().getIde().getCUF()).getCodigo() + "!");
        }

        if (StringUtils.isBlank(csc)) {
            throw new IllegalArgumentException("CSC nao informado nas configuracoes!");
        }

        if ((idCSC == null) || (idCSC == "0")) {
            throw new IllegalArgumentException("IdCSC nao informado nas configuracoes!");
        }

        return url;
    }

    public static String createHash(final String campos, final String csc) throws NoSuchAlgorithmException {
        return sha1(campos + csc);
    }

    public static String toHex(final String arg) {
        return String.format("%040x", new BigInteger(1, arg.getBytes(Charset.forName("UTF-8"))));
    }

    public static String sha1(final String input) throws NoSuchAlgorithmException {
        final StringBuilder sb = new StringBuilder();
        for (final byte element : MessageDigest.getInstance("SHA1").digest(input.getBytes(Charset.forName("UTF-8")))) {
            sb.append(Integer.toString((element & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString().toUpperCase();
    }

    public String urlConsultaChaveAcesso(){
        return this.tnfe.getInfNFe().getIde().getTpAmb().equals(DFAmbiente.PRODUCAO.getCodigo()) ? DFUnidadeFederativa.valueOfCodigo(this.tnfe.getInfNFe().getIde().getCUF()).getConsultaChaveAcessoProducao() : DFUnidadeFederativa.valueOfCodigo(this.tnfe.getInfNFe().getIde().getCUF()).getConsultaChaveAcessoHomologacao();
    }
}