package com.fincatto.documentofiscal.cte300.dacte;

import com.fincatto.documentofiscal.cte300.classes.nota.CTeProcessado;
import com.fincatto.documentofiscal.persister.DFPersister;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;


public class CTeDacteReport {
    private final CTeProcessado cte;

    public CTeDacteReport(File xml) throws Exception {
        this(new DFPersister().read(CTeProcessado.class, FileUtils.readFileToString(xml, StandardCharsets.UTF_8)));
    }

    public CTeDacteReport(String xml) throws Exception {
        this(new DFPersister().read(CTeProcessado.class, xml));
    }

    public CTeDacteReport(CTeProcessado cTeProcessado) {
        this.cte = cTeProcessado;
    }

    public byte[] gerarDacteCTe() throws Exception {
        return toPDF(createJasperPrintCTe());
    }

    private static byte[] toPDF(JasperPrint print) throws JRException {
        return JasperExportManager.exportReportToPdf(print);
    }

    public JasperPrint createJasperPrintCTe() throws Exception {
        List<String> notas = new ArrayList<>();
        this.cte.getCte().getCteNotaInfo().getCteNormal().getInfoDocumentos().getInfoNFe().forEach(nota -> {
            notas.add(nota.getChave());
        });
        int size = notas.size();
        if (size == 0) throw new RuntimeException("NÃO É POSSÍVEL GERAR UM CTE SEM NOTAS FISCAIS");

        List<String> notas1 = new ArrayList<>(notas.subList(0, (size + 1) / 2));
        List<String> notas2 = new ArrayList<>(notas.subList((size + 1) / 2, size));

        while (notas1.size() < notas2.size()) notas1.add("");
        while (notas2.size() < notas1.size()) notas2.add("");

        try (InputStream in = com.fincatto.documentofiscal.cte300.dacte.CTeDacteReport.class.getClassLoader().getResourceAsStream("danfe/DACTE.jasper")) {
            final JRPropertiesUtil jrPropertiesUtil = JRPropertiesUtil.getInstance(DefaultJasperReportsContext.getInstance());
            jrPropertiesUtil.setProperty("net.sf.jasperreports.xpath.executer.factory", "net.sf.jasperreports.engine.util.xml.JaxenXPathExecuterFactory");
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("NOTAS_FISCAIS_T1", notas1);
            parameters.put("NOTAS_FISCAIS_T2", notas2);

            return JasperFillManager.fillReport(in, parameters,  new JRXmlDataSource(convertStringXMl2DOM(), "/cteProc"));
        }
    }

    private Document convertStringXMl2DOM() throws ParserConfigurationException, IOException, SAXException {
        try (StringReader stringReader = new StringReader(cte.toString())) {
            InputSource inputSource = new InputSource();
            inputSource.setCharacterStream(stringReader);
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource);
        }
    }
}
