package br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.mg.hom;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de nFeAutorizacao4LoteResult complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="nFeAutorizacao4LoteResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="retEnviNFe" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nFeAutorizacao4LoteResult", propOrder = {
    "retEnviNFe"
})
public class NFeAutorizacao4LoteResult {

    @XmlMixed
    @XmlAnyElement(lax = true)
    protected List<Object> retEnviNFe;

    /**
     * Gets the value of the retEnviNFe property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the retEnviNFe property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRetEnviNFe().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getRetEnviNFe() {
        if (retEnviNFe == null) {
            retEnviNFe = new ArrayList<Object>();
        }
        return this.retEnviNFe;
    }

}
