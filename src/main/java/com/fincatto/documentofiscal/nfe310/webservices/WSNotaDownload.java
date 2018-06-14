package com.fincatto.documentofiscal.nfe310.webservices;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import org.w3c.dom.Element;

import com.fincatto.documentofiscal.nfe310.classes.NFAutorizador31;
import com.fincatto.documentofiscal.nfe310.classes.evento.downloadnf.NFDownloadNFe;
import com.fincatto.documentofiscal.nfe310.classes.evento.downloadnf.NFDownloadNFeRetorno;
import com.fincatto.nfe310.converters.ElementStringConverter;

import br.inf.portalfiscal.nfe.wsdl.nfedownloadnf.svan.NfeCabecMsg;
import br.inf.portalfiscal.nfe.wsdl.nfedownloadnf.svan.NfeDadosMsg;
import br.inf.portalfiscal.nfe.wsdl.nfedownloadnf.svan.NfeDownloadNF;
import br.inf.portalfiscal.nfe.wsdl.nfedownloadnf.svan.NfeDownloadNFResult;
import br.inf.portalfiscal.nfe.wsdl.nfedownloadnf.svan.NfeDownloadNFSoap;
import com.fincatto.documentofiscal.DFConfig;
import com.fincatto.documentofiscal.transformers.DFRegistryMatcher;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;


class WSNotaDownload {

    private static final BigDecimal VERSAO_LEIAUTE = new BigDecimal("1.00");
    private static final String NOME_SERVICO = "DOWNLOAD NFE";
    private final DFConfig config;

    WSNotaDownload(final DFConfig config) {
        this.config = config;
    }

    NFDownloadNFeRetorno downloadNota(final String cnpj, final String chave) throws Exception {
        return new Persister(new DFRegistryMatcher(), new Format(0)).read(NFDownloadNFeRetorno.class, efetuaDownloadNF(gerarDadosDownloadNF(cnpj, chave).toString()));
    }

    private NFDownloadNFe gerarDadosDownloadNF(final String cnpj, final String chave) throws Exception {
        final NFDownloadNFe nfDownloadNFe = new NFDownloadNFe();
        nfDownloadNFe.setVersao(WSNotaDownload.VERSAO_LEIAUTE.toPlainString());
        nfDownloadNFe.setAmbiente(this.config.getAmbiente());
        nfDownloadNFe.setServico(WSNotaDownload.NOME_SERVICO);
        nfDownloadNFe.setCnpj(cnpj);
        nfDownloadNFe.setChave(chave);
        return nfDownloadNFe;
    }

    private String efetuaDownloadNF(final String xml) throws RemoteException, MalformedURLException {
        final NfeCabecMsg nfeCabecMsg = new NfeCabecMsg();
        nfeCabecMsg.setCUF(this.config.getCUF().getCodigoIbge());
        nfeCabecMsg.setVersaoDados(WSNotaDownload.VERSAO_LEIAUTE.toPlainString());

        final NfeDadosMsg nfeDadosMsg = new NfeDadosMsg();
        nfeDadosMsg.getContent().add(ElementStringConverter.read(xml));

        NFAutorizador31 autorizador = NFAutorizador31.valueOfCodigoUF(this.config.getCUF());
        final String endpoint = autorizador.getNfeDownloadNF(this.config.getAmbiente());
        if (endpoint == null) {
            throw new IllegalArgumentException("Nao foi possivel encontrar URL para DownloadNF, autorizador " + autorizador.name());
        }

        NfeDownloadNFSoap port = new NfeDownloadNF(new URL(endpoint)).getNfeDownloadNFSoap12();
        NfeDownloadNFResult result = port.nfeDownloadNF(nfeDadosMsg, nfeCabecMsg);

        return ElementStringConverter.write((Element) result.getContent().get(0));
    }

}
