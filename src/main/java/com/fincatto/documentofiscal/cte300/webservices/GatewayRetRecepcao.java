package com.fincatto.documentofiscal.cte300.webservices;

import br.inf.portalfiscal.cte.*;
import br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.hom.*;
import br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.hom.ObjectFactory;
import com.fincatto.documentofiscal.DFAmbiente;
import com.fincatto.documentofiscal.cte300.CTeConfig;
import com.fincatto.documentofiscal.cte300.parsers.CTeParser;
import com.fincatto.documentofiscal.utils.Util;
import com.tartigrado.df.validadores.cte.CTeValidatorFactory;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.ws.Holder;
import java.util.Arrays;

public enum GatewayRetRecepcao {

    MS {
        @Override
        public TRetConsReciCTe getTRetConsReciCTe(JAXBElement<TConsReciCTe> tConsReciCTe, CTeConfig config) throws JAXBException, Exception {
            return getTRetConsReciCTeMS(tConsReciCTe, config);
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
        public TRetConsReciCTe getTRetConsReciCTe(JAXBElement<TConsReciCTe> tConsReciCTe, CTeConfig config) throws JAXBException, Exception {
            return getTRetConsReciCTeMT(tConsReciCTe, config);
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
        public TRetConsReciCTe getTRetConsReciCTe(JAXBElement<TConsReciCTe> tConsReciCTe, CTeConfig config) throws JAXBException, Exception {
            return getTRetConsReciCTePR(tConsReciCTe, config);
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
        public TRetConsReciCTe getTRetConsReciCTe(JAXBElement<TConsReciCTe> tConsReciCTe, CTeConfig config) throws JAXBException, Exception {
            return getTRetConsReciCTeSVSP(tConsReciCTe, config);
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
        public TRetConsReciCTe getTRetConsReciCTe(JAXBElement<TConsReciCTe> tConsReciCTe, CTeConfig config) throws Exception {
            return getTRetConsReciCTeSVRS(tConsReciCTe, config);
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

    public abstract TRetConsReciCTe getTRetConsReciCTe(JAXBElement<TConsReciCTe> tConsReciCTe, CTeConfig config) throws JAXBException, Exception;

    public abstract TUf[] getUFs();

    public static GatewayRetRecepcao valueOfCodigoUF(final TUf uf) {
        for (final GatewayRetRecepcao autorizador : GatewayRetRecepcao.values()) {
            if (Arrays.asList(autorizador.getUFs()).contains(uf)) {
                return autorizador;
            }
        }
        throw new IllegalStateException(String.format("N\u00e3o existe metodo de envio para a UF %s", uf.value()));
    }

    protected TRetConsReciCTe getTRetConsReciCTeSVRS(JAXBElement<TConsReciCTe> tConsReciCTe, CTeConfig config) {
        if (config.getAmbiente().equals(DFAmbiente.PRODUCAO)) {
            br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.CteDadosMsg cteDadosMsg = new br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.CteDadosMsg();
            cteDadosMsg.getContent().add(tConsReciCTe);

            br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.CteCabecMsg cteCabecMsg = new br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.CteCabecMsg();
            cteCabecMsg.setCUF(config.getCUF().getCodigoIbge());
            cteCabecMsg.setVersaoDados("3.00");

            Holder<br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.CteCabecMsg> holder = new Holder<>(new br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.ObjectFactory().createCteCabecMsg(cteCabecMsg).getValue());

            br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.CteRetRecepcaoSoap12 port = new br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.CteRetRecepcao().getCteRetRecepcaoSoap12();
            br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.CteRetRecepcaoResult result = port.cteRetRecepcao(cteDadosMsg, holder);

            return ((JAXBElement<TRetConsReciCTe>) result.getContent().get(0)).getValue();

        } else {
            br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.hom.CteDadosMsg cteDadosMsg = new br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.hom.CteDadosMsg();
            cteDadosMsg.getContent().add(tConsReciCTe);

            br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.hom.CteCabecMsg cteCabecMsg = new br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.hom.CteCabecMsg();
            cteCabecMsg.setCUF(config.getCUF().getCodigoIbge());
            cteCabecMsg.setVersaoDados("3.00");

            Holder<br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.hom.CteCabecMsg> holder = new Holder<>(new br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.hom.ObjectFactory().createCteCabecMsg(cteCabecMsg).getValue());

            br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.hom.CteRetRecepcaoSoap12 port = new br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.hom.CteRetRecepcao().getCteRetRecepcaoSoap12();
            br.inf.portalfiscal.cte.wsdl.cteretrecepcao.svrs.hom.CteRetRecepcaoResult result = port.cteRetRecepcao(cteDadosMsg, holder);

            return ((JAXBElement<TRetConsReciCTe>) result.getContent().get(0)).getValue();
        }
    }

    protected TRetConsReciCTe getTRetConsReciCTeMS(JAXBElement<TConsReciCTe> tConsReciCTe, CTeConfig config) {
        return null;
    }

    protected TRetConsReciCTe getTRetConsReciCTeMT(JAXBElement<TConsReciCTe> tConsReciCTe, CTeConfig config) {
        return null;
    }

    protected TRetConsReciCTe getTRetConsReciCTePR(JAXBElement<TConsReciCTe> tConsReciCTe, CTeConfig config) {
        return null;
    }

    protected TRetConsReciCTe getTRetConsReciCTeSVSP(JAXBElement<TConsReciCTe> tConsReciCTe, CTeConfig config) {
        return null;
    }

}
