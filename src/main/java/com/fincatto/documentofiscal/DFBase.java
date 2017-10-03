package com.fincatto.documentofiscal;

import java.io.StringWriter;

import org.simpleframework.xml.core.Persister;

import com.fincatto.documentofiscal.persister.DFPersister;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBException;

/**
 * Classe utilizada como base para objetos serializaveis.<br>
 * Automatiza a transformacao para xml no metodo toString.
 */
public abstract class DFBase {

    @Override
    public String toString() {
        final Persister persister = new DFPersister();
        try (StringWriter writer = new StringWriter()) {
            persister.write(this, writer);
            return writer.toString();
        } catch (final Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public String marshaller() throws JAXBException {
        StringWriter stringWriter = new StringWriter();
        JAXB.marshal(this, stringWriter);
        return stringWriter.toString();
    }

}
