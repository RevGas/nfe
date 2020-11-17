package com.fincatto.dfe.utils;

import com.fincatto.documentofiscal.nfe.XSDFields;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;

public class AdapterXSDError {
    public static String getErrorMessage(String xsdError) {
        String str = xsdError;
        String[] result = str.split("'");
        String field = "";
        String finalMessage = "";
        Integer size = result.length - 1;
        String lastField = result[size];
        if( lastField.trim().equals("is expected.")) { //requerid
            String[] term = new String [] {result[1]};
            field = AdapterXSDError.getField(term);
            finalMessage = field + " não pode ser em branco.";
        }
        else if (!AdapterXSDError.isNullOrEmpty(result[1])) { //invalid
            field = AdapterXSDError.getField(result);
            finalMessage = "O valor " + result[1] + " é inválido para o campo " + field + " do Expedidor/Recebedor.";
        } else { // campo em branco
            field = AdapterXSDError.getField(result);
            finalMessage = field + " do Expedidor/Recebedor não pode ser em branco.";
        }
        return finalMessage;
    }

    public static String getField(String[] term) {
        if(term.length <= 1) {
            XSDFields fields = XSDFields.getFieldTypeByKey(term[0]);
            if(AdapterXSDError.isNullOrEmpty(fields)) {
                return term[0];
            } else {
                return fields.getDescription();
            }
        } else {
            for (String r : term) {
                String[] fieldComplet = r.split("_");
                if(fieldComplet.length > 1) {
                    XSDFields fields = XSDFields.getFieldTypeByKey(fieldComplet[1]);
                    if(AdapterXSDError.isNullOrEmpty(fields)) {
                        return fieldComplet[1];
                    } else {
                        return fields.getDescription();
                    }
                }
            }
        }

        return "";
    }

    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof String && obj.equals("")) {
            return true;
        } else if (obj instanceof Long && obj.equals(0l)) {
            return true;
        } else if (obj instanceof BigInteger && obj.equals(BigInteger.ZERO)) {
            return true;
        } else if (obj instanceof Integer && obj.equals(0)) {
            return true;
        } else if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        } else return obj instanceof Map && ((Map) obj).isEmpty();
    }
}
