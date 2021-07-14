package com.fincatto.documentofiscal.cte300.parsers;

import br.inf.portalfiscal.cte.*;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

public class CTeParser {

    public static String parserTEnviCTe(final TEnviCTe tEnviCTe) throws JAXBException {
        br.inf.portalfiscal.cte.ObjectFactory factoryObject = new br.inf.portalfiscal.cte.ObjectFactory();

        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.cte");
        Marshaller marshaller = context.createMarshaller();

        JAXBElement<TEnviCTe> etEnviCTe = factoryObject.createEnviCTe(tEnviCTe);
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(etEnviCTe, stringWriter);
        return stringWriter.toString();
    }


    public static String parserTCTe(TCTe tcTe) throws JAXBException {
        br.inf.portalfiscal.cte.ObjectFactory factoryObject = new br.inf.portalfiscal.cte.ObjectFactory();
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.cte");
        Marshaller marshaller = context.createMarshaller();

        JAXBElement<TCTe> tcTeJAXBElement = factoryObject.createCTe(tcTe);
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(tcTeJAXBElement, stringWriter);
        return stringWriter.toString();
    }
    
    public static JAXBElement<TEnviCTe> parserTEnviCTe(final String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.cte");
        
        StringReader reader = new StringReader(xml);
        Unmarshaller unmarshaller = context.createUnmarshaller();
      
        JAXBElement<TEnviCTe> etEnviCTe = (JAXBElement<TEnviCTe>) unmarshaller.unmarshal(new StreamSource(reader));
        
        return etEnviCTe;
    }
    
    public static JAXBElement<TConsReciCTe> parserTConsReciCTe(final TConsReciCTe tConsReciCTe) throws JAXBException {
        br.inf.portalfiscal.cte.ObjectFactory factoryObject = new br.inf.portalfiscal.cte.ObjectFactory();
                
        JAXBElement<TConsReciCTe> eTConsReciCTe = factoryObject.createConsReciCTe(tConsReciCTe);
        
        return eTConsReciCTe;
    }
    
    public static String parserTEvento(final TEvento tEvento) throws JAXBException {
        br.inf.portalfiscal.cte.ObjectFactory factoryObject = new br.inf.portalfiscal.cte.ObjectFactory();

        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.cte");
        Marshaller marshaller = context.createMarshaller();

        JAXBElement<TEvento> eTEvento = factoryObject.createEventoCTe(tEvento);
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(eTEvento, stringWriter);
        return stringWriter.toString();
    }
            
    public static JAXBElement<TEvento> parserTEvento(final String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.cte");
        
        StringReader reader = new StringReader(xml);
        Unmarshaller unmarshaller = context.createUnmarshaller();
      
        JAXBElement<TEvento> eTEvento = (JAXBElement<TEvento>) unmarshaller.unmarshal(new StreamSource(reader));
        
        return eTEvento;
    }
    
}
