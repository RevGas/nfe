package com.fincatto.documentofiscal.cte300.dacte;

import com.fincatto.mdfe300.classes.MDFParser;
import com.fincatto.mdfe300.danfe.MDFeDanfeReport;
import com.fincatto.nfe310.danfe.NFDanfeReport;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import org.apache.commons.io.FileUtils;

import javax.xml.bind.JAXBException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;


public class CTeDacteReport {
    private final File xml;

    public CTeDacteReport(File xml) {
        this.xml = xml;
    }

    public byte[] gerarDacteCTe() throws Exception {
        return toPDF(createJasperPrintCTe());
    }

    private static byte[] toPDF(JasperPrint print) throws JRException {
        return JasperExportManager.exportReportToPdf(print);
    }

    public JasperPrint createJasperPrintCTe() throws IOException, JRException, WriterException, JAXBException {
        try (InputStream in = com.fincatto.documentofiscal.cte300.dacte.CTeDacteReport.class.getClassLoader().getResourceAsStream("danfe/DACTE.jasper");
             InputStream subDocumentosObrigatorios = com.fincatto.documentofiscal.cte300.dacte.CTeDacteReport.class.getClassLoader().getResourceAsStream("danfe/DACTE_DOCUMENTOS_OBRIGATORIOS.jasper");) {
            final JRPropertiesUtil jrPropertiesUtil = JRPropertiesUtil.getInstance(DefaultJasperReportsContext.getInstance());
            jrPropertiesUtil.setProperty("net.sf.jasperreports.xpath.executer.factory", "net.sf.jasperreports.engine.util.xml.JaxenXPathExecuterFactory");
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("SUBREPORT_DOCUMENTOS_OBRIGATORIOS", subDocumentosObrigatorios);
            return JasperFillManager.fillReport(in, parameters, new JRXmlDataSource(this.xml, "/cteProc"));
        }
    }

    /**
     * Geracao do QRCode com ZXing
     * http://repo1.maven.org/maven2/com/google/zxing/core/3.2.0/
     * @return
     * @throws com.google.zxing.WriterException
     * @throws java.io.IOException
     * @throws javax.xml.bind.JAXBException
     */
    public BufferedImage gerarQRCode() throws WriterException, IOException, JAXBException {
        int size = 250;
        Map<EncodeHintType, Object> hintMap = new EnumMap<>(EncodeHintType.class);
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        String _xml = FileUtils.readFileToString(xml, StandardCharsets.UTF_8);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(MDFParser.parser(_xml).getMDFe().getInfMDFeSupl().getQrCodMDFe(),
                BarcodeFormat.QR_CODE, size, size, hintMap);
        int crunchifyWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(crunchifyWidth, crunchifyWidth, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, crunchifyWidth, crunchifyWidth);
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < crunchifyWidth; i++) {
            for (int j = 0; j < crunchifyWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        return image;
    }

    public static void main(String[] args) throws Exception {
        File xml = new File("/home/revgas/cte2.xml");
        CTeDacteReport dacte = new CTeDacteReport(xml);
        File file_logo = new File("/Users/rafael/Downloads/REVGAS.png");
        final byte[] fileByte = dacte.gerarDacteCTe();
        File temp = new File("/home/revgas/Documents/dacte/DACTE.pdf");
        FileUtils.writeByteArrayToFile(temp, fileByte);
    }
}
