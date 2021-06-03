package com.fincatto.mdfe300.danfe;

import com.fincatto.mdfe300.classes.MDFParser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBException;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import org.apache.commons.io.FileUtils;

public class MDFeDanfeReport {

    private final File xml;

    public MDFeDanfeReport(File xml) {
        this.xml = xml;
    }

    public byte[] gerarDanfeMDFe(byte[] logoEmpresa, String rodape) throws Exception {
        return toPDF(createJasperPrintMDFe(logoEmpresa, rodape));
    }

    private static byte[] toPDF(JasperPrint print) throws JRException {
    	return JasperExportManager.exportReportToPdf(print);
    }

    public JasperPrint createJasperPrintMDFe(byte[] logoEmpresa, String rodape) throws IOException, JRException, WriterException, JAXBException {
    	try (InputStream in = MDFeDanfeReport.class.getClassLoader().getResourceAsStream("danfe/DANFE_MDFE_RODO_RETRATO2.jasper");) {
            final JRPropertiesUtil jrPropertiesUtil = JRPropertiesUtil.getInstance(DefaultJasperReportsContext.getInstance());
            jrPropertiesUtil.setProperty("net.sf.jasperreports.xpath.executer.factory", "net.sf.jasperreports.engine.util.xml.JaxenXPathExecuterFactory");
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("LOGO_EMPRESA", (logoEmpresa == null ? null : new ByteArrayInputStream(logoEmpresa)));
            parameters.put("RODAPE", rodape);
            parameters.put("QR_CODE", gerarQRCode());
            return JasperFillManager.fillReport(in, parameters, new JRXmlDataSource(this.xml, "/mdfeProc"));
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

}
