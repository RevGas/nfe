package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.TRetConsReciNFe;
import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.nfe.NFeConfig;

import com.fincatto.documentofiscal.DFLog;

class WSLoteConsulta implements DFLog {
    
    private final NFeConfig config;
    
    WSLoteConsulta(final NFeConfig config) {
        this.config = config;
    }

    TRetConsReciNFe consultaLote(final String numeroRecibo, final DFModelo modelo) throws Exception {
        return GatewayLoteConsulta.valueOfCodigoUF(this.config.getCUF()).getTRetConsReciNFe(numeroRecibo, this.config.getAmbiente(), this.config.getVersao());
    }

}
