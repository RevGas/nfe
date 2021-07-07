
package br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao2.svan;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b14002
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "NfeInutilizacao2", targetNamespace = "http://www.portalfiscal.inf.br/nfe/wsdl/NfeInutilizacao2", wsdlLocation = "https://www.sefazvirtual.fazenda.gov.br/NfeInutilizacao2/NfeInutilizacao2.asmx?WSDL")
public class NfeInutilizacao2
    extends Service
{

    private final static URL NFEINUTILIZACAO2_WSDL_LOCATION;
    private final static WebServiceException NFEINUTILIZACAO2_EXCEPTION;
    private final static QName NFEINUTILIZACAO2_QNAME = new QName("http://www.portalfiscal.inf.br/nfe/wsdl/NfeInutilizacao2", "NfeInutilizacao2");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://www.sefazvirtual.fazenda.gov.br/NfeInutilizacao2/NfeInutilizacao2.asmx?WSDL");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        NFEINUTILIZACAO2_WSDL_LOCATION = url;
        NFEINUTILIZACAO2_EXCEPTION = e;
    }

    public NfeInutilizacao2() {
        super(__getWsdlLocation(), NFEINUTILIZACAO2_QNAME);
    }

    public NfeInutilizacao2(WebServiceFeature... features) {
        super(__getWsdlLocation(), NFEINUTILIZACAO2_QNAME, features);
    }

    public NfeInutilizacao2(URL wsdlLocation) {
        super(wsdlLocation, NFEINUTILIZACAO2_QNAME);
    }

    public NfeInutilizacao2(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, NFEINUTILIZACAO2_QNAME, features);
    }

    public NfeInutilizacao2(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public NfeInutilizacao2(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns NfeInutilizacao2Soap
     */
    @WebEndpoint(name = "NfeInutilizacao2Soap")
    public NfeInutilizacao2Soap getNfeInutilizacao2Soap() {
        return super.getPort(new QName("http://www.portalfiscal.inf.br/nfe/wsdl/NfeInutilizacao2", "NfeInutilizacao2Soap"), NfeInutilizacao2Soap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns NfeInutilizacao2Soap
     */
    @WebEndpoint(name = "NfeInutilizacao2Soap")
    public NfeInutilizacao2Soap getNfeInutilizacao2Soap(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.portalfiscal.inf.br/nfe/wsdl/NfeInutilizacao2", "NfeInutilizacao2Soap"), NfeInutilizacao2Soap.class, features);
    }

    /**
     * 
     * @return
     *     returns NfeInutilizacao2Soap
     */
    @WebEndpoint(name = "NfeInutilizacao2Soap12")
    public NfeInutilizacao2Soap getNfeInutilizacao2Soap12() {
        return super.getPort(new QName("http://www.portalfiscal.inf.br/nfe/wsdl/NfeInutilizacao2", "NfeInutilizacao2Soap12"), NfeInutilizacao2Soap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns NfeInutilizacao2Soap
     */
    @WebEndpoint(name = "NfeInutilizacao2Soap12")
    public NfeInutilizacao2Soap getNfeInutilizacao2Soap12(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.portalfiscal.inf.br/nfe/wsdl/NfeInutilizacao2", "NfeInutilizacao2Soap12"), NfeInutilizacao2Soap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (NFEINUTILIZACAO2_EXCEPTION!= null) {
            throw NFEINUTILIZACAO2_EXCEPTION;
        }
        return NFEINUTILIZACAO2_WSDL_LOCATION;
    }

}
