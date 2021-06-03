package com.tartigrado.df.validadores.cte;

import com.tartigrado.df.validadores.cte.versoes.CTeValidador300a;

import java.util.HashMap;
import java.util.Map;

/**
 * Fábrica de Validadores XML do projeto CTe
 * Deve conter o caminho para o pacote de schemas XML para ser utilizado pelo Validador
 */
public class CTeValidatorFactory {

    public static String caminhoPadrao = "schemas";

    private static CTeValidador atual;

    private static final Map<CTeVersion, Class> validadores = new HashMap();

    static {
        validadores.put(CTeVersion.CTe300a, CTeValidador300a.class);
    }

    public static CTeValidador create(CTeVersion version) {
        try {
            return (CTeValidador) validadores.get(version).newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Não foi possível instanciar o validador da versão: " + version.name());
        }
    }

    public static CTeValidador padrao() {
        if (atual == null) {
            atual = create(CTeVersion.CTe300a);
        }
        return atual;
    }
}
