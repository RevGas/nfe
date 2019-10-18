package com.fincatto.documentofiscal.cte300.webservices;

import br.inf.portalfiscal.cte.TEnviCTe;
import br.inf.portalfiscal.cte.TRetEnviCTe;
import br.inf.portalfiscal.cte.wsdl.cterecepcao.svrs.hom.CteCabecMsg;
import br.inf.portalfiscal.cte.wsdl.cterecepcao.svrs.hom.CteDadosMsg;
import br.inf.portalfiscal.cte.wsdl.cterecepcao.svrs.hom.ObjectFactory;
import br.inf.portalfiscal.cte.wsdl.cterecepcaosinc.svrs.hom.CteRecepcaoSinc;
import br.inf.portalfiscal.cte.wsdl.cterecepcaosinc.svrs.hom.CteRecepcaoSincSoap12;
import com.fincatto.documentofiscal.DFLog;
import com.fincatto.documentofiscal.cte300.CTeConfig;
import com.fincatto.documentofiscal.cte300.parsers.CTChaveParser;
import com.fincatto.documentofiscal.cte300.parsers.CTeParser;
import com.fincatto.documentofiscal.cte300.utils.CTeGeraChave;
import com.fincatto.documentofiscal.utils.DFAssinaturaDigital;
import javax.xml.bind.JAXBException;
import javax.xml.ws.Holder;

class WSRecepcaoLote implements DFLog {

    private static final String VERSAO = "3.00";
    private final CTeConfig config;

    WSRecepcaoLote(final CTeConfig config) {
        this.config = config;
    }

    public TRetEnviCTe envioRecepcao(TEnviCTe tEnviCTe) throws Exception {
        CTeGeraChave gerarChave = new CTeGeraChave(tEnviCTe);
        tEnviCTe.getCTe().get(0).getInfCte().getIde().setCCT(gerarChave.geraCodigoRandomico());
        tEnviCTe.getCTe().get(0).getInfCte().setId("CTe"+ gerarChave.getChaveAcesso());
        tEnviCTe.getCTe().get(0).getInfCTeSupl().setQrCodCTe("https://dfe-portal.svrs.rs.gov.br/cte/qrCode?chCTe="+
                tEnviCTe.getCTe().get(0).getInfCte().getId().replace("CTe", "")+
                "&tpAmb="+
                tEnviCTe.getCTe().get(0).getInfCte().getIde().getTpAmb());
        tEnviCTe.getCTe().get(0).getInfCte().getIde().setCDV(gerarChave.getDV().toString());

        CteDadosMsg cteDadosMsg = new CteDadosMsg();
        cteDadosMsg.getContent().add(CTeParser.parserTEnviCTe(getDocumentoAssinado(tEnviCTe)));
        
        return GatewayRecepcao.valueOfCodigoUF(new CTChaveParser().getTUf(tEnviCTe.getCTe().get(0).getInfCte().getIde().getCUF())).getTRetEnviCTe(tEnviCTe, this.config);
    }
    
    public TRetEnviCTe envioRecepcaoSinc(TEnviCTe tEnviCTe) throws Exception {
        CteCabecMsg cteCabecMsg = new CteCabecMsg();
        cteCabecMsg.setCUF(this.config.getCUF().getCodigoIbge());
        cteCabecMsg.setVersaoDados(VERSAO);

        Holder<CteCabecMsg> holder = new Holder<>(new ObjectFactory().createCteCabecMsg(cteCabecMsg).getValue());
        
        CteRecepcaoSincSoap12 port = new CteRecepcaoSinc().getCteRecepcaoSincSoap12();
//        CteRecepcaoSincResult result = port.cteRecepcaoSinc(compress(getDocumentoAssinado(tEnviCTe)));

//        TRetEnviCTe tRetEnviCTe = ((JAXBElement<TRetEnviCTe>) result.getContent().get(0)).getValue();
//        return tRetEnviCTe;
        return null;
    }
    
    private String getDocumentoAssinado(TEnviCTe tEnviCTe) throws JAXBException, Exception {
        return new DFAssinaturaDigital(this.config).assinarDocumento(CTeParser.parserTEnviCTe(tEnviCTe).replace(" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", ""), "infCte");
    }
    
//    public static String compress(String str) throws Exception {
//        if (str == null || str.length() == 0) {
//            return str;
//        }
//        ByteArrayOutputStream obj=new ByteArrayOutputStream();
//        GZIPOutputStream gzip = new GZIPOutputStream(obj);
//        gzip.write(str.getBytes("UTF-8"));
//        gzip.close();
//        String outStr = Base64.encode(obj.toByteArray());
//        return outStr;
//     }
    
}
