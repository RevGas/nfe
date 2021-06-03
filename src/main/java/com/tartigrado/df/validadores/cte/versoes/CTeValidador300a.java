package com.tartigrado.df.validadores.cte.versoes;

import com.fincatto.documentofiscal.validadores.XMLValidador;
import com.tartigrado.df.validadores.cte.CTeValidador;
import com.tartigrado.df.validadores.cte.CTeValidatorFactory;
import com.tartigrado.df.validadores.cte.exception.CTeXSDValidationException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.StringReader;
import java.net.URL;
import java.util.Objects;

public class CTeValidador300a implements CTeValidador {

    private static final String basePath = CTeValidatorFactory.caminhoPadrao + "/PL_CTe_300a/%s";

    private void validarXml(String xml, String xsd) throws CTeXSDValidationException {
        try {
            Objects.requireNonNull(xml);
            final URL xsdPath = XMLValidador.class.getClassLoader().getResource(String.format(basePath, xsd));
            Objects.requireNonNull(xsdPath);
            final SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            final Schema schema = schemaFactory.newSchema(new StreamSource(xsdPath.toURI().toString()));
            schema.newValidator().validate(new StreamSource(new StringReader(xml)));
        } catch (Exception e) {
            throw new CTeXSDValidationException(e);
        }
    }

    @Override
    public void validaTConsStatServ(String tConsStatServ) throws CTeXSDValidationException {
        validarXml(tConsStatServ, "consStatServCTe_v3.00.xsd");
    }

    @Override
    public void validaTEnviCTe(String tEnviCTe) throws CTeXSDValidationException {
        validarXml(tEnviCTe, "enviCTe_v3.00.xsd");
    }

    @Override
    public void validaTConsReciCTe(String tConsReciCTe) throws CTeXSDValidationException {
        validarXml(tConsReciCTe, "consReciCTe_v3.00.xsd");
    }

    @Override
    public void validaTEvento(String tEvento) throws CTeXSDValidationException {
        validarXml(tEvento, "eventoCTe_v3.00.xsd");
    }

    @Override
    public void validaEvCancCTe(String evCancCTe) throws CTeXSDValidationException {
        validarXml(evCancCTe, "eventoCTe_v3.00.xsd");
    }
}
