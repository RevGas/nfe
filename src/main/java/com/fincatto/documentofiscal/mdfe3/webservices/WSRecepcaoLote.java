package com.fincatto.documentofiscal.mdfe3.webservices;

import com.fincatto.documentofiscal.assinatura.AssinaturaDigital;
import com.fincatto.documentofiscal.mdfe3.classes.lote.envio.MDFEnvioLote;
import com.fincatto.documentofiscal.mdfe3.classes.lote.envio.MDFEnvioLoteRetorno;
import com.fincatto.documentofiscal.mdfe3.classes.lote.envio.MDFEnvioLoteRetornoDados;
import com.fincatto.documentofiscal.mdfe3.MDFeConfig;
import com.fincatto.documentofiscal.mdfe3.classes.parsers.MDFeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class WSRecepcaoLote {

    private static final String MDFE_ELEMENTO = "MDFe";
    private static final Logger LOGGER = LoggerFactory.getLogger(WSRecepcaoLote.class);
    private final MDFeConfig config;

    WSRecepcaoLote(final MDFeConfig config){
            this.config = config;
    }

    public MDFEnvioLoteRetornoDados envioRecepcao(MDFEnvioLote mdfeRecepcaoLote) throws Exception {
            //assina o lote
            final String documentoAssinado = new AssinaturaDigital(this.config).assinarDocumento(mdfeRecepcaoLote.toString(), "infMDFe");
            final MDFEnvioLote loteAssinado = new MDFeParser().mdfeRecepcaoParaObjeto(documentoAssinado);

            //comunica o lote
            final MDFEnvioLoteRetorno retorno = comunicaLote(documentoAssinado);
            return new MDFEnvioLoteRetornoDados(retorno, loteAssinado);
    }

    private MDFEnvioLoteRetorno comunicaLote(final String loteAssinadoXml) throws Exception {
        throw new UnsupportedOperationException("Nao suportado ainda");
    }

}
