package com.fincatto.documentofiscal.nfe.webservices.distribuicao;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.zip.GZIPInputStream;


import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;

import com.fincatto.documentofiscal.nfe.classes.distribuicao.NFDistribuicaoInt;
import com.fincatto.documentofiscal.nfe310.NFeConfig;
import com.fincatto.documentofiscal.transformers.DFRegistryMatcher;

public class WSDistribuicaoDFe {

    /**
     * Metodo para consultar os conhecimentos de transporte e retorna uma String<br>
     * É importante salvar esta String para não perder nenhuma informacao<br>
     * A receita não disponibiliza o conhecimento várias vezes para consultar, retorna rejeicao: Consumo indevido
     */
    public static String consultar(final NFDistribuicaoInt distDFeInt, final NFeConfig config) throws Exception {
       throw new UnsupportedOperationException("Nao suportado ainda");
    }

    public static String decodeGZipToXml(final String conteudoEncode) throws Exception {
        if (conteudoEncode == null || conteudoEncode.length() == 0) {
            return "";
        }
        final byte[] conteudo = Base64.getDecoder().decode(conteudoEncode);
        try (GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(conteudo))) {
            try (BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "UTF-8"))) {
                String outStr = "";
                String line;
                while ((line = bf.readLine()) != null) {
                    outStr += line;
                }
                return outStr;
            }
        }
    }

    public static <T> T xmlToObject(final String xml, final Class<T> classe) throws Exception {
        return new Persister(new DFRegistryMatcher(), new Format(0)).read(classe, xml);
    }

}