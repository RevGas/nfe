package com.fincatto.dfe.utils;

import com.fincatto.documentofiscal.nfe.XSDFields;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class AdapterXSDError {
    public static String getErrorMessage(String xsdError) {
        String str = xsdError;
        String[] result = str.split("'");
        String field = "";
        String finalMessage = "";
        Integer size = result.length - 1;
        String lastField = result[size].trim();
        if( lastField.equals("is expected.")) { //requerid
            finalMessage = getMessageEmpty(xsdError);
        }
        else if (!AdapterXSDError.isNullOrEmpty(result[1])) { //invalid
            field = AdapterXSDError.getField(result);
            finalMessage = "O valor " + result[1] + " é inválido para o campo " + field + ".";
        } else { // campo em branco
            field = AdapterXSDError.getField(result);
            finalMessage = field + " não pode ser em branco.";
        }
        return finalMessage;
    }

    public static String getField(String[] term) {
        if(term.length <= 1) {
            XSDFields fieldAdapter = XSDFields.getFieldTypeByKey(term[0]);
            if(AdapterXSDError.isNullOrEmpty(fieldAdapter)) {
                return term[0];
            } else {
                return fieldAdapter.getDescription();
            }
        } else {
            for (String r : term) {
                String[] fieldComplet = r.split("_");
                if(fieldComplet.length > 1) {
                    XSDFields fieldAdapter = XSDFields.getFieldTypeByKey(fieldComplet[1]);
                    if(AdapterXSDError.isNullOrEmpty(fieldAdapter)) {
                        return fieldComplet[1];
                    } else {
                        return fieldAdapter.getDescription();
                    }
                }
            }
            // sem nó raiz
            //teste
            int size = term.length - 1;
            String lastField = term[size].trim();
            if(lastField.equals(".")) {
                XSDFields fieldAdapter = XSDFields.getFieldTypeByKey(term[size - 1].trim());
                if(AdapterXSDError.isNullOrEmpty(fieldAdapter)) {
                    return term[size - 1].trim();
                } else {
                    return fieldAdapter.getDescription();
                }
            } else {
                XSDFields fieldAdapter = XSDFields.getFieldTypeByKey(lastField);
                if(AdapterXSDError.isNullOrEmpty(fieldAdapter)) {
                    return lastField;
                } else {
                    return fieldAdapter.getDescription();
                }
            }
        }
    }

    public static String getMessageEmpty(String xsdError) {
        String str = xsdError;
        String[] result = str.split("'");
        String erroComplet = result[3].replace("{", " ")
                .replace("}", " ").replace("\"", " ");
        String[] fieldErroComplet = erroComplet.split(",");

        ArrayList<String> fieldsResult = new ArrayList<>();
        for (String e : fieldErroComplet) {
            String[] fields = e.split(" :");
            for (String f : fields) {
                if (!f.contains("http")) {
                    fieldsResult.add(f.trim());
                }
            }
        }
        ArrayList<String> newFiedls = new ArrayList<>();
        fieldsResult.forEach(messageField -> {
            XSDFields field = XSDFields.getFieldTypeByKey(messageField);
            if(AdapterXSDError.isNullOrEmpty(field)) {
                newFiedls.add(messageField);
            } else {
                newFiedls.add(field.getDescription());
            }
        });
        String messageFinal = "Um dos seguintes campos obrigatórios está vazio: " + newFiedls.toString() + ".";
        return messageFinal.replace("[", "").replace("]", "");
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
