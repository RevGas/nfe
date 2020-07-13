package br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.mg;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de nfeResultMsg complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="nfeResultMsg">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="retConsSitNFe" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nfeResultMsg", propOrder = {
    "retConsSitNFe"
})
public class NfeResultMsg {

    protected List<Object> retConsSitNFe;

    /**
     * Gets the value of the retConsSitNFe property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the retConsSitNFe property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRetConsSitNFe().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getRetConsSitNFe() {
        if (retConsSitNFe == null) {
            retConsSitNFe = new ArrayList<Object>();
        }
        return this.retConsSitNFe;
    }

}
