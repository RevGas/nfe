package br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.svan;

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
@WebService(name = "NFeRecepcaoEvento4Soap", targetNamespace = "http://www.portalfiscal.inf.br/nfe/wsdl/NFeRecepcaoEvento4")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class, br.inf.portalfiscal.nfe.model.evento_cancelamento.Evento_Canc_PL_v101.ObjectFactory.class
})
public interface NFeRecepcaoEvento4SoapCancelamento {


    /**
     * Serviço destinado à recepção de mensagem de Evento da NF-e.
     * 
     * @param nfeDadosMsg
     * @return
     *     returns br.inf.portalfiscal.nfe.wsdl.nferecepcaoevento4.NfeRecepcaoEventoResult
     */
    @WebMethod(action = "http://www.portalfiscal.inf.br/nfe/wsdl/NFeRecepcaoEvento4/nfeRecepcaoEvento")
    @WebResult(name = "nfeRecepcaoEventoResult", targetNamespace = "http://www.portalfiscal.inf.br/nfe/wsdl/NFeRecepcaoEvento4", partName = "nfeRecepcaoEventoResult")
    public NfeRecepcaoEventoResult nfeRecepcaoEvento(
        @WebParam(name = "nfeDadosMsg", targetNamespace = "http://www.portalfiscal.inf.br/nfe/wsdl/NFeRecepcaoEvento4", partName = "nfeDadosMsg")
        NfeDadosMsg nfeDadosMsg);

}
