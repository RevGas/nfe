package com.tartigrado.df.validadores.cte;

/**
 * Enum utlizado para identificar o nome correto de uma dada Versao do CTe, usado no pacote
 * de schemas XML (XSD)
 */
public enum CTeVersion {

    CTe300a("PL_CTe_300a");

    private String version;

    CTeVersion(String version) {
        this.version = version;
    }
}
