package com.fincatto.documentofiscal.cte300.classes.nota;

import com.fincatto.documentofiscal.DFBase;
import org.simpleframework.xml.Element;

public class CTeNotaInfoSupl extends DFBase {
    private static final long serialVersionUID = -369791523192990400L;

    @Element(name = "qrCodCTe", required = false)
    private String qrCode;

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
