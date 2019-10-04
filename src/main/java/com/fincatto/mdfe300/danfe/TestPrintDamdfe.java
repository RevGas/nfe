package com.fincatto.mdfe300.danfe;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.apache.commons.io.FileUtils;

public class TestPrintDamdfe {

    public static void main(String[] args) throws IOException, Exception {
        File file = new File("/Users/rafael/Desktop/mdfe.xml");
        MDFeDanfeReport danfe = new MDFeDanfeReport(file);
        File file_logo = new File("/Users/rafael/Downloads/REVGAS.png");
        byte[] logo = Files.readAllBytes(file_logo.toPath());
        final byte[] fileByte = danfe.gerarDanfeMDFe(logo, "Emitido pela RevGás - revgas.com");
        File temp = new File("/Users/rafael/Desktop/mdfe.pdf");
        FileUtils.writeByteArrayToFile(temp, fileByte);
    }
    
}
