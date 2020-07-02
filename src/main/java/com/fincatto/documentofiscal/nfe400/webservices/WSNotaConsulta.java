package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.ObjectFactory;
import br.inf.portalfiscal.nfe.TConsSitNFe;
import br.inf.portalfiscal.nfe.TRetConsSitNFe;
import com.fincatto.documentofiscal.DFLog;
import com.fincatto.documentofiscal.nfe.NFeConfig;
import com.fincatto.documentofiscal.nfe400.NotaFiscalChaveParser;
import javax.xml.bind.JAXBElement;

class WSNotaConsulta implements DFLog {
    private static final String NOME_SERVICO = "CONSULTAR";
    private static final String VERSAO_SERVICO = "4.00";
    private final NFeConfig config;
    
    WSNotaConsulta(final NFeConfig config) {
        this.config = config;
    }

    TRetConsSitNFe consultaProtocolo(final String chNFe) throws Exception {
        TRetConsSitNFe tRetConsSitNFe = this.efetuaConsulta(gerarDadosConsulta(chNFe));
        return tRetConsSitNFe;
    }

    private TRetConsSitNFe efetuaConsulta(JAXBElement<TConsSitNFe> tConsSitNFe) throws Exception {
        final NotaFiscalChaveParser chaveParser = new NotaFiscalChaveParser(tConsSitNFe.getValue().getChNFe());
        return GatewayConsultaProtocolo.valueOfCodigoUF(chaveParser.getNFUnidadeFederativa()).getTRetConsSitNFe(tConsSitNFe, chaveParser.getModelo(), config.getAmbiente());
    }
    
    private JAXBElement<TConsSitNFe> gerarDadosConsulta(final String chNFe) {
        final TConsSitNFe consulta = new TConsSitNFe();
        consulta.setChNFe(chNFe);
        consulta.setTpAmb(this.config.getAmbiente().getCodigo());
        consulta.setVersao(WSNotaConsulta.VERSAO_SERVICO);
        consulta.setXServ(WSNotaConsulta.NOME_SERVICO);
        
        JAXBElement<TConsSitNFe> tConsSitNFe = (JAXBElement<TConsSitNFe>) new ObjectFactory().createConsSitNFe(consulta);
        
        return tConsSitNFe;
    }
}