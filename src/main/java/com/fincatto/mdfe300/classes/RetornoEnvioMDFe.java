package com.fincatto.mdfe300.classes;

import br.inf.portalfiscal.mdfe.TRetMDFe;

public class RetornoEnvioMDFe {

    private final String documentoAssinado;
    private final TRetMDFe tRetMDFe;

    public RetornoEnvioMDFe(String documentoAssinado, TRetMDFe tRetMDFe) {
        this.documentoAssinado = documentoAssinado;
        this.tRetMDFe = tRetMDFe;
    }

    /**
     * Método que retorna o XML assinado que foi enviado para a SEFAZ
     * @return
     */
    public String getDocumentoAssinado() {
        return documentoAssinado;
    }

    public TRetMDFe getTRetMDFe() {
        return tRetMDFe;
    }

}