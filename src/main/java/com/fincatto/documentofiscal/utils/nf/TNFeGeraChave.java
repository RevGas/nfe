package com.fincatto.documentofiscal.utils.nf;

import br.inf.portalfiscal.nfe.TNFe;
import org.apache.commons.lang3.StringUtils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class TNFeGeraChave {

    private final TNFe tnfe;

    public TNFeGeraChave(final TNFe tnfe) {
        this.tnfe = tnfe;
    }

    public String geraCodigoRandomico() {
        final Random random = new Random(
                ZonedDateTime.parse(this.tnfe.getInfNFe().getIde().getDhEmi()).toInstant().toEpochMilli());
        return StringUtils.leftPad(String.valueOf(random.nextInt(100000000)), 8, "0");
    }

    public String getChaveAcesso() {
        return String.format("%s%s", this.geraChaveAcessoSemDV(), this.getDV());
    }

    public Integer getDV() {
        final char[] valores = this.geraChaveAcessoSemDV().toCharArray();
        final int[] valoresInt = { 2, 3, 4, 5, 6, 7, 8, 9 };
        int indice = 0;
        int soma = 0;
        int valorTemp;
        int multTemp;
        for (int i = valores.length; i > 0; i--) {
            if (indice >= valoresInt.length) {
                indice = 0;
            }

            valorTemp = Integer.parseInt(String.valueOf(valores[i - 1]));
            multTemp = valoresInt[indice++];
            soma += valorTemp * multTemp;
        }
        final int dv = 11 - (soma % 11);
        return ((dv == 11) || (dv == 10)) ? 0 : dv;
    }

    private String geraChaveAcessoSemDV() {
        if (StringUtils.isBlank(this.tnfe.getInfNFe().getIde().getCNF())) {
            throw new IllegalStateException("Codigo randomico deve estar presente para gerar a chave de acesso");
        }
        return StringUtils.leftPad(this.tnfe.getInfNFe().getIde().getCUF(), 2, "0") +
                StringUtils.leftPad(DateTimeFormatter.ofPattern("yyMM").format(ZonedDateTime.parse(this.tnfe.getInfNFe().getIde().getDhEmi())), 4, "0") +
                StringUtils.leftPad(this.tnfe.getInfNFe().getEmit().getCNPJ() == null ? this.tnfe.getInfNFe().getEmit().getCPF() : this.tnfe.getInfNFe().getEmit().getCNPJ(), 14, "0") +
                StringUtils.leftPad(this.tnfe.getInfNFe().getIde().getMod(), 2, "0") +
                StringUtils.leftPad(this.tnfe.getInfNFe().getIde().getSerie(), 3, "0") +
                StringUtils.leftPad(this.tnfe.getInfNFe().getIde().getNNF(), 9, "0") +
                StringUtils.leftPad(this.tnfe.getInfNFe().getIde().getTpEmis(), 1, "0") +
                StringUtils.leftPad(this.tnfe.getInfNFe().getIde().getCNF(), 8, "0");
    }
}
