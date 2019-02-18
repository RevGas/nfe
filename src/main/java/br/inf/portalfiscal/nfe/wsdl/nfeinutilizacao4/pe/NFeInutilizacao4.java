package br.inf.portalfiscal.nfe.wsdl.nfeinutilizacao4.pe;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * 
 * Servico destinado ao atendimento de solicitacoes de inutilizacao de numeracao.  
 * 
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.10
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "NFeInutilizacao4", targetNamespace = "http://www.portalfiscal.inf.br/nfe/wsdl/NFeInutilizacao4", wsdlLocation = "https://nfe.sefaz.pe.gov.br/nfe-service/services/NFeInutilizacao4?wsdl")
public class NFeInutilizacao4
    extends Service
{

    private final static URL NFEINUTILIZACAO4_WSDL_LOCATION;
    private final static WebServiceException NFEINUTILIZACAO4_EXCEPTION;
    private final static QName NFEINUTILIZACAO4_QNAME = new QName("http://www.portalfiscal.inf.br/nfe/wsdl/NFeInutilizacao4", "NFeInutilizacao4");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://nfe.sefaz.pe.gov.br/nfe-service/services/NFeInutilizacao4?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        NFEINUTILIZACAO4_WSDL_LOCATION = url;
        NFEINUTILIZACAO4_EXCEPTION = e;
    }

    public NFeInutilizacao4() {
        super(__getWsdlLocation(), NFEINUTILIZACAO4_QNAME);
    }

    public NFeInutilizacao4(WebServiceFeature... features) {
        super(__getWsdlLocation(), NFEINUTILIZACAO4_QNAME, features);
    }

    public NFeInutilizacao4(URL wsdlLocation) {
        super(wsdlLocation, NFEINUTILIZACAO4_QNAME);
    }

    public NFeInutilizacao4(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, NFEINUTILIZACAO4_QNAME, features);
    }

    public NFeInutilizacao4(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public NFeInutilizacao4(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns NFeInutilizacao4Soap12
     */
    @WebEndpoint(name = "NFeInutilizacao4ServicePort")
    public NFeInutilizacao4Soap12 getNFeInutilizacao4ServicePort() {
        return super.getPort(new QName("http://www.portalfiscal.inf.br/nfe/wsdl/NFeInutilizacao4", "NFeInutilizacao4ServicePort"), NFeInutilizacao4Soap12.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns NFeInutilizacao4Soap12
     */
    @WebEndpoint(name = "NFeInutilizacao4ServicePort")
    public NFeInutilizacao4Soap12 getNFeInutilizacao4ServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.portalfiscal.inf.br/nfe/wsdl/NFeInutilizacao4", "NFeInutilizacao4ServicePort"), NFeInutilizacao4Soap12.class, features);
    }

    private static URL __getWsdlLocation() {
        if (NFEINUTILIZACAO4_EXCEPTION!= null) {
            throw NFEINUTILIZACAO4_EXCEPTION;
        }
        return NFEINUTILIZACAO4_WSDL_LOCATION;
    }

}
