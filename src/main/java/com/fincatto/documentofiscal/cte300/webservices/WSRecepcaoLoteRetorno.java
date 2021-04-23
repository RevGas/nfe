package com.fincatto.documentofiscal.cte300.webservices;

import br.inf.portalfiscal.cte.TConsReciCTe;
import br.inf.portalfiscal.cte.TRetConsReciCTe;
import br.inf.portalfiscal.cte.TRetEnviCTe;
import br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.hom.CteCabecMsg;
import br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.hom.CteDadosMsg;
import br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.hom.CteRetRecepcao;
import br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.hom.CteRetRecepcaoResult;
import br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.hom.CteRetRecepcaoSoap12;
import br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.hom.ObjectFactory;
import com.fincatto.documentofiscal.DFLog;
import com.fincatto.documentofiscal.S3;
import com.fincatto.documentofiscal.cte300.CTeConfig;
import com.fincatto.documentofiscal.cte300.parsers.CTeParser;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.ws.Holder;
import java.io.IOException;

class WSRecepcaoLoteRetorno implements DFLog {

    private static final String VERSAO = "3.00";
    private final CTeConfig config;

    WSRecepcaoLoteRetorno(final CTeConfig config) {
        this.config = config;
    }

    TRetConsReciCTe consultaLote(final String nRec) throws Exception {
        return efetuaConsulta(gerarDadosConsulta(nRec));
    }

    private TRetConsReciCTe efetuaConsulta(final TConsReciCTe tConsReciCTe) throws JAXBException, IOException {
        CteDadosMsg cteDadosMsg = new CteDadosMsg();
        cteDadosMsg.getContent().add(CTeParser.parserTConsReciCTe(tConsReciCTe));

        CteCabecMsg cteCabecMsg = new CteCabecMsg();
        cteCabecMsg.setCUF(this.config.getCUF().getCodigoIbge());
        cteCabecMsg.setVersaoDados(VERSAO);

        Holder<CteCabecMsg> holder = new Holder<>(new ObjectFactory().createCteCabecMsg(cteCabecMsg).getValue());
        
        CteRetRecepcaoSoap12 port = new CteRetRecepcao().getCteRetRecepcaoSoap12();
        CteRetRecepcaoResult result = port.cteRetRecepcao(cteDadosMsg, holder);

        TRetConsReciCTe tRetEnviCTe = ((JAXBElement<TRetConsReciCTe>) result.getContent().get(0)).getValue();
        sendTRetEnviCTe(tRetEnviCTe);
        return tRetEnviCTe;
    }

    private TConsReciCTe gerarDadosConsulta(final String nRec) {
        TConsReciCTe tConsReciCTe = new TConsReciCTe();
        tConsReciCTe.setNRec(nRec);
        tConsReciCTe.setTpAmb("2");
        tConsReciCTe.setVersao("3.00");
        return tConsReciCTe;
    }

    public static void sendTRetEnviCTe(TRetConsReciCTe retorno) throws JAXBException, IOException {
        new S3().sendTRetConsReciCTe(retorno); //Tentar enviar para o S3
    }

}
