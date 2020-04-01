package com.fincatto.documentofiscal.nfe400.webservices;

import br.inf.portalfiscal.nfe.*;
import com.fincatto.documentofiscal.*;
import com.fincatto.documentofiscal.nfe.NFTipoEmissao;
import com.fincatto.documentofiscal.nfe.NFeConfig;
import com.fincatto.documentofiscal.nfe400.classes.NFRetornoStatus;
import com.fincatto.documentofiscal.nfe400.classes.lote.envio.NFLoteEnvio;
import com.fincatto.documentofiscal.nfe400.classes.nota.NFNota;
import com.fincatto.documentofiscal.nfe400.classes.nota.NFNotaInfoSuplementar;
import com.fincatto.documentofiscal.nfe400.utils.NFGeraChave;
import com.fincatto.documentofiscal.nfe400.utils.qrcode20.NFGeraQRCode20;
import com.fincatto.documentofiscal.nfe400.utils.qrcode20.NFGeraQRCodeContingenciaOffline20;
import com.fincatto.documentofiscal.nfe400.utils.qrcode20.NFGeraQRCodeEmissaoNormal20;
import com.fincatto.documentofiscal.utils.DFAssinaturaDigital;
import com.fincatto.documentofiscal.utils.Util;
import com.fincatto.documentofiscal.validadores.XMLValidador;
import com.fincatto.nfe310.utils.SOAPHandlerUtil;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.bind.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

class WSLoteEnvio implements DFLog {

    private static final String NFE_ELEMENTO = "NFe";
    private final NFeConfig config;

    WSLoteEnvio(final NFeConfig config) {
        this.config = config;
    }

    TRetEnviNFe enviaLoteAssinado(final String loteAssinadoXml, final DFModelo modelo, final DFAmbiente ambiente) throws Exception {
        return null;
    }

    TRetEnviNFe enviaLote(final NFLoteEnvio lote) throws Exception {
        final NFLoteEnvio loteAssinado = this.getLoteAssinado(lote);
        // comunica o lote
        final TRetEnviNFe loteEnvioRetorno = this.comunicaLote(loteAssinado.toString(), loteAssinado.getNotas().get(0).getInfo().getIdentificacao().getModelo(), loteAssinado.getNotas().get(0).getInfo().getIdentificacao().getAmbiente(), loteAssinado.getNotas().get(0));
        return loteEnvioRetorno;
    }

    TRetEnviNFe enviaLote(final String xml) throws Exception {
        //XML to Object
        TEnviNFe tEnviNFe = GatewayLoteEnvio.getTEnviNFe(xml).getValue();
        // comunica o lote
        final TRetEnviNFe loteEnvioRetorno = this.comunicaLote(xml, DFModelo.valueOfCodigo(tEnviNFe.getNFe().get(0).getInfNFe().getIde().getMod()),
                DFAmbiente.valueOfCodigo(tEnviNFe.getNFe().get(0).getInfNFe().getIde().getTpAmb()), tEnviNFe.getNFe().get(0));

        if(Objects.equals(loteEnvioRetorno.getProtNFe().getInfProt().getCStat(), String.valueOf(NFRetornoStatus.CODIGO_100.getCodigo()))){ // TODO melhorar verificação de status
            uploadProcNFe(tEnviNFe,loteEnvioRetorno);//TODO tentar novamente e redundância
        }

        return loteEnvioRetorno;
    }

    /**
     *
     * Envia nota processada para o S3
     *
     * @param tEnviNFe
     * @param retEnviNFe
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws JAXBException
     * @throws TransformerException
     */
    void uploadProcNFe(TEnviNFe tEnviNFe, TRetEnviNFe retEnviNFe) throws ParserConfigurationException, IOException, SAXException, JAXBException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();

        //Recuperar enviNFe do S3
        File enviNFe = new S3().downloadFile(S3.bucket, S3.getPath(Util.chaveFromTNFe(tEnviNFe.getNFe().get(0)), "enviNFe", tEnviNFe.getNFe().get(0).getInfNFe().getIde().getTpAmb()));

        //Instanciar um ProcNFE para ser manipulado
        TNfeProc tNfeProc = new TNfeProc();
        tNfeProc.setVersao(retEnviNFe.getVersao());

        //Converter enviNFe e retEnviNFe e procNFe em Document
        Document enviNFeDocument = dBuilder.parse(enviNFe);
        Document retEnviNFeDocument = dBuilder.parse(new ByteArrayInputStream(Util.marshllerRetEnviNFe(new ObjectFactory().createRetEnviNFe(retEnviNFe)).getBytes()));
        Document nfeProcDocument = dBuilder.parse(new ByteArrayInputStream(Util.marshlerNfeProc(new ObjectFactory().createNfeProc(tNfeProc)).getBytes()));

        //Extrair e importar Nodes no nfeProc
        Node nfeNode = enviNFeDocument.getElementsByTagName("NFe").item(0);
        Node prot = retEnviNFeDocument.getElementsByTagName("protNFe").item(0);
        nfeNode = nfeProcDocument.importNode(nfeNode, true);
        prot = nfeProcDocument.importNode(prot, true);
        Node nfeProcNode = nfeProcDocument.getFirstChild();
        nfeProcNode.appendChild(nfeNode);
        nfeProcNode.appendChild(prot);

        //Gerar string de procNfe e enviar para o S3
        DOMSource source = new DOMSource(nfeProcNode);
        StringWriter stringResult = new StringWriter();
        TransformerFactory.newInstance().newTransformer().transform(source, new StreamResult(stringResult));

        new S3().sendProcNFe(stringResult.toString());
    }

    /**
     * Retorna o Lote assinado.
     */
    NFLoteEnvio getLoteAssinado(final NFLoteEnvio lote) throws Exception {
        // adiciona a chave e o dv antes de assinar
        for (final NFNota nota : lote.getNotas()) {
            final NFGeraChave geraChave = new NFGeraChave(nota);
            nota.getInfo().getIdentificacao().setCodigoRandomico(StringUtils.defaultIfBlank(nota.getInfo().getIdentificacao().getCodigoRandomico(), geraChave.geraCodigoRandomico()));
            nota.getInfo().getIdentificacao().setDigitoVerificador(geraChave.getDV());
            nota.getInfo().setIdentificador(geraChave.getChaveAcesso());
        }
        //Remover caracteres especiais do xml para o autorizador MT
        String _lote = lote.toString();
        if (lote.getNotas().get(0).getInfo().getEmitente().getEndereco().getUf().equals(DFUnidadeFederativa.MT.getCodigo())) {
            _lote = Util.convertToASCII2(_lote);
        }
        // assina o lote
        final String documentoAssinado = new DFAssinaturaDigital(this.config).assinarDocumento(_lote);
        final NFLoteEnvio loteAssinado = this.config.getPersister().read(NFLoteEnvio.class, documentoAssinado);

        // verifica se nao tem NFCe junto com NFe no lote e gera qrcode (apos assinar mesmo, eh assim)
        int qtdNF = 0, qtdNFC = 0;
        for (final NFNota nota : loteAssinado.getNotas()) {
            switch (nota.getInfo().getIdentificacao().getModelo()) {
                case NFE:
                    qtdNF++;
                    break;
                case NFCE:
                    NFGeraQRCode20 geraQRCode = getNfGeraQRCode20(nota);

                    nota.setInfoSuplementar(new NFNotaInfoSuplementar());
                    nota.getInfoSuplementar().setUrlConsultaChaveAcesso(geraQRCode.urlConsultaChaveAcesso());
                    nota.getInfoSuplementar().setQrCode(geraQRCode.getQRCode());
                    qtdNFC++;
                    break;
                default:
                    throw new IllegalArgumentException(String.format("Modelo de nota desconhecida: %s", nota.getInfo().getIdentificacao().getModelo()));
            }
        }
        // verifica se todas as notas do lote sao do mesmo modelo
        if ((qtdNF > 0) && (qtdNFC > 0)) {
            throw new IllegalArgumentException("Lote contendo notas de modelos diferentes!");
        }
        return loteAssinado;
    }

    private TRetEnviNFe comunicaLote(final String loteAssinadoXml, final DFModelo modelo, final DFAmbiente ambiente, final NFNota nota) throws Exception {
        //valida o lote assinado, para verificar se o xsd foi satisfeito, antes de comunicar com a sefaz

        XMLValidador.validaLote400(loteAssinadoXml);

        return getTRetEnviNFe(modelo, this.config.getCUF(), loteAssinadoXml, ambiente, nota);
    }

    private TRetEnviNFe comunicaLote(final String loteAssinadoXml, final DFModelo modelo, final DFAmbiente ambiente, final TNFe nfe) throws Exception {
        //valida o lote assinado, para verificar se o xsd foi satisfeito, antes de comunicar com a sefaz

        XMLValidador.validaLote400(loteAssinadoXml);

        return getTRetEnviNFe(modelo, this.config.getCUF(), loteAssinadoXml, ambiente, nfe);
    }

    private TRetEnviNFe getTRetEnviNFe(final DFModelo modelo, final DFUnidadeFederativa uf, String loteAssinadoXml, DFAmbiente ambiente, NFNota nota) throws MalformedURLException, JAXBException, Exception {
        return com.fincatto.documentofiscal.nfe400.webservices.GatewayLoteEnvio.valueOfTipoEmissao(nota.getInfo().getIdentificacao().getTipoEmissao(), uf).getTRetEnviNFe(modelo, loteAssinadoXml, ambiente);
    }

    private TRetEnviNFe getTRetEnviNFe(final DFModelo modelo, final DFUnidadeFederativa uf, String loteAssinadoXml, DFAmbiente ambiente, TNFe nfe) throws MalformedURLException, JAXBException, Exception {
        return com.fincatto.documentofiscal.nfe400.webservices.GatewayLoteEnvio.valueOfTipoEmissao(NFTipoEmissao.valueOfCodigo(nfe.getInfNFe().getIde().getTpEmis()), uf).getTRetEnviNFe(modelo, loteAssinadoXml, ambiente);
    }

    private NFGeraQRCode20 getNfGeraQRCode20(NFNota nota) {
        if (NFTipoEmissao.EMISSAO_NORMAL.equals(nota.getInfo().getIdentificacao().getTipoEmissao())) {
            return new NFGeraQRCodeEmissaoNormal20(nota, this.config);
        } else if (NFTipoEmissao.CONTIGENCIA_OFFLINE.equals(nota.getInfo().getIdentificacao().getTipoEmissao())) {
            return new NFGeraQRCodeContingenciaOffline20(nota, this.config);
        } else {
            throw new IllegalArgumentException("QRCode 2.0 Tipo Emissao nao implementado: " + nota.getInfo().getIdentificacao().getTipoEmissao().getDescricao());
        }
    }

}
