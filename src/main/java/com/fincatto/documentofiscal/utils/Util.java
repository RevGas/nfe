package com.fincatto.documentofiscal.utils;

import br.inf.portalfiscal.cte.*;
import br.inf.portalfiscal.cte.TConsStatServ;
import br.inf.portalfiscal.nfe.*;
import br.inf.portalfiscal.nfe.model.evento_generico.Evento_Generico_PL_v101.TRetEnvEvento;
import com.fincatto.documentofiscal.cte300.classes.consultastatusservico.CTeConsStatServ;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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
                .replace("xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"\n" +
                        "    xmlns:ns3=\"http://www.portalfiscal.inf.br/cte\"", "")
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

    public static String marshallerCTe(Object object) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.cte");
        Marshaller marshaller = context.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(new JAXBElement(new QName(convert(object.getClass().getSimpleName())), object.getClass(), object), stringWriter);
        return stringWriter.toString();
    }

    public static String marshllerCTeConsStatServ(JAXBElement<TConsStatServ> jAXBElement) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.cte");
        StringWriter result = new StringWriter();
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(jAXBElement, result);
        return result.toString();
    }

    public static String marshllerRetEnviNFe(JAXBElement<TRetEnviNFe> jAXBElement) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.nfe");
        StringWriter result = new StringWriter();
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(jAXBElement, result);
        return result.toString();
    }

    public static String marshllerRetEnvEvento(JAXBElement<TRetEnvEvento> jAXBElement) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.nfe.model.evento_generico.Evento_Generico_PL_v101");
        StringWriter result = new StringWriter();
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(jAXBElement, result);
        return result.toString();
    }

    public static String marshllerRetEnvEventoCancelamento(JAXBElement<br.inf.portalfiscal.nfe.model.evento_cancelamento.Evento_Canc_PL_v101.TRetEnvEvento> jAXBElement) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.nfe.model.evento_cancelamento.Evento_Canc_PL_v101");
        StringWriter result = new StringWriter();
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(jAXBElement, result);
        return result.toString();
    }

    public static String marshllerRetEnvEventoCartaCorrecao(JAXBElement<br.inf.portalfiscal.nfe.model.evento_carta_correcao.Evento_CCe_PL_v101.TRetEnvEvento> jAXBElement) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.nfe.model.evento_carta_correcao.Evento_CCe_PL_v101");
        StringWriter result = new StringWriter();
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(jAXBElement, result);
        return result.toString();
    }


    public static String marshllerRetEnvEventoManifestacao(JAXBElement<br.inf.portalfiscal.nfe.model.evento_manifesta_destinatario.Evento_ManifestaDest_PL_v101.TRetEnvEvento> jAXBElement) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.nfe.model.evento_manifesta_destinatario.Evento_ManifestaDest_PL_v101");
        StringWriter result = new StringWriter();
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(jAXBElement, result);
        return result.toString();
    }

    public static String marshllerRetInutNFe(JAXBElement<TRetInutNFe> jAXBElement) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.nfe");
        StringWriter result = new StringWriter();
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(jAXBElement, result);
        return result.toString();
    }

    public static String marshllerretConsSitNFe(JAXBElement<TRetConsSitNFe> jAXBElement) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.nfe");
        StringWriter result = new StringWriter();
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(jAXBElement, result);
        return result.toString();
    }

    public static String marshllerRetRetConsReciNFe(JAXBElement<TRetConsReciNFe> jAXBElement) throws JAXBException {
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

    public static CteProc unmarshlerCTe(Class element, String value) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(element.getPackage().getName());
        Unmarshaller jaxbUnmarshaller;
        jaxbUnmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(value);
        return (CteProc) jaxbUnmarshaller.unmarshal(reader);
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
            case "TRetConsSitNFe":
                return "retConsSitNFe";
            case "TRetConsStatServ":
                return "retConsStatServ";
            case "TRetEnviNFe":
                return "retEnviNFe";
            case "TRetInutNFe":
                return "retInutNFe";
            case "TConsStatServ":
                return "consStatServCte";
            default:
                return objectName;
        }
    }

    public static String chaveFromCTe(TCTe tcTe) {
        return tcTe.getInfCte().getId().replace("CTe", "");
    }

    public static String chaveFromtRetConsReciCTe(TRetConsReciCTe tRetConsReciCTe) {
        return tRetConsReciCTe.getProtCTe().get(0).getInfProt().getChCTe();
    }

    public static String chaveFromtRetCTe(TRetCTe tRetConsReciCTe) {
        return tRetConsReciCTe.getProtCTe().getInfProt().getChCTe();
    }

    public static String marshllerRetConsReciCTe(JAXBElement<TRetConsReciCTe> retConsReciCTe) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.cte");
        StringWriter result = new StringWriter();
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(retConsReciCTe, result);
        return result.toString();
    }

    public static String marshllerRetCTe(JAXBElement<TRetCTe> retCTe) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.cte");
        StringWriter result = new StringWriter();
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(retCTe, result);
        return result.toString();
    }

    public static String marshallerCteProc(CteProc cteProc) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.cte");
        Marshaller marshaller = context.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(new JAXBElement(new QName("cteProc"), CteProc.class, cteProc), stringWriter);
        return stringWriter.toString()
                .replace("xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"","")
                .replace("xmlns:ns3=\"http://www.portalfiscal.inf.br/cte\"",
                        "xmlns=\"http://www.portalfiscal.inf.br/cte\"");
    }

    public static String marshllerTConsReciCTe(JAXBElement<TConsReciCTe> parserTConsReciCTe) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("br.inf.portalfiscal.cte");
        StringWriter result = new StringWriter();
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(parserTConsReciCTe, result);
        return result.toString();
    }

    public static String compress(String str) throws Exception {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream obj = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(obj);
        gzip.write(str.getBytes(StandardCharsets.UTF_8));
        gzip.close();
        return Base64.encode(obj.toByteArray());
    }

    public static String decompress(String encoded) throws Exception {
        GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(Base64.decode(encoded)));
        BufferedReader br = new BufferedReader(new InputStreamReader(gis, StandardCharsets.UTF_8));
        StringBuilder outStr = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            outStr.append(line);
        }

        return outStr.toString();
    }
}
