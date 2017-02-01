
package br.inf.portalfiscal.mdfe.wsdl.mdfeconsulta;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b14002
 * Generated source version: 2.2
 * 
 */
@WebService(name = "MDFeConsultaSoap12", targetNamespace = "http://www.portalfiscal.inf.br/mdfe/wsdl/MDFeConsulta")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class, br.inf.portalfiscal.mdfe.ObjectFactory.class
})
public interface MDFeConsultaSoap12 {


    /**
     * 
     * @param mdfeDadosMsg
     * @param mdfeCabecMsg
     * @return
     *     returns br.inf.portalfiscal.mdfe.wsdl.mdfeconsulta.MdfeConsultaMDFResult
     */
    @WebMethod(action = "http://www.portalfiscal.inf.br/mdfe/wsdl/MDFeConsulta/mdfeConsultaMDF")
    @WebResult(name = "mdfeConsultaMDFResult", targetNamespace = "http://www.portalfiscal.inf.br/mdfe/wsdl/MDFeConsulta", partName = "mdfeConsultaMDFResult")
    public MdfeConsultaMDFResult mdfeConsultaMDF(
        @WebParam(name = "mdfeDadosMsg", targetNamespace = "http://www.portalfiscal.inf.br/mdfe/wsdl/MDFeConsulta", partName = "mdfeDadosMsg")
        MdfeDadosMsg mdfeDadosMsg,
        @WebParam(name = "mdfeCabecMsg", targetNamespace = "http://www.portalfiscal.inf.br/mdfe/wsdl/MDFeConsulta", header = true, mode = WebParam.Mode.INOUT, partName = "mdfeCabecMsg")
        Holder<MdfeCabecMsg> mdfeCabecMsg);

}
