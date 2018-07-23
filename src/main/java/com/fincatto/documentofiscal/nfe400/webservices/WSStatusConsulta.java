package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.wsdl.nfestatusservico4.go.hom.NFeStatusServico4;
import br.inf.portalfiscal.nfe.wsdl.nfestatusservico4.go.hom.NFeStatusServico4Service;
import br.inf.portalfiscal.nfe.wsdl.nfestatusservico4.go.hom.NfeDadosMsg;
import br.inf.portalfiscal.nfe.wsdl.nfestatusservico4.go.hom.NfeResultMsg;
import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.DFUnidadeFederativa;
import com.fincatto.documentofiscal.nfe.NFeConfig;
import com.fincatto.documentofiscal.nfe400.classes.NFAutorizador400;
import com.fincatto.documentofiscal.nfe400.classes.statusservico.consulta.NFStatusServicoConsulta;
import com.fincatto.documentofiscal.nfe400.classes.statusservico.consulta.NFStatusServicoConsultaRetorno;
import com.fincatto.documentofiscal.transformers.DFRegistryMatcher;
import com.fincatto.nfe310.converters.ElementStringConverter;
import java.net.MalformedURLException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;
import org.w3c.dom.Element;

class WSStatusConsulta {

    private static final String NOME_SERVICO = "STATUS";
    private static final Logger LOGGER = LoggerFactory.getLogger(WSStatusConsulta.class);
    private final NFeConfig config;

    WSStatusConsulta(final NFeConfig config) {
        this.config = config;
    }

    NFStatusServicoConsultaRetorno consultaStatus(final DFUnidadeFederativa uf, final DFModelo modelo) throws Exception {
        return new Persister(new DFRegistryMatcher(), new Format(0)).read(NFStatusServicoConsultaRetorno.class, efetuaConsultaStatus(gerarDadosConsulta(uf).toString(), uf, modelo));
    }

    private NFStatusServicoConsulta gerarDadosConsulta(final DFUnidadeFederativa unidadeFederativa) {
        final NFStatusServicoConsulta consStatServ = new NFStatusServicoConsulta();
        consStatServ.setUf(unidadeFederativa);
        consStatServ.setAmbiente(this.config.getAmbiente());
        consStatServ.setVersao(this.config.getVersao());
        consStatServ.setServico(WSStatusConsulta.NOME_SERVICO);
        return consStatServ;
    }

    private String efetuaConsultaStatus(final String xml, final DFUnidadeFederativa unidadeFederativa, final DFModelo modelo) throws RemoteException, MalformedURLException {
        NfeDadosMsg dadosMsg = new NfeDadosMsg();

        dadosMsg.getContent().add(ElementStringConverter.read(xml));

        final NFAutorizador400 autorizador = NFAutorizador400.valueOfCodigoUF(unidadeFederativa);
        final String endpoint = DFModelo.NFCE.equals(modelo) ? autorizador.getNfceStatusServico(this.config.getAmbiente()) : autorizador.getNfeStatusServico(this.config.getAmbiente());
        if (endpoint == null) {
            throw new IllegalArgumentException("Nao foi possivel encontrar URL para StatusServico " + modelo.name() + ", autorizador " + autorizador.name() + ", UF " + unidadeFederativa.name());
        }

        NFeStatusServico4Service port = new NFeStatusServico4(new URL(endpoint)).getNFeStatusServico4ServicePort();
        NfeResultMsg result = port.nfeStatusServicoNF(dadosMsg);

        return ElementStringConverter.write((Element) result.getContent().get(0));
    }
}
