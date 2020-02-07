package com.fincatto.documentofiscal.utils;

import br.inf.portalfiscal.nfe.TEnviNFe;
import br.inf.portalfiscal.nfe.TNFe;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.StringWriter;

/**
 * Classe utilitária
 * @author rafael
 */
public class Util {

    /**
     * Metodo para remover caracteres especiais do xml da nota fiscal
     * @param xml
     * @return 
     */
    public static String convertToASCII2(String xml) {
        return xml.replaceAll("[ãâàáä]", "a")
            .replaceAll("[êèéë]", "e")
            .replaceAll("[îìíï]", "i")
            .replaceAll("[õôòóö]", "o")
            .replaceAll("[ûúùü]", "u")
            .replaceAll("[ÃÂÀÁÄ]", "A")
            .replaceAll("[ÊÈÉË]", "E")
            .replaceAll("[ÎÌÍÏ]", "I")
            .replaceAll("[ÕÔÒÓÖ]", "O")
            .replaceAll("[ÛÙÚÜ]", "U")
            .replace('ç', 'c')
            .replace('Ç', 'C')
            .replace('ñ', 'n')
            .replace('Ñ', 'N');
    }

    public static String tNFeToString(TNFe tnfe) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(TNFe.class);
        Marshaller marshaller = context.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(new JAXBElement(new QName("NFe"), TNFe.class, tnfe), stringWriter);
        return stringWriter.toString();
    }

    public static String tEnviNFeToString(TEnviNFe tEnviNFe) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(TEnviNFe.class);
        Marshaller marshaller = context.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(new JAXBElement(new QName("enviNFe"), TEnviNFe.class, tEnviNFe), stringWriter);
        return stringWriter.toString();
    }

    public static String chaveFromTNFe(TNFe tnfe){
        return tnfe.getInfNFe().getId().replace("NFe","");
    }
}
