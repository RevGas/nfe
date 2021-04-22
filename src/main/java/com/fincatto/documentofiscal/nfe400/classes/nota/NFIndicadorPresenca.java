package com.fincatto.documentofiscal.nfe400.classes.nota;

public enum NFIndicadorPresenca {

    OPERACAO_PRESENCIAL("1", "Opera\u00e7\u00e3o presencial"),
    OPERACAO_NAO_PRESENCIAL_PELA_INTERNET("2", "Opera\u00e7\u00e3o n\u00e3o presencial, pela Internet;"),
    OPERACAO_NAO_PRESENCIAL_TELEATENDIMENTO("3", "Opera\u00e7\u00e3o n\u00e3o presencial, Teleatendimento"),
    NFCE_EM_OPERACAO_COM_ENTREGA_A_DOMICILIO("4", "NFC-e em opera\u00e7\u00e3o com entrega a domic\u00edlio"),
    OPERACAO_NAO_PRESENCIAL_OUTROS("9", "Opera\u00e7\u00e3o n\u00e3o presencial, outros");

    private final String codigo;
    private final String descricao;

    NFIndicadorPresenca(final String codigo, final String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public static NFIndicadorPresenca valueOfCodigo(final String codigo) {
        for (final NFIndicadorPresenca intermediacao : NFIndicadorPresenca.values()) {
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