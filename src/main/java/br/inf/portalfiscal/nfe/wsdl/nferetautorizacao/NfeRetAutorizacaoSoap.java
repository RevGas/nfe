
package br.inf.portalfiscal.nfe.wsdl.nferetautorizacao;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b14002
 * Generated source version: 2.2
 * 
 */
@WebService(name = "NfeRetAutorizacaoSoap", targetNamespace = "http://www.portalfiscal.inf.br/nfe/wsdl/NfeRetAutorizacao")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface NfeRetAutorizacaoSoap {


    /**
     * Serviço destinado a retornar o resultado do processamento do lote de NF-e
     * 
     * @param nfeCabecMsg
     * @param nfeDadosMsg
     * @return
     *     returns br.inf.portalfiscal.nfe.wsdl.nferetautorizacao.NfeRetAutorizacaoLoteResult
     */
    @WebMethod(action = "http://www.portalfiscal.inf.br/nfe/wsdl/NfeRetAutorizacao/nfeRetAutorizacaoLote")
    @WebResult(name = "nfeRetAutorizacaoLoteResult", targetNamespace = "http://www.portalfiscal.inf.br/nfe/wsdl/NfeRetAutorizacao", partName = "nfeRetAutorizacaoLoteResult")
    public NfeRetAutorizacaoLoteResult nfeRetAutorizacaoLote(
        @WebParam(name = "nfeDadosMsg", targetNamespace = "http://www.portalfiscal.inf.br/nfe/wsdl/NfeRetAutorizacao", partName = "nfeDadosMsg")
        NfeDadosMsg nfeDadosMsg,
        @WebParam(name = "nfeCabecMsg", targetNamespace = "http://www.portalfiscal.inf.br/nfe/wsdl/NfeRetAutorizacao", header = true, partName = "nfeCabecMsg")
        NfeCabecMsg nfeCabecMsg);

}