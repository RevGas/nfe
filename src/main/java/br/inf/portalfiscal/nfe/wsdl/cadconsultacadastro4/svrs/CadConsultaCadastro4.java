package br.inf.portalfiscal.nfe.wsdl.cadconsultacadastro4.svrs;

import java.net.MalformedURLException;
import java.net.URL;
import javax.jws.HandlerChain;
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
@WebServiceClient(name = "CadConsultaCadastro4", targetNamespace = "http://www.portalfiscal.inf.br/nfe/wsdl/CadConsultaCadastro4", wsdlLocation = "https://cad.svrs.rs.gov.br/ws/cadconsultacadastro/cadconsultacadastro4.asmx?WSDL")
@HandlerChain(file="handler-nfeconsultacadastro.xml")
public class CadConsultaCadastro4
    extends Service
{

    private final static URL CADCONSULTACADASTRO4_WSDL_LOCATION;
    private final static WebServiceException CADCONSULTACADASTRO4_EXCEPTION;
    private final static QName CADCONSULTACADASTRO4_QNAME = new QName("http://www.portalfiscal.inf.br/nfe/wsdl/CadConsultaCadastro4", "CadConsultaCadastro4");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://cad.svrs.rs.gov.br/ws/cadconsultacadastro/cadconsultacadastro4.asmx?WSDL");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CADCONSULTACADASTRO4_WSDL_LOCATION = url;
        CADCONSULTACADASTRO4_EXCEPTION = e;
    }

    public CadConsultaCadastro4() {
        super(__getWsdlLocation(), CADCONSULTACADASTRO4_QNAME);
    }

    public CadConsultaCadastro4(WebServiceFeature... features) {
        super(__getWsdlLocation(), CADCONSULTACADASTRO4_QNAME, features);
    }

    public CadConsultaCadastro4(URL wsdlLocation) {
        super(wsdlLocation, CADCONSULTACADASTRO4_QNAME);
    }

    public CadConsultaCadastro4(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CADCONSULTACADASTRO4_QNAME, features);
    }

    public CadConsultaCadastro4(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CadConsultaCadastro4(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns CadConsultaCadastro4Soap
     */
    @WebEndpoint(name = "CadConsultaCadastro4Soap")
    public CadConsultaCadastro4Soap getCadConsultaCadastro4Soap() {
        return super.getPort(new QName("http://www.portalfiscal.inf.br/nfe/wsdl/CadConsultaCadastro4", "CadConsultaCadastro4Soap"), CadConsultaCadastro4Soap.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CadConsultaCadastro4Soap
     */
    @WebEndpoint(name = "CadConsultaCadastro4Soap")
    public CadConsultaCadastro4Soap getCadConsultaCadastro4Soap(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.portalfiscal.inf.br/nfe/wsdl/CadConsultaCadastro4", "CadConsultaCadastro4Soap"), CadConsultaCadastro4Soap.class, features);
    }

    /**
     *
     * @return
     *     returns CadConsultaCadastro4Soap
     */
    @WebEndpoint(name = "CadConsultaCadastro4Soap12")
    public CadConsultaCadastro4Soap getCadConsultaCadastro4Soap12() {
        return super.getPort(new QName("http://www.portalfiscal.inf.br/nfe/wsdl/CadConsultaCadastro4", "CadConsultaCadastro4Soap12"), CadConsultaCadastro4Soap.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CadConsultaCadastro4Soap
     */
    @WebEndpoint(name = "CadConsultaCadastro4Soap12")
    public CadConsultaCadastro4Soap getCadConsultaCadastro4Soap12(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.portalfiscal.inf.br/nfe/wsdl/CadConsultaCadastro4", "CadConsultaCadastro4Soap12"), CadConsultaCadastro4Soap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CADCONSULTACADASTRO4_EXCEPTION!= null) {
            throw CADCONSULTACADASTRO4_EXCEPTION;
        }
        return CADCONSULTACADASTRO4_WSDL_LOCATION;
    }

}