package com.fincatto.documentofiscal.cte300.parsers;

import br.inf.portalfiscal.cte.TUf;
import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.DFUnidadeFederativa;
import com.fincatto.documentofiscal.cte300.classes.CTTipoEmissao;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

public class CTChaveParser {

    private final String chave;

    public CTChaveParser() {
        this.chave = null;
    }
    
    public CTChaveParser(final String chave) {
        this.chave = StringUtils.stripToEmpty(chave).replaceAll("\\D", "");
        if (this.chave.length() != 44) {
            throw new IllegalArgumentException(String.format("A chave deve ter exatos 44 caracteres numericos: %s", chave));
        }
    }

    public String getChave() {
        return chave;
    }

    public DFUnidadeFederativa getNFUnidadeFederativa() {
        return DFUnidadeFederativa.valueOfCodigo(this.chave.substring(0, 2));
    }

    public LocalDate getDataEmissao() {
        return LocalDate.of(this.getDataEmissaoAno(), this.getDataEmissaoMes(), 1);
    }

    private int getDataEmissaoMes() {
        return Integer.parseInt(this.chave.substring(4, 6));
    }

    private int getDataEmissaoAno() {
        return 2000 + Integer.parseInt(this.chave.substring(2, 4));
    }

    public String getCnpjEmitente() {
        return this.chave.substring(6, 20);
    }

    public DFModelo getModelo() {
        return DFModelo.valueOfCodigo(this.chave.substring(20, 22));
    }

    public String getSerie() {
        return this.chave.substring(22, 25);
    }

    public String getNumero() {
        return this.chave.substring(25, 34);
    }

    public CTTipoEmissao getFormaEmissao() {
        return CTTipoEmissao.valueOfCodigo(this.chave.substring(34, 35));
    }

    public String getCodigoNumerico() {
        return this.chave.substring(35, 43);
    }

    public String getDV() {
        return this.chave.substring(43, 44);
    }

    public boolean isEmitidaContingenciaSCAN() {
        return this.getSerie().matches("9[0-9]{2}");
    }

    public boolean isEmitidaContingenciaSCVSP() {
        return this.chave.matches("\\d{34}6\\d{9}");
    }

    public boolean isEmitidaContingenciaSCVRS() {
        return this.chave.matches("\\d{34}7\\d{9}");
    }

    public String getFormatado() {
        return this.chave.replaceFirst("(\\d{4})(\\d{4})(\\d{4})(\\d{4})(\\d{4})(\\d{4})(\\d{4})(\\d{4})(\\d{4})(\\d{4})(\\d{4})", "$1 $2 $3 $4 $5 $6 $7 $8 $9 $10 $11");
    }
    
    public TUf getTUf(String code) {
        switch (code) {
            case "12" : return TUf.AC;
            case "27" : return TUf.AL;
            case "13" : return TUf.AM;
            case "16" : return TUf.AP;
            case "29" : return TUf.BA;
            case "23" : return TUf.CE;
            case "53" : return TUf.DF;
            case "32" : return TUf.ES;
            case "52" : return TUf.GO;
            case "21" : return TUf.MA;
            case "31" : return TUf.MG;
            case "50" : return TUf.MS;
            case "51" : return TUf.MT;
            case "15" : return TUf.PA;
            case "25" : return TUf.PB;
            case "26" : return TUf.PE;
            case "22" : return TUf.PI;
            case "41" : return TUf.PR;
            case "33" : return TUf.RJ;
            case "24" : return TUf.RN;
            case "11" : return TUf.RO;
            case "14" : return TUf.RR;
            case "43" : return TUf.RS;
            case "42" : return TUf.SC;
            case "28" : return TUf.SE;
            case "35" : return TUf.SP;
            case "17" : return TUf.TO;
            default: return null;
        }
    }
    
}
