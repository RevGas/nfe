package com.fincatto.nfe310.danfe;

import com.fincatto.documentofiscal.nfe310.FabricaDeObjetosFake;
import com.fincatto.documentofiscal.nfe310.NFeConfig;
import com.fincatto.documentofiscal.DFAmbiente;
import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.nfe310.classes.NFProtocolo;
import com.fincatto.documentofiscal.DFUnidadeFederativa;
import com.fincatto.documentofiscal.nfe310.classes.nota.NFNota;
import com.fincatto.documentofiscal.nfe310.classes.nota.NFNotaInfoSuplementar;
import com.fincatto.documentofiscal.nfe310.classes.nota.NFNotaProcessada;
import com.fincatto.documentofiscal.nfe310.classes.nota.assinatura.NFSignature;
import com.fincatto.documentofiscal.nfe310.utils.NFGeraQRCode;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.security.KeyStore;
import java.security.KeyStoreException;

public class NFDanfeReportTest {

    @Test
    public void deveGerarDanfeNFeAPartirDoXML() throws Exception {
        final NFProtocolo nfProtocolo = new NFProtocolo();
        nfProtocolo.setProtocoloInfo(FabricaDeObjetosFake.getNFProtocoloInfo());
        nfProtocolo.setVersao("3.10");

        final NFSignature signature = new NFSignature();
        signature.setSignatureValue("NFe89172658591754401086218048846976493475937081");

        final NFNota nota = new NFNota();
        nota.setInfo(FabricaDeObjetosFake.getNFNotaInfo());
        nota.setIdentificadorLocal(123456);
        nota.setAssinatura(signature);

        final NFNotaProcessada notaProcessada = new NFNotaProcessada();
        notaProcessada.setVersao(BigDecimal.TEN);
        notaProcessada.setProtocolo(nfProtocolo);
        notaProcessada.setNota(nota);

        NFDanfeReport danfe = new NFDanfeReport(notaProcessada);
        final byte[] fileByte = danfe.gerarDanfeNFe(null);
        Assert.assertTrue(fileByte.length > 0);
    }

    @Test
    public void deveGerarDanfeNFCeAPartirDoXML() throws Exception {
        final NFNotaProcessada notaProcessada = FabricaDeObjetosFake.getNFNotaProcessada();
        notaProcessada.setNota(FabricaDeObjetosFake.getNotaQRCode());
        notaProcessada.getNota().getInfo().getIdentificacao().setModelo(DFModelo.NFCE);
        notaProcessada.getNota().setInfoSuplementar(new NFNotaInfoSuplementar());
        notaProcessada.getNota().getInfoSuplementar().setQrCode(new NFGeraQRCode(notaProcessada.getNota(), createConfigTest()).getQRCode());
        notaProcessada.getNota().getInfo().getIdentificacao().setAmbiente(DFAmbiente.HOMOLOGACAO);

        NFDanfeReport danfe = new NFDanfeReport(notaProcessada);
        final byte[] fileByte = danfe.gerarDanfeNFCe(null, false);
        Assert.assertTrue(fileByte.length > 0);
    }

    @Test(expected = IllegalStateException.class)
    public void naoDeveGerarDanfeNFeDeUmaNFCe() throws Exception {
        final NFNotaProcessada notaProcessada = FabricaDeObjetosFake.getNFNotaProcessada();
        notaProcessada.getNota().getInfo().getIdentificacao().setModelo(DFModelo.NFCE);

        NFDanfeReport danfe = new NFDanfeReport(notaProcessada);
        danfe.gerarDanfeNFe(null);
    }

    @Test(expected = IllegalStateException.class)
    public void naoDeveGerarDanfeNFCeDeUmaNFe() throws Exception {
        final NFNotaProcessada notaProcessada = FabricaDeObjetosFake.getNFNotaProcessada();
        notaProcessada.getNota().getInfo().getIdentificacao().setModelo(DFModelo.NFE);

        NFDanfeReport danfe = new NFDanfeReport(notaProcessada);
        danfe.gerarDanfeNFCe(null, false);
    }

    private NFeConfig createConfigTest() {
        return new NFeConfig() {
            @Override
            public Integer getCodigoSegurancaContribuinteID() {
                return 1;
            }

            @Override
            public String getCodigoSegurancaContribuinte() {
                return "SEU-CODIGO-CSC-CONTRIBUINTE-36-CARACTERES";
            }

            @Override
            public DFUnidadeFederativa getCUF() {
                return DFUnidadeFederativa.SE;
            }

            @Override
            public KeyStore getCertificadoKeyStore() throws KeyStoreException {
                return null;
            }

            @Override
            public String getCertificadoSenha() {
                return null;
            }

            @Override
            public KeyStore getCadeiaCertificadosKeyStore() throws KeyStoreException {
                return null;
            }

            @Override
            public String getCadeiaCertificadosSenha() {
                return null;
            }

            @Override
            public DFAmbiente getAmbiente() {
                return DFAmbiente.HOMOLOGACAO;
            }
        };
    }
}
