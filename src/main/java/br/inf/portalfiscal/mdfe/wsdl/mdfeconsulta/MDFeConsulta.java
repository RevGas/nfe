
package br.inf.portalfiscal.mdfe.wsdl.mdfeconsulta;

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
@WebServiceClient(name = "MDFeConsulta", targetNamespace = "http://www.portalfiscal.inf.br/mdfe/wsdl/MDFeConsulta", wsdlLocation = "https://mdfe.svrs.rs.gov.br/ws/MDFeConsulta/MDFeConsulta.asmx?WSDL")
public class MDFeConsulta
    extends Service
{

    private final static URL MDFECONSULTA_WSDL_LOCATION;
    private final static WebServiceException MDFECONSULTA_EXCEPTION;
    private final static QName MDFECONSULTA_QNAME = new QName("http://www.portalfiscal.inf.br/mdfe/wsdl/MDFeConsulta", "MDFeConsulta");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://mdfe.svrs.rs.gov.br/ws/MDFeConsulta/MDFeConsulta.asmx?WSDL");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        MDFECONSULTA_WSDL_LOCATION = url;
        MDFECONSULTA_EXCEPTION = e;
    }

    public MDFeConsulta() {
        super(__getWsdlLocation(), MDFECONSULTA_QNAME);
    }

    public MDFeConsulta(WebServiceFeature... features) {
        super(__getWsdlLocation(), MDFECONSULTA_QNAME, features);
    }

    public MDFeConsulta(URL wsdlLocation) {
        super(wsdlLocation, MDFECONSULTA_QNAME);
    }

    public MDFeConsulta(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, MDFECONSULTA_QNAME, features);
    }

    public MDFeConsulta(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public MDFeConsulta(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns MDFeConsultaSoap12
     */
    @WebEndpoint(name = "MDFeConsultaSoap12")
    public MDFeConsultaSoap12 getMDFeConsultaSoap12() {
        return super.getPort(new QName("http://www.portalfiscal.inf.br/mdfe/wsdl/MDFeConsulta", "MDFeConsultaSoap12"), MDFeConsultaSoap12.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns MDFeConsultaSoap12
     */
    @WebEndpoint(name = "MDFeConsultaSoap12")
    public MDFeConsultaSoap12 getMDFeConsultaSoap12(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.portalfiscal.inf.br/mdfe/wsdl/MDFeConsulta", "MDFeConsultaSoap12"), MDFeConsultaSoap12.class, features);
    }

    private static URL __getWsdlLocation() {
        if (MDFECONSULTA_EXCEPTION!= null) {
            throw MDFECONSULTA_EXCEPTION;
        }
        return MDFECONSULTA_WSDL_LOCATION;
    }

}
