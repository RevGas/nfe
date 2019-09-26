//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.11 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2019.09.25 às 09:50:28 AM BRT 
//


package br.inf.portalfiscal.mdfe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Tipo procEvento
 * 
 * <p>Classe Java de TProcEvento complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="TProcEvento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="eventoMDFe" type="{http://www.portalfiscal.inf.br/mdfe}TEvento"/&gt;
 *         &lt;element name="retEventoMDFe" type="{http://www.portalfiscal.inf.br/mdfe}TRetEvento"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="versao" use="required" type="{http://www.portalfiscal.inf.br/mdfe}TVerEvento" /&gt;
 *       &lt;attribute name="ipTransmissor" type="{http://www.portalfiscal.inf.br/mdfe}TIPv4" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TProcEvento", propOrder = {
    "eventoMDFe",
    "retEventoMDFe"
})
public class TProcEvento {

    @XmlElement(required = true)
    protected TEvento eventoMDFe;
    @XmlElement(required = true)
    protected TRetEvento retEventoMDFe;
    @XmlAttribute(name = "versao", required = true)
    protected String versao;
    @XmlAttribute(name = "ipTransmissor")
    protected String ipTransmissor;

    /**
     * Obtém o valor da propriedade eventoMDFe.
     * 
     * @return
     *     possible object is
     *     {@link TEvento }
     *     
     */
    public TEvento getEventoMDFe() {
        return eventoMDFe;
    }

    /**
     * Define o valor da propriedade eventoMDFe.
     * 
     * @param value
     *     allowed object is
     *     {@link TEvento }
     *     
     */
    public void setEventoMDFe(TEvento value) {
        this.eventoMDFe = value;
    }

    /**
     * Obtém o valor da propriedade retEventoMDFe.
     * 
     * @return
     *     possible object is
     *     {@link TRetEvento }
     *     
     */
    public TRetEvento getRetEventoMDFe() {
        return retEventoMDFe;
    }

    /**
     * Define o valor da propriedade retEventoMDFe.
     * 
     * @param value
     *     allowed object is
     *     {@link TRetEvento }
     *     
     */
    public void setRetEventoMDFe(TRetEvento value) {
        this.retEventoMDFe = value;
    }

    /**
     * Obtém o valor da propriedade versao.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersao() {
        return versao;
    }

    /**
     * Define o valor da propriedade versao.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersao(String value) {
        this.versao = value;
    }

    /**
     * Obtém o valor da propriedade ipTransmissor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIpTransmissor() {
        return ipTransmissor;
    }

    /**
     * Define o valor da propriedade ipTransmissor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIpTransmissor(String value) {
        this.ipTransmissor = value;
    }

}
