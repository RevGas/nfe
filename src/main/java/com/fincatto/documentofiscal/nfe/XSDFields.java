package com.fincatto.documentofiscal.nfe;

public enum  XSDFields {
    CEP(0, "Cep", "CEPTEndereco"),
    FANTASYNAME(1, "Nome Fantasia", "xFantemitinfNFeTNFe"),
    NAME(2, "Razão Social ou Nome", "xNome"),
    ADDITIONALINFORMATION(3, "Informações Adicionais", "infCplinfAdicinfNFeTNFe"),
    PASSPORT(4, "Número do passaporte", "idEstrangeiro"),
    STATEREGISTRATION(5, "Inscrição Estadual do destinatário", "TIeDestNaoIsento")
    ;

    Integer id;
    String description;
    String key;

    XSDFields(Integer id, String description, String key) {
        this.id = id;
        this.description = description;
        this.key = key;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static XSDFields getFieldTypeByKey(String key) {
        for (XSDFields xsdFields : XSDFields.values()) {
            if (xsdFields.getKey().equals(key)) {
                return xsdFields;
            }
        }
        return null;
    }
}
