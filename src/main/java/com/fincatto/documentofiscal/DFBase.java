package com.fincatto.documentofiscal;

import org.simpleframework.xml.core.Persister;

import com.fincatto.documentofiscal.persister.DFPersister;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBException;
import java.io.Serializable;
import java.io.StringWriter;


/**
 * Classe utilizada como base para objetos serializaveis.<br>
 * Automatiza a transformacao para xml no metodo toString.
 */
public abstract class DFBase implements Serializable {
    private static final long serialVersionUID = 6887612399839814676L;

    /**
     * Metodo que serializa o objeto para String.
     * Por padrao, usara o {@link DFPersister}.
     *
     * @return String serializada do objeto.
     */
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
