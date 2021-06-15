package com.fincatto.documentofiscal.cte300.webservices;

import br.inf.portalfiscal.cte.TEvento;
import br.inf.portalfiscal.cte.TRetEvento;
import br.inf.portalfiscal.cte.TUf;
import br.inf.portalfiscal.cte.wsdl.cterecepcaoevento.svrs.hom.ObjectFactory;
import com.fincatto.documentofiscal.cte300.CTeConfig;
import java.util.Arrays;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.ws.Holder;

public enum GatewayEvento {
    
    MG {
        @Override
        public TRetEvento getTRetEvento(JAXBElement<TEvento> eTEvento, CTeConfig config) {
            return getTRetEventoMG(eTEvento, config);
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
        public TRetEvento getTRetEvento(JAXBElement<TEvento> eTEvento, CTeConfig config) {
            return getTRetEventoMG(eTEvento, config);
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
        public TRetEvento getTRetEvento(JAXBElement<TEvento> eTEvento, CTeConfig config) {
            return getTRetEventoMT(eTEvento, config);
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
        public TRetEvento getTRetEvento(JAXBElement<TEvento> eTEvento, CTeConfig config) {
            return getTRetEventoPR(eTEvento, config);
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
        public TRetEvento getTRetEvento(JAXBElement<TEvento> eTEvento, CTeConfig config) {
            return getTRetEventoSVSP(eTEvento, config);
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
        public TRetEvento getTRetEvento(JAXBElement<TEvento> eTEvento, CTeConfig config) throws Exception {
            return getTRetEventoSVRS(eTEvento, config);
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

    public abstract TRetEvento getTRetEvento(JAXBElement<TEvento> eTEvento, CTeConfig config) throws JAXBException, Exception;

    public abstract TUf[] getUFs();

    public static GatewayEvento valueOfCodigoUF(final TUf uf) {
        for (final GatewayEvento autorizador : GatewayEvento.values()) {
            if (Arrays.asList(autorizador.getUFs()).contains(uf)) {
                return autorizador;
            }
        }
        throw new IllegalStateException(String.format("N\u00e3o existe metodo de envio para a UF %s", uf.value()));
    }
    
    public TRetEvento getTRetEventoMG(JAXBElement<TEvento> eTEvento, CTeConfig config) {
       return null;
    }
    
    public TRetEvento getTRetEventoMT(JAXBElement<TEvento> eTEvento, CTeConfig config) {
       return null;
    }
    
    public TRetEvento getTRetEventoPR(JAXBElement<TEvento> eTEvento, CTeConfig config) {
       return null;
    }
    
    public TRetEvento getTRetEventoSVRS(JAXBElement<TEvento> eTEvento, CTeConfig config) throws Exception {
        if (eTEvento.getValue().getInfEvento().getTpAmb().equals("1")) { // Produção
            br.inf.portalfiscal.cte.wsdl.cterecepcaoevento.svrs.CteDadosMsg cteDadosMsg = new br.inf.portalfiscal.cte.wsdl.cterecepcaoevento.svrs.CteDadosMsg();
            cteDadosMsg.getContent().add(eTEvento);

//            br.inf.portalfiscal.cte.wsdl.cterecepcaoevento.svrs.CteCabecMsg cteCabecMsg = new br.inf.portalfiscal.cte.wsdl.cterecepcaoevento.svrs.CteCabecMsg();
//            cteCabecMsg.setCUF(config.getCUF().getCodigoIbge());
//            cteCabecMsg.setVersaoDados("3.00");
//
//            Holder<br.inf.portalfiscal.cte.wsdl.cterecepcaoevento.svrs.CteCabecMsg> holder = new Holder<>(new br.inf.portalfiscal.cte.wsdl.cterecepcaoevento.svrs.ObjectFactory().createCteCabecMsg(cteCabecMsg).getValue());

            br.inf.portalfiscal.cte.wsdl.cterecepcaoevento.svrs.CteRecepcaoEventoSoap12 port = new br.inf.portalfiscal.cte.wsdl.cterecepcaoevento.svrs.CteRecepcaoEvento().getCteRecepcaoEventoSoap12();
            br.inf.portalfiscal.cte.wsdl.cterecepcaoevento.svrs.CteRecepcaoEventoResult result = port.cteRecepcaoEvento(cteDadosMsg);

            TRetEvento tProcEvento = ((JAXBElement<TRetEvento>) result.getContent().get(0)).getValue();
            return tProcEvento;
        } else { // Homologação
            br.inf.portalfiscal.cte.wsdl.cterecepcaoevento.svrs.hom.CteDadosMsg cteDadosMsg = new br.inf.portalfiscal.cte.wsdl.cterecepcaoevento.svrs.hom.CteDadosMsg();
            cteDadosMsg.getContent().add(eTEvento);

            br.inf.portalfiscal.cte.wsdl.cterecepcaoevento.svrs.hom.CteCabecMsg cteCabecMsg = new br.inf.portalfiscal.cte.wsdl.cterecepcaoevento.svrs.hom.CteCabecMsg();
            cteCabecMsg.setCUF(config.getCUF().getCodigoIbge());
            cteCabecMsg.setVersaoDados("3.00");

            Holder<br.inf.portalfiscal.cte.wsdl.cterecepcaoevento.svrs.hom.CteCabecMsg> holder = new Holder<>(new br.inf.portalfiscal.cte.wsdl.cterecepcaoevento.svrs.hom.ObjectFactory().createCteCabecMsg(cteCabecMsg).getValue());

            br.inf.portalfiscal.cte.wsdl.cterecepcaoevento.svrs.hom.CteRecepcaoEventoSoap12 port = new br.inf.portalfiscal.cte.wsdl.cterecepcaoevento.svrs.hom.CteRecepcaoEvento().getCteRecepcaoEventoSoap12();
            br.inf.portalfiscal.cte.wsdl.cterecepcaoevento.svrs.hom.CteRecepcaoEventoResult result = port.cteRecepcaoEvento(cteDadosMsg, holder);

            TRetEvento tProcEvento = ((JAXBElement<TRetEvento>) result.getContent().get(0)).getValue();
            return tProcEvento;
        }
    }
    
    public TRetEvento getTRetEventoSVSP(JAXBElement<TEvento> eTEvento, CTeConfig config) {
       return null;
    }

}
