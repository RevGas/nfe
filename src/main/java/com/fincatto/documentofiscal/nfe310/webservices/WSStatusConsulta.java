package com.fincatto.documentofiscal.nfe310.webservices;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;
import org.w3c.dom.Element;

import br.inf.portalfiscal.nfe.wsdl.nfestatusservico2.svan.NfeCabecMsg;
import br.inf.portalfiscal.nfe.wsdl.nfestatusservico2.svan.NfeDadosMsg;
import br.inf.portalfiscal.nfe.wsdl.nfestatusservico2.svan.NfeStatusServico2;
import br.inf.portalfiscal.nfe.wsdl.nfestatusservico2.svan.NfeStatusServico2Soap;
import br.inf.portalfiscal.nfe.wsdl.nfestatusservico2.svan.NfeStatusServicoNF2Result;
import com.fincatto.documentofiscal.DFConfig;
import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.DFUnidadeFederativa;
import com.fincatto.documentofiscal.nfe310.NFeConfig;
import com.fincatto.documentofiscal.nfe310.classes.NFAutorizador31;
import com.fincatto.documentofiscal.nfe310.classes.statusservico.consulta.NFStatusServicoConsulta;
import com.fincatto.documentofiscal.nfe310.classes.statusservico.consulta.NFStatusServicoConsultaRetorno;
import com.fincatto.documentofiscal.transformers.DFRegistryMatcher;
import com.fincatto.nfe310.converters.ElementStringConverter;

class WSStatusConsulta {

    private static final String NOME_SERVICO = "STATUS";
    private final DFConfig config;

    WSStatusConsulta(final DFConfig config) {
        this.config = config;
    }

    NFStatusServicoConsultaRetorno consultaStatus(final DFUnidadeFederativa uf, final DFModelo modelo) throws Exception {
        return new Persister(new DFRegistryMatcher(), new Format(0)).read(NFStatusServicoConsultaRetorno.class, efetuaConsultaStatus(gerarDadosConsulta(uf).toString(), uf, modelo));
    }

    private NFStatusServicoConsulta gerarDadosConsulta(final DFUnidadeFederativa unidadeFederativa) {
        final NFStatusServicoConsulta consStatServ = new NFStatusServicoConsulta();
        consStatServ.setUf(unidadeFederativa);
        consStatServ.setAmbiente(this.config.getAmbiente());
        consStatServ.setVersao(NFeConfig.VERSAO);
        consStatServ.setServico(WSStatusConsulta.NOME_SERVICO);
        return consStatServ;
    }

    private String efetuaConsultaStatus(final String xml, final DFUnidadeFederativa unidadeFederativa, final DFModelo modelo) throws RemoteException, MalformedURLException, Exception {
        NfeDadosMsg dadosMsg = new NfeDadosMsg();
        NfeCabecMsg cabecMsg = new NfeCabecMsg();

        cabecMsg.setCUF(unidadeFederativa.getCodigoIbge());
        cabecMsg.setVersaoDados(NFeConfig.VERSAO);

        dadosMsg.getContent().add(ElementStringConverter.read(xml));

        final NFAutorizador31 autorizador = NFAutorizador31.valueOfCodigoUF(unidadeFederativa);
        final String endpoint = DFModelo.NFCE.equals(modelo) ? autorizador.getNfceStatusServico(this.config.getAmbiente()) : autorizador.getNfeStatusServico(this.config.getAmbiente());
        if (endpoint == null) {
            throw new IllegalArgumentException("Nao foi possivel encontrar URL para StatusServico " + modelo.name() + ", autorizador " + autorizador.name() + ", UF " + unidadeFederativa.name());
        }

        NfeStatusServico2Soap port = new NfeStatusServico2(new URL(endpoint)).getNfeStatusServico2Soap12();
        NfeStatusServicoNF2Result result = port.nfeStatusServicoNF2(dadosMsg, cabecMsg);

        return ElementStringConverter.write((Element) result.getContent().get(0));
    }

}