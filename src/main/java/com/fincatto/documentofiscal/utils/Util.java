package com.fincatto.documentofiscal.utils;
import br.inf.portalfiscal.nfe.*;
import br.inf.portalfiscal.nfe.model.evento_generico.Evento_Generico_PL_v101.TRetEnvEvento;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Classe utilitária
 *
 * @author rafael
 */
public class Util {

    /**
     * Metodo para remover caracteres especiais do xml da nota fiscal
     *
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

    public static String fileToString(File file) throws IOException {
        StringBuilder builder = new StringBuilder();
        Files.readAllLines(Paths.get(file.toURI()), StandardCharsets.UTF_8).forEach(line -> {
            builder.append(line);
        });
        return builder.toString();
    }

    public static String marshaller(Object object) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = context.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(new JAXBElement(new QName(convert(object.getClass().getSimpleName())), object.getClass(), object), stringWriter);
        return stringWriter.toString()
                .replace("xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns3=\"http://www.portalfiscal.inf.br/nfe\"", "xmlns=\"http://www.portalfiscal.inf.br/nfe\"")
                .replace("xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", "")
                .replace("xmlns:ns3=\"http://www.portalfiscal.inf.br/nfe\"", "")
                .replace("ns2:", "")
                .replace("ns3:", "");
    }

    public static String marshallerPuro(Object object) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = context.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(new JAXBElement(new QName(convert(object.getClass().getSimpleName())), object.getClass(), object), stringWriter);
        return stringWriter.toString();
    }

    public static String marshllerRetEnviNFe(JAXBElement<TRetEnviNFe> jAXBElement ) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.nfe");
        StringWriter result = new StringWriter();
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(jAXBElement, result);
        return result.toString();
    }

    public static String marshllerRetEnvEvento(JAXBElement<TRetEnvEvento> jAXBElement ) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.nfe.model.evento_generico.Evento_Generico_PL_v101");
        StringWriter result = new StringWriter();
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(jAXBElement, result);
        return result.toString();
    }

    public static String marshllerretConsSitNFe(JAXBElement<TRetConsSitNFe> jAXBElement ) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.nfe");
        StringWriter result = new StringWriter();
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(jAXBElement, result);
        return result.toString();
    }

    public static String marshllerRetRetConsReciNFe(JAXBElement<TRetConsReciNFe> jAXBElement ) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.nfe");
        StringWriter result = new StringWriter();
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(jAXBElement, result);
        return result.toString();
    }

    public static String marshlerNfeProc(JAXBElement<TNfeProc> tNfeProc) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(TNfeProc.class.getPackage().getName());
        StringWriter result = new StringWriter();
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.marshal(tNfeProc, result);
        return result.toString();
    }

    public static String marshlerTnfe(JAXBElement<TNFe> tnfe) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(TNFe.class.getPackage().getName());
        StringWriter result = new StringWriter();
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.marshal(tnfe, result);
        return result.toString();
    }

    public static Object unmarshler(Class element, String value) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(element.getPackage().getName());
        Unmarshaller jaxbUnmarshaller;
        jaxbUnmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(value);
        JAXBElement<Object> object = (JAXBElement<Object>) jaxbUnmarshaller.unmarshal(reader);
        return object.getValue();
    }

    public static String chaveFromTNFe(TNFe tnfe) {
        return tnfe.getInfNFe().getId().replace("NFe", "");
    }

    private static String convert(String objectName) {
        switch (objectName) {
            case "TEnviNFe":
                return "enviNFe";

            case "TNfeProc":
                return "nfeProc";

            case "TNFe":
                return "NFe";

            case "TRetEnvEvento":
                return "retEnvEvento";
//            case "TNFe":
//                return "consReciNFe";
//            case "TNFe":
//                return "consSitNFe";
//            case "TNFe":
//                return "consStatServ";
//            case "TNFe":
//                return "retConsReciNFe";
            case "TRetConsSitNFe":
                return "retConsSitNFe";
            case "TRetConsStatServ":
                return "retConsStatServ";
            case "TRetEnviNFe":
                return "retEnviNFe";
            case "TRetInutNFe":
                return "retInutNFe";
            default:
                return objectName;
        }
    }

}
