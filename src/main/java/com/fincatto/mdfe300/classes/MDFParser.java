package com.fincatto.mdfe300.classes;

import br.inf.portalfiscal.mdfe.MdfeProc;
import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

public class MDFParser {
    
    public static MdfeProc parser(String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.mdfe");
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(xml);
        MdfeProc mdfeProc = (MdfeProc) unmarshaller.unmarshal(new StreamSource(reader));
        return mdfeProc;
    }
    
}
