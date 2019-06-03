package com.fincatto.documentofiscal.utils;

/**
 * Classe utilitária
 * @author rafael
 */
public class Util {

    /**
     * Metodo para remover caracteres especiais do xml da nota fiscal
     * @param xml
     * @return 
     */
    public static String convertToASCII2(String xml) {
        return xml.replaceAll("[ãâàáä]", "a")
            .replaceAll("[êèéë]", "e")
            .replaceAll("[îìíï]", "i")
            .replaceAll("[õôòóö]", "o")
            .replaceAll("[ûúùü]", "u")
            .replaceAll("[ÃÂÀÁÄ]", "A")
            .replaceAll("[ÊÈÉË]", "E")
            .replaceAll("[ÎÌÍÏ]", "I")
            .replaceAll("[ÕÔÒÓÖ]", "O")
            .replaceAll("[ÛÙÚÜ]", "U")
            .replace('ç', 'c')
            .replace('Ç', 'C')
            .replace('ñ', 'n')
            .replace('Ñ', 'N');
    }
    
}
