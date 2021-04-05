package com.fincatto.documentofiscal.nfe400.classes.nota;

public enum NFIndicadorIntermediador {

    OPERACAO_SEM_INTERMEDIADOR("0", "Opera\u00e7\u00e3o  sem intermediador (em site ou plataforma pr\u00F3pria)"),
    OPERACAO_EM_PLATAFORMA_DE_TERCEIROS("1", "Opera\u00e7\u00e3o em site ou plataforma de terceiros (intermediadores/marketplace)");

    private final String codigo;
    private final String descricao;

    NFIndicadorIntermediador(final String codigo, final String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public static NFIndicadorIntermediador valueOfCodigo(final String codigo) {
        for (final NFIndicadorIntermediador intermediacao : NFIndicadorIntermediador.values()) {
            if (intermediacao.getCodigo().equals(codigo)) {
                return intermediacao;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return codigo + " - " + descricao;
    }
}