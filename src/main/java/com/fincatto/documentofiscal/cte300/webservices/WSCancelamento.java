package com.fincatto.documentofiscal.cte300.webservices;

import br.inf.portalfiscal.cte.EvCancCTe;
import br.inf.portalfiscal.cte.TEvento;
import br.inf.portalfiscal.cte.TRetEvento;
import com.fincatto.documentofiscal.DFLog;

import com.fincatto.documentofiscal.cte300.CTeConfig;
import com.fincatto.documentofiscal.cte300.parsers.CTChaveParser;
import com.fincatto.documentofiscal.cte300.parsers.CTeParser;
import com.fincatto.documentofiscal.utils.DFAssinaturaDigital;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

class WSCancelamento implements DFLog {

    private static final String DESCRICAO_EVENTO = "Cancelamento";
    private static final String VERSAO_LEIAUTE = "3.00";
    private static final String EVENTO_CANCELAMENTO = "110111";
    private final CTeConfig config;

    WSCancelamento(final CTeConfig config) {
        this.config = config;
    }

    TRetEvento cancelaCTe(final String chCTe, final String nProt, final String XJus) throws Exception {
        final String xmlAssinado = new DFAssinaturaDigital(this.config).assinarDocumento(CTeParser.parserTEvento(gerarDadosCancelamento(chCTe, nProt, XJus)), "infEvento");
        JAXBElement<TEvento> eTEvento = CTeParser.parserTEvento(xmlAssinado);
        return efetuaCancelamento(eTEvento);
    }

    private TRetEvento efetuaCancelamento(JAXBElement<TEvento> eTEvento) throws Exception {
         return GatewayEvento.valueOfCodigoUF(new CTChaveParser().getTUf(this.config.getCUF().getCodigoIbge())).getTRetEvento(eTEvento, this.config);
    }
    
    private TEvento gerarDadosCancelamento(final String chCTe, final String nProt, final String XJus) throws JAXBException, ParserConfigurationException {
        final CTChaveParser chaveParser = new CTChaveParser(chCTe);
        
        EvCancCTe evCancCTe = new EvCancCTe();
        evCancCTe.setDescEvento(DESCRICAO_EVENTO);
        evCancCTe.setNProt(nProt);
        evCancCTe.setXJust(XJus);
        
        TEvento.InfEvento.DetEvento detEvento = new TEvento.InfEvento.DetEvento();
        detEvento.setAny(getEvCancCTeElement(evCancCTe));
        detEvento.setVersaoEvento(VERSAO_LEIAUTE);
        
        TEvento.InfEvento infEvento = new TEvento.InfEvento();
        infEvento.setCNPJ(chaveParser.getCnpjEmitente());
        infEvento.setCOrgao(chaveParser.getNFUnidadeFederativa().getCodigoIbge());
        infEvento.setChCTe(chCTe);
        infEvento.setDetEvento(detEvento);
        infEvento.setDhEvento(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZZZ").format(ZonedDateTime.now()));
        infEvento.setId(String.format("ID%s%s0%s", WSCancelamento.EVENTO_CANCELAMENTO, chCTe, "1"));
        infEvento.setNSeqEvento("1");
        infEvento.setTpAmb(this.config.getAmbiente().getCodigo());
        infEvento.setTpEvento(EVENTO_CANCELAMENTO);
                
        TEvento tEvento = new TEvento();
        tEvento.setInfEvento(infEvento);
        
        return tEvento;
    }
    
    private static Element getEvCancCTeElement(EvCancCTe evCancCTe) throws JAXBException, ParserConfigurationException {
        // Create the JAXBContext
        JAXBContext jc = JAXBContext.newInstance(EvCancCTe.class);

        // Create the Document
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.newDocument();

        // Marshal the Object to a Document
        Marshaller marshallerrodo = jc.createMarshaller();
        marshallerrodo.marshal(evCancCTe, document);

        return document.getDocumentElement();
    }
    
}