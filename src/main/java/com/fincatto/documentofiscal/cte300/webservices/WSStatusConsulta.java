package com.fincatto.documentofiscal.cte300.webservices;

import br.inf.portalfiscal.cte.TConsStatServ;
import br.inf.portalfiscal.cte.TRetConsStatServ;
import br.inf.portalfiscal.cte.wsdl.ctestatusservico.svrs.hom.CteCabecMsg;
import br.inf.portalfiscal.cte.wsdl.ctestatusservico.svrs.hom.CteDadosMsg;
import br.inf.portalfiscal.cte.wsdl.ctestatusservico.svrs.hom.CteStatusServico;
import br.inf.portalfiscal.cte.wsdl.ctestatusservico.svrs.hom.CteStatusServicoCTResult;
import br.inf.portalfiscal.cte.wsdl.ctestatusservico.svrs.hom.CteStatusServicoSoap12;
import br.inf.portalfiscal.cte.wsdl.ctestatusservico.svrs.hom.ObjectFactory;
import com.fincatto.documentofiscal.DFLog;
import com.fincatto.documentofiscal.DFUnidadeFederativa;
import com.fincatto.documentofiscal.cte300.CTeConfig;
import com.fincatto.documentofiscal.utils.Util;
import com.tartigrado.df.validadores.cte.CTeValidatorFactory;
import com.tartigrado.df.validadores.cte.exception.CTeXSDValidationException;

import java.net.MalformedURLException;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.ws.Holder;

class WSStatusConsulta implements DFLog {

    private static final String VERSAO_LEIAUTE = "3.00";
    private static final String NOME_SERVICO = "STATUS";
    private final CTeConfig config;

    WSStatusConsulta(final CTeConfig config) {
        this.config = config;
    }

    TRetConsStatServ consultaStatus() throws Exception {
        return efetuaConsultaStatus(gerarDadosConsultaStatus(config), config.getCUF());
    }

    private static TConsStatServ gerarDadosConsultaStatus(final CTeConfig config) {
        final TConsStatServ tConsStatServ = new TConsStatServ();
        tConsStatServ.setTpAmb(config.getTipoEmissao().getCodigo());
        tConsStatServ.setVersao(VERSAO_LEIAUTE);
        tConsStatServ.setXServ(NOME_SERVICO);
        return tConsStatServ;
    }

    private TRetConsStatServ efetuaConsultaStatus(TConsStatServ tConsStatServ, DFUnidadeFederativa uf) throws Exception {
        JAXBElement<TConsStatServ> eTConsStatServ = new br.inf.portalfiscal.cte.ObjectFactory().createConsStatServCte(tConsStatServ);
        
        CteCabecMsg cteCabecMsg = new CteCabecMsg();
        cteCabecMsg.setCUF(uf.getCodigoIbge());
        cteCabecMsg.setVersaoDados(VERSAO_LEIAUTE);
        Holder<CteCabecMsg> holder = new Holder<>(new ObjectFactory().createCteCabecMsg(cteCabecMsg).getValue());
                
        CteDadosMsg cteDadosMsg = new CteDadosMsg();
//        cteDadosMsg.getContent().add(cteCabecMsg);
        cteDadosMsg.getContent().add(eTConsStatServ);
        CTeValidatorFactory.padrao().validaTConsStatServ(Util.marshllerCTeConsStatServ(eTConsStatServ));
        CteStatusServicoSoap12 port = new CteStatusServico().getCteStatusServicoSoap12();
        CteStatusServicoCTResult result = port.cteStatusServicoCT(cteDadosMsg);
        TRetConsStatServ tRetConsStatServ = ((JAXBElement<TRetConsStatServ>) result.getContent().get(0)).getValue();
        return tRetConsStatServ;
    }

}
