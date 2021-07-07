package br.inf.portalfiscal.nfe.wsdl.nfestatusservico4.go;

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
 * JAX-WS RI 2.2.10
 * Generated source version: 2.2
 *
 */
@WebServiceClient(name = "NFeStatusServico4", targetNamespace = "http://www.portalfiscal.inf.br/nfe/wsdl/NFeStatusServico4", wsdlLocation = "https://nfe.sefaz.go.gov.br/nfe/services/NFeStatusServico4?wsdl")
public class NFeStatusServico4
    extends Service
{

    private final static URL NFESTATUSSERVICO4_WSDL_LOCATION;
    private final static WebServiceException NFESTATUSSERVICO4_EXCEPTION;
    private final static QName NFESTATUSSERVICO4_QNAME = new QName("http://www.portalfiscal.inf.br/nfe/wsdl/NFeStatusServico4", "NFeStatusServico4");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://nfe.sefaz.go.gov.br/nfe/services/NFeStatusServico4?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        NFESTATUSSERVICO4_WSDL_LOCATION = url;
        NFESTATUSSERVICO4_EXCEPTION = e;
    }

    public NFeStatusServico4() {
        super(__getWsdlLocation(), NFESTATUSSERVICO4_QNAME);
    }

    public NFeStatusServico4(WebServiceFeature... features) {
        super(__getWsdlLocation(), NFESTATUSSERVICO4_QNAME, features);
    }

    public NFeStatusServico4(URL wsdlLocation) {
        super(wsdlLocation, NFESTATUSSERVICO4_QNAME);
    }

    public NFeStatusServico4(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, NFESTATUSSERVICO4_QNAME, features);
    }

    public NFeStatusServico4(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public NFeStatusServico4(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns NFeStatusServico4Service
     */
    @WebEndpoint(name = "NFeStatusServico4ServicePort")
    public NFeStatusServico4Service getNFeStatusServico4ServicePort() {
        return super.getPort(new QName("http://www.portalfiscal.inf.br/nfe/wsdl/NFeStatusServico4", "NFeStatusServico4ServicePort"), NFeStatusServico4Service.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns NFeStatusServico4Service
     */
    @WebEndpoint(name = "NFeStatusServico4ServicePort")
    public NFeStatusServico4Service getNFeStatusServico4ServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.portalfiscal.inf.br/nfe/wsdl/NFeStatusServico4", "NFeStatusServico4ServicePort"), NFeStatusServico4Service.class, features);
    }

    private static URL __getWsdlLocation() {
        if (NFESTATUSSERVICO4_EXCEPTION!= null) {
            throw NFESTATUSSERVICO4_EXCEPTION;
        }
        return NFESTATUSSERVICO4_WSDL_LOCATION;
    }

}
