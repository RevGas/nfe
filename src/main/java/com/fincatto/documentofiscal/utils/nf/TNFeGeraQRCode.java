package com.fincatto.documentofiscal.utils.nf;

import br.inf.portalfiscal.nfe.TNFe;
import com.fincatto.documentofiscal.DFAmbiente;
import com.fincatto.documentofiscal.DFUnidadeFederativa;
import com.fincatto.documentofiscal.utils.Util;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TNFeGeraQRCode {

    private final TNFe tnfe;
    private final String csc;
    private final String idCSC;

    public TNFeGeraQRCode(final TNFe tnfe, String csc, String idCSC) {
        this.tnfe = tnfe;
        this.csc = csc;
        this.idCSC = idCSC;
    }
    
    public String getQRCodev2() throws NoSuchAlgorithmException {
        String url = this.tnfe.getInfNFe().getIde().getTpAmb().equals(DFAmbiente.PRODUCAO.getCodigo()) ? DFUnidadeFederativa.valueOfCodigo(this.tnfe.getInfNFe().getIde().getCUF()).getQrCodeProducao() : DFUnidadeFederativa.valueOfCodigo(this.tnfe.getInfNFe().getIde().getCUF()).getQrCodeHomologacao();
        
        if (StringUtils.isBlank(url)) {
            throw new IllegalArgumentException("URL para consulta do QRCode nao informada para uf de código " + this.tnfe.getInfNFe().getIde().getCUF() + "!");
        }
        if (StringUtils.isBlank(csc)) {
            throw new IllegalArgumentException("CSC nao informado nas configuracoes!");
        }
        if ((idCSC == null) || (idCSC == "0")) {
            throw new IllegalArgumentException("IdCSC nao informado nas configuracoes!");
        }
        
        final StringBuilder parametros = new StringBuilder();
        parametros.append(Util.chaveFromTNFe(tnfe)).append("|"); // Chave de Acesso da NFC-e
        parametros.append("2").append("|"); // Versao do QRCode
        parametros.append(this.tnfe.getInfNFe().getIde().getTpNF()).append("|");
        parametros.append(idCSC);
        
        return url.concat("?p=").concat(parametros.toString().concat("|").concat(StringUtils.upperCase(TNFeGeraQRCode.createHash(parametros.toString(), csc))));
    }

    public String getQRCode() throws NoSuchAlgorithmException {
        String url = this.tnfe.getInfNFe().getIde().getTpAmb().equals(DFAmbiente.PRODUCAO.getCodigo()) ? DFUnidadeFederativa.valueOfCodigo(this.tnfe.getInfNFe().getIde().getCUF()).getQrCodeProducao() : DFUnidadeFederativa.valueOfCodigo(this.tnfe.getInfNFe().getIde().getCUF()).getQrCodeHomologacao();

        /* FIXME TODO Workaround para corrigir erro :
         *<cStat>395</cStat><xMotivo>Endereco do site da UF da Consulta via QR-Code diverge do previsto. Novo endereco:http://www.fazenda.pr.gov.br/nfce/qrcode</xMotivo>
         * corrigir em DFUnidadeFederativa quando a URL da versao 3.10 do PR for desabilitada.
         */
        if (this.tnfe.getInfNFe().getIde().getCUF().equals(DFUnidadeFederativa.PR.getCodigo()) && this.tnfe.getInfNFe().getVersao().equals("4.00")) {
            url = "http://www.fazenda.pr.gov.br/nfce/qrcode";
        }

        if (StringUtils.isBlank(url)) {
            throw new IllegalArgumentException("URL para consulta do QRCode nao informada para uf de codigo " + this.tnfe.getInfNFe().getIde().getCUF() + "!");
        }
        if (StringUtils.isBlank(csc)) {
            throw new IllegalArgumentException("CSC nao informado nas configuracoes!");
        }
        if ((idCSC == null) || (idCSC == "0")) {
            throw new IllegalArgumentException("IdCSC nao informado nas configuracoes!");
        }

        final String cpfj = this.tnfe.getInfNFe().getDest() == null ? null : this.tnfe.getInfNFe().getDest().getCNPJ();

        // Monta os parametros do qrcode: https://www.sefaz.rs.gov.br/NFCE/NFCE-COM.aspx?chNFe=43160493062776000117650010000012891000012891&nVersao=100&tpAmb=1&cDest=00400437031&dhEmi=323031362d30342d31355431363a32313a35312d30333a3030&vNF=88.00&vICMS=0.00&digVal=787971704e2f7771446134687070486e6b6b6c34705a39536a36633d&cIdToken=000001&cHashQRCode=852E4B5BC4EB9BF65484AEEBB06BE4A65F0E8E13
        final StringBuilder parametros = new StringBuilder();
        parametros.append("chNFe=").append(Util.chaveFromTNFe(tnfe)).append("&"); // Chave de Acesso da NFC-e
        parametros.append("nVersao=100").append("&"); // Versao do QRCode
        parametros.append("tpAmb=").append(this.tnfe.getInfNFe().getIde().getTpAmb()).append("&");

        if (StringUtils.isNotBlank(cpfj)) {
            parametros.append("cDest=").append(cpfj).append("&");// Documento de Identificacao do Consumidor (CNPJ/CPF/ID Estrangeiro)
        }

        parametros.append("dhEmi=").append(TNFeGeraQRCode.toHex(this.tnfe.getInfNFe().getIde().getDhEmi())).append("&");// Data e Hora de Emissão da NFC-e
        parametros.append("vNF=").append(this.tnfe.getInfNFe().getTotal().getICMSTot().getVNF()).append("&"); // Valor Total da NFC-e
        parametros.append("vICMS=").append(this.tnfe.getInfNFe().getTotal().getICMSTot().getVICMS()).append("&");// NFC-e Valor Total ICMS na NFC-e
        parametros.append("digVal=").append(TNFeGeraQRCode.toHex(new String(this.tnfe.getSignature().getSignedInfo().getReference().getDigestValue()))).append("&");// Digest Value da NFC-e
        parametros.append("cIdToken=").append(String.format("%06d", idCSC));// Identificador do CSC – Codigo de Seguranca do Contribuinte no Banco de Dados da SEFAZ

        // retorna a url do qrcode
        return url + "?" + parametros.toString() + "&cHashQRCode=" + TNFeGeraQRCode.createHash(parametros.toString(), csc);
    }

    public static String createHash(final String campos, final String csc) throws NoSuchAlgorithmException {
        return TNFeGeraQRCode.sha1(campos + csc);
    }

    public static String toHex(final String arg) {
        return String.format("%040x", new BigInteger(1, arg.getBytes(Charset.forName("UTF-8"))));
    }

    public static String sha1(final String input) throws NoSuchAlgorithmException {
        final StringBuilder sb = new StringBuilder();
        for (final byte element : MessageDigest.getInstance("SHA1").digest(input.getBytes(Charset.forName("UTF-8")))) {
            sb.append(Integer.toString((element & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
    
    public String urlConsultaChaveAcesso() {
        return this.tnfe.getInfNFe().getIde().getTpAmb().equals(DFAmbiente.PRODUCAO.getCodigo()) ? DFUnidadeFederativa.valueOfCodigo(this.tnfe.getInfNFe().getIde().getTpAmb()).getConsultaChaveAcessoProducao() : DFUnidadeFederativa.valueOfCodigo(this.tnfe.getInfNFe().getIde().getTpAmb()).getConsultaChaveAcessoHomologacao();
    }
}
