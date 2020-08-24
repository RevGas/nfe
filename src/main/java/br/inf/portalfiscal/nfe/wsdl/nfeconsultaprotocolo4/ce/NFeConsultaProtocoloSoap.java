package br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.ce;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.10
 * Generated source version: 2.2
 * 
 */
@WebService(name = "NFeConsultaProtocoloSoap", targetNamespace = "http://www.portalfiscal.inf.br/nfe/wsdl/NFeConsultaProtocolo4")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface NFeConsultaProtocoloSoap {


    /**
     * Servico destinado ao atendimento de solicitacoes de consulta da situacao atual da NF-e
     * 
     * @param nfeDadosMsg
     * @return
     *     returns br.inf.portalfiscal.nfe.wsdl.nfeconsultaprotocolo4.NfeResultMsg
     */
    @WebMethod(action = "http://www.portalfiscal.inf.br/nfe/wsdl/NFeConsultaProtocolo4/nfeConsultaNF")
    @WebResult(name = "nfeResultMsg", targetNamespace = "http://www.portalfiscal.inf.br/nfe/wsdl/NFeConsultaProtocolo4", partName = "nfeResultMsg")
    public NfeResultMsg nfeConsultaNF(
        @WebParam(name = "nfeDadosMsg", targetNamespace = "http://www.portalfiscal.inf.br/nfe/wsdl/NFeConsultaProtocolo4", partName = "nfeDadosMsg")
        NfeDadosMsg nfeDadosMsg);

}