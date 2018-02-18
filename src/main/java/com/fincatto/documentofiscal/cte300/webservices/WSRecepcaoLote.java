package com.fincatto.documentofiscal.cte300.webservices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fincatto.documentofiscal.assinatura.AssinaturaDigital;
import com.fincatto.documentofiscal.cte300.CTeConfig;
import com.fincatto.documentofiscal.cte300.classes.enviolote.CTeEnvioLote;
import com.fincatto.documentofiscal.cte300.classes.enviolote.CTeEnvioLoteRetorno;
import com.fincatto.documentofiscal.cte300.classes.enviolote.CTeEnvioLoteRetornoDados;
import com.fincatto.documentofiscal.parsers.DFParser;

class WSRecepcaoLote {

    private static final String CTE_ELEMENTO = "CTe";
    private static final Logger LOGGER = LoggerFactory.getLogger(WSRecepcaoLote.class);
    private final CTeConfig config;

    WSRecepcaoLote(final CTeConfig config) {
        this.config = config;
    }

    public CTeEnvioLoteRetornoDados envioRecepcao(CTeEnvioLote cteRecepcaoLote) throws Exception {
        //assina o lote
        final String documentoAssinado = new AssinaturaDigital(this.config).assinarDocumento(cteRecepcaoLote.toString());
        final CTeEnvioLote loteAssinado = new DFParser().cteRecepcaoParaObjeto(documentoAssinado);

        //comunica o lote
        final CTeEnvioLoteRetorno retorno = comunicaLote(documentoAssinado);
        return new CTeEnvioLoteRetornoDados(retorno, loteAssinado);
    }

    private CTeEnvioLoteRetorno comunicaLote(final String loteAssinadoXml) throws Exception {
        throw new UnsupportedOperationException("Nao suportado ainda");
    }

}
