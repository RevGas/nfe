package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.model.consulta_cadastro.PL_006v.ObjectFactory;
import br.inf.portalfiscal.nfe.model.consulta_cadastro.PL_006v.TConsCad;
import br.inf.portalfiscal.nfe.model.consulta_cadastro.PL_006v.TRetConsCad;
import br.inf.portalfiscal.nfe.model.consulta_cadastro.PL_006v.TUfCons;
import com.fincatto.documentofiscal.DFLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fincatto.documentofiscal.DFUnidadeFederativa;
import com.fincatto.documentofiscal.nfe.NFeConfig;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

class WSConsultaCadastro implements DFLog {
    
    private static final Logger LOG = LoggerFactory.getLogger(WSConsultaCadastro.class);
    public static final String NOME_SERVICO = "CONS-CAD";
    public static final String VERSAO_SERVICO = "2.00";
    private final NFeConfig config;
    
    WSConsultaCadastro(final NFeConfig config) {
        this.config = config;
    }

    br.inf.portalfiscal.nfe.model.consulta_cadastro.PL_006v.TRetConsCad consultaCadastro(final String cnpj, final DFUnidadeFederativa UF) throws Exception {
        return efetuaConsulta(getDadosConsulta(cnpj, UF), UF);
    }

    private TRetConsCad efetuaConsulta(final JAXBElement<TConsCad> tConsCad, final DFUnidadeFederativa UF) throws JAXBException, Exception {
        return com.fincatto.documentofiscal.nfe400.webservices.GatewayConsultaCadastro.valueOfCodigoUF(UF).getTRetConsCad(tConsCad, UF.getCodigoIbge());
    }

    private JAXBElement<TConsCad> getDadosConsulta(final String cnpj, final DFUnidadeFederativa uf) {
        final TConsCad consulta = new TConsCad();
        consulta.setVersao(WSConsultaCadastro.VERSAO_SERVICO);
        consulta.setInfCons(new TConsCad.InfCons());
        consulta.getInfCons().setCNPJ(cnpj);
        consulta.getInfCons().setXServ(WSConsultaCadastro.NOME_SERVICO);
        consulta.getInfCons().setUF(TUfCons.fromValue(uf.getCodigo()));
        
        JAXBElement<TConsCad> tConsCad = (JAXBElement<TConsCad>) new ObjectFactory().createConsCad(consulta);

        return tConsCad;
    }
}
