package com.fincatto.documentofiscal.cte300.webservices;

import br.inf.portalfiscal.cte.TEnviCTe;
import br.inf.portalfiscal.cte.TRetEnviCTe;
import br.inf.portalfiscal.cte.TUf;
import com.fincatto.documentofiscal.S3;
import com.fincatto.documentofiscal.cte300.CTeConfig;
import com.fincatto.documentofiscal.cte300.parsers.CTeParser;
import com.fincatto.documentofiscal.utils.DFAssinaturaDigital;

import java.io.IOException;
import java.util.Arrays;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.ws.Holder;

public enum GatewayRecepcao {
    
    MG {
        @Override
        public TRetEnviCTe getTRetEnviCTe(TEnviCTe tEnviCTe, CTeConfig config) {
            return getTRetEnviCTeMG(tEnviCTe, config);
        }
        
        @Override
        public TUf[] getUFs() {
            return new TUf[]{
                TUf.MG
            };
        }
    },
    MS {
        @Override
        public TRetEnviCTe getTRetEnviCTe(TEnviCTe tEnviCTe, CTeConfig config) {
            return getTRetEnviCTeMG(tEnviCTe, config);
        }
        
        @Override
        public TUf[] getUFs() {
            return new TUf[]{
                TUf.MS
            };
        }
    },
    MT {
        @Override
        public TRetEnviCTe getTRetEnviCTe(TEnviCTe tEnviCTe, CTeConfig config) {
            return getTRetEnviCTeMT(tEnviCTe, config);
        }
        
        @Override
        public TUf[] getUFs() {
            return new TUf[]{
                TUf.MT
            };
        }
    }, 
    PR {
        @Override
        public TRetEnviCTe getTRetEnviCTe(TEnviCTe tEnviCTe, CTeConfig config) {
            return getTRetEnviCTePR(tEnviCTe, config);
        }
        
        @Override
        public TUf[] getUFs() {
            return new TUf[]{
                TUf.PR
            };
        }
    },
    SVSP {
        @Override
        public TRetEnviCTe getTRetEnviCTe(TEnviCTe tEnviCTe, CTeConfig config) {
            return getTRetEnviCTeSVSP(tEnviCTe, config);
        }

        @Override
        public TUf[] getUFs() {
            return new TUf[]{
                TUf.AP, TUf.PE, TUf.RR, TUf.SP
            };
        }
    },
    SVRS {
        @Override
        public TRetEnviCTe getTRetEnviCTe(TEnviCTe tEnviCTe, CTeConfig config) throws Exception {
            return getTRetEnviCTeSVRS(tEnviCTe, config);
        }

        @Override
        public TUf[] getUFs() {
            return new TUf[]{
                TUf.AC, TUf.AL, TUf.AM, TUf.BA, TUf.CE, TUf.DF,
                TUf.ES, TUf.GO, TUf.MA, TUf.PA, TUf.PB, TUf.PI,
                TUf.RJ, TUf.RN, TUf.RO, TUf.RS, TUf.SC, TUf.TO
            };
        }
    };

    public abstract TRetEnviCTe getTRetEnviCTe(TEnviCTe tEnviCTe, CTeConfig config) throws JAXBException, Exception;

    public abstract TUf[] getUFs();

    public static GatewayRecepcao valueOfCodigoUF(final TUf uf) {
        for (final GatewayRecepcao autorizador : GatewayRecepcao.values()) {
            if (Arrays.asList(autorizador.getUFs()).contains(uf)) {
                return autorizador;
            }
        }
        throw new IllegalStateException(String.format("N\u00e3o existe metodo de envio para a UF %s", uf.value()));
    }
    
    public TRetEnviCTe getTRetEnviCTeMG(TEnviCTe tEnviCTe, CTeConfig config) {
       return null;
    }
    
    public TRetEnviCTe getTRetEnviCTeMT(TEnviCTe tEnviCTe, CTeConfig config) {
       return null;
    }
    
    public TRetEnviCTe getTRetEnviCTePR(TEnviCTe tEnviCTe, CTeConfig config) {
       return null;
    }
    
    public TRetEnviCTe getTRetEnviCTeSVRS(TEnviCTe tEnviCTe, CTeConfig config) throws Exception {
        if (tEnviCTe.getCTe().get(0).getInfCte().getIde().getTpAmb().equals("1")) { // Produção
            br.inf.portalfiscal.cte.wsdl.cterecepcao.svrs.CteDadosMsg cteDadosMsg = new br.inf.portalfiscal.cte.wsdl.cterecepcao.svrs.CteDadosMsg();
            cteDadosMsg.getContent().add(CTeParser.parserTEnviCTe(getDocumentoAssinado(tEnviCTe, config)));

            br.inf.portalfiscal.cte.wsdl.cterecepcao.svrs.CteCabecMsg cteCabecMsg = new br.inf.portalfiscal.cte.wsdl.cterecepcao.svrs.CteCabecMsg();
            cteCabecMsg.setCUF(config.getCUF().getCodigoIbge());
            cteCabecMsg.setVersaoDados("3.00");

            Holder<br.inf.portalfiscal.cte.wsdl.cterecepcao.svrs.CteCabecMsg> holder = new Holder<>(new br.inf.portalfiscal.cte.wsdl.cterecepcao.svrs.ObjectFactory().createCteCabecMsg(cteCabecMsg).getValue());

            br.inf.portalfiscal.cte.wsdl.cterecepcao.svrs.CteRecepcaoSoap12 port = new br.inf.portalfiscal.cte.wsdl.cterecepcao.svrs.CteRecepcao().getCteRecepcaoSoap12();
            br.inf.portalfiscal.cte.wsdl.cterecepcao.svrs.CteRecepcaoLoteResult result = port.cteRecepcaoLote(cteDadosMsg, holder);

            TRetEnviCTe tRetEnviCTe = ((JAXBElement<TRetEnviCTe>) result.getContent().get(0)).getValue();
            sendTRetEnviCTe(tRetEnviCTe, tEnviCTe);
            return tRetEnviCTe;
        } else { // Homologação
            br.inf.portalfiscal.cte.wsdl.cterecepcao.svrs.hom.CteDadosMsg cteDadosMsg = new br.inf.portalfiscal.cte.wsdl.cterecepcao.svrs.hom.CteDadosMsg();
            cteDadosMsg.getContent().add(CTeParser.parserTEnviCTe(getDocumentoAssinado(tEnviCTe, config)));

            br.inf.portalfiscal.cte.wsdl.cterecepcao.svrs.hom.CteCabecMsg cteCabecMsg = new br.inf.portalfiscal.cte.wsdl.cterecepcao.svrs.hom.CteCabecMsg();
            cteCabecMsg.setCUF(config.getCUF().getCodigoIbge());
            cteCabecMsg.setVersaoDados("3.00");

            Holder<br.inf.portalfiscal.cte.wsdl.cterecepcao.svrs.hom.CteCabecMsg> holder = new Holder<>(new br.inf.portalfiscal.cte.wsdl.cterecepcao.svrs.hom.ObjectFactory().createCteCabecMsg(cteCabecMsg).getValue());

            br.inf.portalfiscal.cte.wsdl.cterecepcao.svrs.hom.CteRecepcaoSoap12 port = new br.inf.portalfiscal.cte.wsdl.cterecepcao.svrs.hom.CteRecepcao().getCteRecepcaoSoap12();
            br.inf.portalfiscal.cte.wsdl.cterecepcao.svrs.hom.CteRecepcaoLoteResult result = port.cteRecepcaoLote(cteDadosMsg, holder);

            TRetEnviCTe tRetEnviCTe = ((JAXBElement<TRetEnviCTe>) result.getContent().get(0)).getValue();
            sendTRetEnviCTe(tRetEnviCTe, tEnviCTe);
            return tRetEnviCTe;
        }
    }
    
    public TRetEnviCTe getTRetEnviCTeSVSP(TEnviCTe tEnviCTe, CTeConfig config) {
       return null;
    }
    
    private String getDocumentoAssinado(TEnviCTe tEnviCTe, CTeConfig config) throws JAXBException, Exception {
        return new DFAssinaturaDigital(config).assinarDocumento(CTeParser.parserTEnviCTe(tEnviCTe).replace(" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", ""), "infCte");
    }

    public static void sendTRetEnviCTe(TRetEnviCTe retorno, TEnviCTe tEnviCTe) throws JAXBException, IOException {
        new S3().sendTRetEnviCTe(retorno, tEnviCTe); //Tentar enviar para o S3
    }

}