package br.inf.portalfiscal.cte.wsdl.cteinutilizacao.svrs.hom;

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
@WebService(name = "CteInutilizacaoSoap12", targetNamespace = "http://www.portalfiscal.inf.br/cte/wsdl/CteInutilizacao")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface CteInutilizacaoSoap12 {


    /**
     * 
     * @param cteDadosMsg
     * @return
     *     returns br.inf.portalfiscal.cte.wsdl.cteinutilizacao.CteInutilizacaoCTResult
     */
    @WebMethod(action = "http://www.portalfiscal.inf.br/cte/wsdl/CteInutilizacao/cteInutilizacaoCT")
    @WebResult(name = "cteInutilizacaoCTResult", targetNamespace = "http://www.portalfiscal.inf.br/cte/wsdl/CteInutilizacao", partName = "cteInutilizacaoCTResult")
    public CteInutilizacaoCTResult cteInutilizacaoCT(
        @WebParam(name = "cteDadosMsg", targetNamespace = "http://www.portalfiscal.inf.br/cte/wsdl/CteInutilizacao", partName = "cteDadosMsg")
        CteDadosMsg cteDadosMsg);

}
