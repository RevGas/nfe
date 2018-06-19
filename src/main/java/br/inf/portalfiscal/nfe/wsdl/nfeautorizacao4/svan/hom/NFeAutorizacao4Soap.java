package br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.svan.hom;

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
@WebService(name = "NFeAutorizacao4Soap", targetNamespace = "http://www.portalfiscal.inf.br/nfe/wsdl/NFeAutorizacao4")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface NFeAutorizacao4Soap {


    /**
     * Serviço destinado à recepção de mensagens de lote de NF-e
     * 
     * @param nfeDadosMsg
     * @return
     *     returns br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.NfeAutorizacaoLoteResult
     */
    @WebMethod(action = "http://www.portalfiscal.inf.br/nfe/wsdl/NFeAutorizacao4/nfeAutorizacaoLote")
    @WebResult(name = "nfeAutorizacaoLoteResult", targetNamespace = "http://www.portalfiscal.inf.br/nfe/wsdl/NFeAutorizacao4", partName = "nfeAutorizacaoLoteResult")
    public NfeAutorizacaoLoteResult nfeAutorizacaoLote(
        @WebParam(name = "nfeDadosMsg", targetNamespace = "http://www.portalfiscal.inf.br/nfe/wsdl/NFeAutorizacao4", partName = "nfeDadosMsg")
        NfeDadosMsg nfeDadosMsg);

    /**
     * Serviço destinado à recepção de mensagens de lote de NF-e compactada
     * 
     * @param nfeDadosMsgZip
     * @return
     *     returns br.inf.portalfiscal.nfe.wsdl.nfeautorizacao4.NfeAutorizacaoLoteZipResult
     */
    @WebMethod(action = "http://www.portalfiscal.inf.br/nfe/wsdl/NFeAutorizacao4/nfeAutorizacaoLoteZip")
    @WebResult(name = "nfeAutorizacaoLoteZipResult", targetNamespace = "http://www.portalfiscal.inf.br/nfe/wsdl/NFeAutorizacao4", partName = "nfeAutorizacaoLoteZipResult")
    public NfeAutorizacaoLoteZipResult nfeAutorizacaoLoteZip(
        @WebParam(name = "nfeDadosMsgZip", targetNamespace = "http://www.portalfiscal.inf.br/nfe/wsdl/NFeAutorizacao4", partName = "nfeDadosMsgZip")
        String nfeDadosMsgZip);

}
