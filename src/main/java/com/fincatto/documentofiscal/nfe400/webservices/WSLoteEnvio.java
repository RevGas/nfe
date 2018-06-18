package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.TRetEnviNFe;
import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.DFUnidadeFederativa;
import com.fincatto.documentofiscal.assinatura.AssinaturaDigital;
import com.fincatto.documentofiscal.nfe.NFeConfig;
import com.fincatto.documentofiscal.nfe400.classes.lote.envio.NFLoteEnvio;
import com.fincatto.documentofiscal.nfe400.classes.nota.NFNota;
import com.fincatto.documentofiscal.nfe400.classes.nota.NFNotaInfoSuplementar;
import com.fincatto.documentofiscal.nfe400.parsers.DFParser;
import com.fincatto.documentofiscal.nfe400.utils.NFGeraChave;
import com.fincatto.documentofiscal.nfe400.utils.NFGeraQRCode;
import com.fincatto.documentofiscal.validadores.xsd.XMLValidador;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;

class WSLoteEnvio {

    private static final String NFE_ELEMENTO = "NFe";
    private static final Logger LOGGER = LoggerFactory.getLogger(WSLoteEnvio.class);
    private final NFeConfig config;

    WSLoteEnvio(final NFeConfig config) {
        this.config = config;
    }

    TRetEnviNFe enviaLoteAssinado(final String loteAssinadoXml, final DFModelo modelo) throws Exception {
        return this.comunicaLote(loteAssinadoXml, modelo);
    }

    TRetEnviNFe enviaLote(final NFLoteEnvio lote) throws Exception {
        final NFLoteEnvio loteAssinado = this.getLoteAssinado(lote);
        // comunica o lote
        final TRetEnviNFe loteEnvioRetorno = this.comunicaLote(loteAssinado.toString(), loteAssinado.getNotas().get(0).getInfo().getIdentificacao().getModelo());
        return loteEnvioRetorno;
    }

    /**
     * Retorna o Lote assinado.
     * @param lote
     * @return
     * @throws Exception
     */
    NFLoteEnvio getLoteAssinado(final NFLoteEnvio lote) throws Exception {
        // adiciona a chave e o dv antes de assinar
        for (final NFNota nota : lote.getNotas()) {
            final NFGeraChave geraChave = new NFGeraChave(nota);
            nota.getInfo().getIdentificacao().setCodigoRandomico(StringUtils.defaultIfBlank(nota.getInfo().getIdentificacao().getCodigoRandomico(), geraChave.geraCodigoRandomico()));
            nota.getInfo().getIdentificacao().setDigitoVerificador(geraChave.getDV());
            nota.getInfo().setIdentificador(geraChave.getChaveAcesso());
        }
        // assina o lote
        final String documentoAssinado = new AssinaturaDigital(this.config).assinarDocumento(lote.toString());
        final NFLoteEnvio loteAssinado = new DFParser().loteParaObjeto(documentoAssinado);

        // verifica se nao tem NFCe junto com NFe no lote e gera qrcode (apos assinar mesmo, eh assim)
        int qtdNF = 0, qtdNFC = 0;
        for (final NFNota nota : loteAssinado.getNotas()) {
            switch (nota.getInfo().getIdentificacao().getModelo()) {
                case NFE:
                    qtdNF++;
                    break;
                case NFCE:
                    final NFGeraQRCode geraQRCode = new NFGeraQRCode(nota, this.config);
                    nota.setInfoSuplementar(new NFNotaInfoSuplementar());
                    nota.getInfoSuplementar().setUrlConsultaChaveAcesso(geraQRCode.urlConsultaChaveAcesso());
                    nota.getInfoSuplementar().setQrCode(geraQRCode.getQRCode());
                    qtdNFC++;
                    break;
                default:
                    throw new IllegalArgumentException(String.format("Modelo de nota desconhecida: %s", nota.getInfo().getIdentificacao().getModelo()));
            }
        }
        // verifica se todas as notas do lote sao do mesmo modelo
        if ((qtdNF > 0) && (qtdNFC > 0)) {
            throw new IllegalArgumentException("Lote contendo notas de modelos diferentes!");
        }
        return loteAssinado;
    }

    private TRetEnviNFe comunicaLote(final String loteAssinadoXml, final DFModelo modelo) throws Exception {
        //valida o lote assinado, para verificar se o xsd foi satisfeito, antes de comunicar com a sefaz
        XMLValidador.validaLote400(loteAssinadoXml);

        return getTRetEnviNFe(modelo, this.config.getCUF(), loteAssinadoXml);
    }

    private TRetEnviNFe getTRetEnviNFe(final DFModelo modelo, final DFUnidadeFederativa uf, String loteAssinadoXml) throws MalformedURLException, JAXBException, Exception {

        switch (uf) {
            case MA :
                return com.fincatto.documentofiscal.nfe400.classes.NFLoteEnvio.valueOfCodigoUF(DFUnidadeFederativa.MA).getTRetEnviNFe(modelo, loteAssinadoXml);
//            case PR :
//                //envia o lote para a sefaz
//                Holder<NfeCabecMsg> nfeCabecMsgPR = new Holder<>();
//                nfeCabecMsgPR.value = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao3.pr.NfeCabecMsg();
//                nfeCabecMsgPR.value.setCUF(this.config.getCUF().getCodigoIbge());
//                nfeCabecMsgPR.value.setVersaoDados(this.config.getVersao());
//
//                final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao3.pr.NfeDadosMsg nfeDadosMsgPR = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao3.pr.NfeDadosMsg();
//                nfeDadosMsgPR.getContent().add(ElementStringConverter.read(loteAssinadoXml));
//
//                NfeAutorizacaoSoap12 portPR = new NfeAutorizacao3(new URL(endpoint)).getNfeAutorizacaoServicePort();
//                br.inf.portalfiscal.nfe.wsdl.nfeautorizacao3.pr.NfeAutorizacaoLoteResult resultPR = portPR.nfeAutorizacaoLote(nfeDadosMsgPR, nfeCabecMsgPR);
//                com.fincatto.documentofiscal.nfe310.classes.lote.envio.NFLoteEnvioRetorno nFLoteEnvioRetorno = new DFPersister().read(com.fincatto.documentofiscal.nfe310.classes.lote.envio.NFLoteEnvioRetorno.class, ElementStringConverter.write((Element) resultPR.getContent().get(0)));
//
//                TRetEnviNFe tRetEnviNFePR = new TRetEnviNFe();
//                tRetEnviNFePR.setCStat(nFLoteEnvioRetorno.getStatus());
//                tRetEnviNFePR.setCUF(nFLoteEnvioRetorno.getUf().getCodigo());
//                tRetEnviNFePR.setDhRecbto(new DFLocalDateTimeTransformer().write(nFLoteEnvioRetorno.getDataRecebimento()));
//                TRetEnviNFe.InfRec infRec = new TRetEnviNFe.InfRec();
//                infRec.setNRec(nFLoteEnvioRetorno.getInfoRecebimento() == null ? null : nFLoteEnvioRetorno.getInfoRecebimento().getRecibo());
//                infRec.setTMed(nFLoteEnvioRetorno.getInfoRecebimento() == null ? null : nFLoteEnvioRetorno.getInfoRecebimento().getTempoMedio());
//                tRetEnviNFePR.setInfRec(infRec);
//                if (nFLoteEnvioRetorno.getProtocoloInfo() != null) {
//                    TProtNFe tProtNFe = new TProtNFe();
//                    TProtNFe.InfProt infProt = new TProtNFe.InfProt();
//                    infProt.setCStat(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : nFLoteEnvioRetorno.getProtocoloInfo().getStatus());
//                    infProt.setChNFe(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : nFLoteEnvioRetorno.getProtocoloInfo().getChave());
//                    infProt.setDhRecbto(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : new DFLocalDateTimeTransformer().write(nFLoteEnvioRetorno.getProtocoloInfo().getDataRecebimento()));
//                    infProt.setDigVal(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : nFLoteEnvioRetorno.getProtocoloInfo().getValidador().getBytes());
//                    infProt.setId(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : nFLoteEnvioRetorno.getProtocoloInfo().getIdentificador());
//                    infProt.setNProt(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : nFLoteEnvioRetorno.getProtocoloInfo().getNumeroProtocolo());
//                    infProt.setTpAmb(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : nFLoteEnvioRetorno.getProtocoloInfo().getAmbiente().getCodigo());
//                    infProt.setVerAplic(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : nFLoteEnvioRetorno.getProtocoloInfo().getVersaoAplicacao());
//                    infProt.setXMotivo(nFLoteEnvioRetorno.getProtocoloInfo() == null ? null : nFLoteEnvioRetorno.getProtocoloInfo().getMotivo());
//                    tProtNFe.setInfProt(infProt);
//                    tProtNFe.setSignature(null);
//                    tProtNFe.setVersao(nFLoteEnvioRetorno.getVersaoAplicacao());
//                    tRetEnviNFePR.setProtNFe(tProtNFe);
//                }
//                tRetEnviNFePR.setTpAmb(nFLoteEnvioRetorno.getAmbiente().getCodigo());
//                tRetEnviNFePR.setVerAplic(nFLoteEnvioRetorno.getVersaoAplicacao());
//                tRetEnviNFePR.setVersao(nFLoteEnvioRetorno.getVersao());
//                tRetEnviNFePR.setXMotivo(nFLoteEnvioRetorno.getMotivo());
//
//                return tRetEnviNFePR;
            default : //SVAN
//                // Create the JAXBContext
//                JAXBContext contextSVAN = JAXBContext.newInstance("br.inf.portalfiscal.nfe");
//
//                Unmarshaller jaxbUnmarshallerSVAN = contextSVAN.createUnmarshaller();
//                StringReader readerSVAN = new StringReader(loteAssinadoXml);
//                JAXBElement<TEnviNFe> tEnviNFeSVAN = (JAXBElement<TEnviNFe>) jaxbUnmarshallerSVAN.unmarshal(readerSVAN);
//
//                //envia o lote para a sefaz
//                final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.NfeCabecMsg nfeCabecMsgSVAN = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.NfeCabecMsg();
//                nfeCabecMsgSVAN.setCUF(this.config.getCUF().getCodigoIbge());
//                nfeCabecMsgSVAN.setVersaoDados(this.config.getVersao());
//
//                final br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.NfeDadosMsg nfeDadosMsgSVAN = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.NfeDadosMsg();
//                nfeDadosMsgSVAN.getContent().add(tEnviNFeSVAN);
//
//                br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.NfeAutorizacaoSoap portSVAN = new br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.NfeAutorizacao(new URL(endpoint)).getNfeAutorizacaoSoap();
//                br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.NfeAutorizacaoLoteResult resultSVAN = portSVAN.nfeAutorizacaoLote(nfeDadosMsgSVAN, nfeCabecMsgSVAN);
//                return ((JAXBElement<TRetEnviNFe>) resultSVAN.getContent().get(0)).getValue();
                return null;
        }
    }

    private String nfeToOMElement(final String documento) throws XMLStreamException {
        final XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty(XMLInputFactory.IS_COALESCING, false);
        final XMLStreamReader reader = factory.createXMLStreamReader(new StringReader(documento));
//        final StAXOMBuilder builder = new StAXOMBuilder(reader);
//        final OMElement ome = builder.getDocumentElement();
//        final Iterator<?> children = ome.getChildrenWithLocalName(WSLoteEnvio.NFE_ELEMENTO);
//        while (children.hasNext()) {
//            final OMElement omElement = (OMElement) children.next();
//            if ((omElement != null) && (WSLoteEnvio.NFE_ELEMENTO.equals(omElement.getLocalName()))) {
//                omElement.addAttribute("xmlns", NFeConfig.NAMESPACE, null);
//            }
//        }
//        return ome;
        return null;
    }
}
