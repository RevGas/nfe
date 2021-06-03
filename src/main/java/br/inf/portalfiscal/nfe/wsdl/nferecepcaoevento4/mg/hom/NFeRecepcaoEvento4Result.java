package br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.mg.hom;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de nFeRecepcaoEvento4Result complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="nFeRecepcaoEvento4Result">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="retEnvEvento" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nFeRecepcaoEvento4Result", propOrder = {
    "retEnvEvento"
})
public class NFeRecepcaoEvento4Result {

    @XmlMixed
    @XmlAnyElement(lax = true)
    protected List<Object> retEnvEvento;

    /**
     * Gets the value of the retEnvEvento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the retEnvEvento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRetEnvEvento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getRetEnvEvento() {
        if (retEnvEvento == null) {
            retEnvEvento = new ArrayList<Object>();
        }
        return this.retEnvEvento;
    }

}
