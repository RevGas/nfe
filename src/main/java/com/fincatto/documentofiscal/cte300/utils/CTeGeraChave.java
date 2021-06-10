package com.fincatto.documentofiscal.cte300.utils;

import br.inf.portalfiscal.cte.TEnviCTe;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;

public class CTeGeraChave {
    
    private final TEnviCTe tEnviCTe;
    
    public CTeGeraChave(final TEnviCTe tEnviCTe) {
        this.tEnviCTe = tEnviCTe;
    }
    
    public String geraCodigoRandomico() {
        final Random random = new Random(new Date().toInstant().toEpochMilli());
        return StringUtils.leftPad(String.valueOf(random.nextInt(100000000)), 8, "0");
    }
    
    public String getChaveAcesso() {
        return String.format("%s%s", this.geraChaveAcessoSemDV(), this.getDV());
    }
    
    public Integer getDV() {
        final char[] valores = this.geraChaveAcessoSemDV().toCharArray();
        final int[] valoresInt = {2, 3, 4, 5, 6, 7, 8, 9};
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
        if (StringUtils.isBlank(this.tEnviCTe.getCTe().get(0).getInfCte().getIde().getCCT())) {
            throw new IllegalStateException("Codigo numerico deve estar presente para gerar a chave de acesso");
        }
        return StringUtils.leftPad(this.tEnviCTe.getCTe().get(0).getInfCte().getIde().getCUF(), 2, "0") +
            StringUtils.leftPad(this.tEnviCTe.getCTe().get(0).getInfCte().getIde().getDhEmi().substring(2,4)+
                    this.tEnviCTe.getCTe().get(0).getInfCte().getIde().getDhEmi().substring(5,7), 4, "0") +
            StringUtils.leftPad(this.tEnviCTe.getCTe().get(0).getInfCte().getEmit().getCNPJ(), 14, "0") +
            StringUtils.leftPad(this.tEnviCTe.getCTe().get(0).getInfCte().getIde().getMod(), 2, "0") + 
            StringUtils.leftPad(this.tEnviCTe.getCTe().get(0).getInfCte().getIde().getSerie() + "", 3, "0") +
            StringUtils.leftPad(this.tEnviCTe.getCTe().get(0).getInfCte().getIde().getNCT() + "", 9, "0") +
            StringUtils.leftPad(this.tEnviCTe.getCTe().get(0).getInfCte().getIde().getTpEmis(), 1, "0") +
            StringUtils.leftPad(this.tEnviCTe.getCTe().get(0).getInfCte().getIde().getCCT(), 8, "0");
    }
}
