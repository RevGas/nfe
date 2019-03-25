package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.ObjectFactory;
import br.inf.portalfiscal.nfe.TConsCad;
import br.inf.portalfiscal.nfe.TRetConsCad;
import br.inf.portalfiscal.nfe.TUfCons;
import br.inf.portalfiscal.nfe.wsdl.cadconsultacadastro4.svrs.CadConsultaCadastro4;
import br.inf.portalfiscal.nfe.wsdl.cadconsultacadastro4.svrs.CadConsultaCadastro4Soap;
import br.inf.portalfiscal.nfe.wsdl.cadconsultacadastro4.svrs.NfeDadosMsg;
import br.inf.portalfiscal.nfe.wsdl.cadconsultacadastro4.svrs.NfeResultMsg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fincatto.documentofiscal.DFUnidadeFederativa;
import com.fincatto.documentofiscal.nfe.NFeConfig;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

class WSConsultaCadastro {
    private static final Logger LOG = LoggerFactory.getLogger(WSConsultaCadastro.class);
    private static final String NOME_SERVICO = "CONS-CAD";
    private static final String VERSAO_SERVICO = "2.00";
    private final NFeConfig config;

    WSConsultaCadastro(final NFeConfig config) {
        this.config = config;
    }

    TRetConsCad consultaCadastro(final String cnpj, final DFUnidadeFederativa uf) throws Exception {
        return efetuaConsulta(getDadosConsulta(cnpj, uf));
    }

    private TRetConsCad efetuaConsulta(final JAXBElement<TConsCad> tConsCad) throws JAXBException {
        final NfeDadosMsg nfeDadosMsg = new NfeDadosMsg();
        nfeDadosMsg.getContent().add(tConsCad);

        CadConsultaCadastro4Soap port = new CadConsultaCadastro4().getCadConsultaCadastro4Soap();
        NfeResultMsg result = port.consultaCadastro(nfeDadosMsg);
        
        return ((JAXBElement<TRetConsCad>) result.getContent().get(0)).getValue();
    }

    private JAXBElement<TConsCad> getDadosConsulta(final String cnpj, final DFUnidadeFederativa uf) {
        final TConsCad consulta = new TConsCad();
        consulta.setVersao(WSConsultaCadastro.VERSAO_SERVICO);
        consulta.setInfCons(new TConsCad.InfCons());
        consulta.getInfCons().setCNPJ(cnpj);
        consulta.getInfCons().setXServ(WSConsultaCadastro.NOME_SERVICO);
        consulta.getInfCons().setUF(TUfCons.fromValue(uf.getCodigo()));
        
        JAXBElement<TConsCad> element = (JAXBElement<TConsCad>) new ObjectFactory().createConsCad(consulta);

        return element;
    }
}
