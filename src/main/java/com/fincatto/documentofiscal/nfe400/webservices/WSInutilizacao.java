package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.ObjectFactory;
import br.inf.portalfiscal.nfe.TInutNFe;
import br.inf.portalfiscal.nfe.TRetInutNFe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.assinatura.AssinaturaDigital;
import com.fincatto.documentofiscal.nfe.NFeConfig;
import com.fincatto.documentofiscal.nfe400.classes.evento.inutilizacao.NFRetornoEventoInutilizacao;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.lang3.StringUtils;

class WSInutilizacao {

    private static final String VERSAO_SERVICO = "4.00";
    private static final String NOME_SERVICO = "INUTILIZAR";
    private static final Logger LOGGER = LoggerFactory.getLogger(WSInutilizacao.class);
    private final NFeConfig config;

    WSInutilizacao(final NFeConfig config) {
        this.config = config;
    }

    NFRetornoEventoInutilizacao inutilizaNotaAssinada(final String eventoAssinadoXml, final DFModelo modelo) throws Exception {
//        final OMElement omElementResult = this.efetuaInutilizacao(eventoAssinadoXml, modelo);
//        return new DFPersister().read(NFRetornoEventoInutilizacao.class, omElementResult.toString());
        return null;
    }

    TRetInutNFe inutilizaNota(final int anoInutilizacaoNumeracao, final String cnpjEmitente, final String serie, final String numeroInicial, final String numeroFinal, final String justificativa, final DFModelo modelo) throws Exception {
        final TInutNFe inutNFe = this.geraDadosInutilizacao(anoInutilizacaoNumeracao, cnpjEmitente, serie, numeroInicial, numeroFinal, justificativa, modelo);
        final String inutilizacaoXMLAssinado = new AssinaturaDigital(this.config).assinarDocumento(getXML(inutNFe));
        final TRetInutNFe retorno = this.efetuaInutilizacao(inutilizacaoXMLAssinado, modelo);
        return retorno;
    }

    private TRetInutNFe efetuaInutilizacao(final String xml, final DFModelo modelo) throws Exception {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.nfe");

        Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(xml);
        JAXBElement<TInutNFe> tInutNFe = (JAXBElement<TInutNFe>) jaxbUnmarshaller.unmarshal(reader);
        
        final br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.svrs.hom.NfeDadosMsg dadosMsg = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.svrs.hom.NfeDadosMsg();
        dadosMsg.getContent().add(tInutNFe);

        br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.svrs.hom.NFeInutilizacao4Soap port = new br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.svrs.hom.NFeInutilizacao4().getNFeInutilizacao4Soap();
        br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.svrs.hom.NfeResultMsg result = port.nfeInutilizacaoNF(dadosMsg);

        return ((JAXBElement<TRetInutNFe>) result.getContent().get(0)).getValue();
    }

    private TInutNFe geraDadosInutilizacao(final int anoInutilizacaoNumeracao, final String cnpjEmitente, final String serie, final String numeroInicial, final String numeroFinal, final String justificativa, final DFModelo modelo) {
        TInutNFe.InfInut infInut = new TInutNFe.InfInut();
        infInut.setAno(String.valueOf(anoInutilizacaoNumeracao));
        infInut.setCNPJ(cnpjEmitente);
        infInut.setCUF(this.config.getCUF().getCodigoIbge());
        final String numeroInicialTamanhoMaximo = StringUtils.leftPad(numeroInicial, 9, "0");
        final String numeroFinalTamanhoMaximo = StringUtils.leftPad(numeroFinal, 9, "0");
        final String serieTamanhoMaximo = StringUtils.leftPad(serie, 3, "0");
        infInut.setId("ID" + this.config.getCUF().getCodigoIbge() + String.valueOf(anoInutilizacaoNumeracao) + cnpjEmitente + modelo.getCodigo() + serieTamanhoMaximo + numeroInicialTamanhoMaximo + numeroFinalTamanhoMaximo);
        infInut.setMod(modelo.getCodigo());
        infInut.setNNFFin(numeroFinal);
        infInut.setNNFIni(numeroInicial);
        infInut.setSerie(serie);
        infInut.setTpAmb(this.config.getAmbiente().getCodigo());
        infInut.setXJust(justificativa);
        infInut.setXServ(WSInutilizacao.NOME_SERVICO);
        TInutNFe inutNFe = new TInutNFe();
        inutNFe.setInfInut(infInut);
        inutNFe.setVersao(WSInutilizacao.VERSAO_SERVICO);
        return inutNFe;
    }
    
    private String getXML(TInutNFe tInutNFe) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(TInutNFe.class);
        Marshaller marshaller = context.createMarshaller();

        JAXBElement<TInutNFe> element = new ObjectFactory().createInutNFe(tInutNFe);
        
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(element, stringWriter);
        
        String xml = stringWriter.toString();
        
        xml = xml.replace("xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", "");
                
        return xml;
    }
    
}
