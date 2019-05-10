package com.fincatto.documentofiscal;

/**
 * URls qrCode: http://nfce.encat.org/desenvolvedor/qrcode/
 * URLs consulta chave acesso: http://nfce.encat.org/consumidor/consulte-sua-nota/
 **/
public enum DFUnidadeFederativa {

    AC("AC", "Acre", "12", "http://hml.sefaznet.ac.gov.br/nfce/qrcode", "http://hml.sefaznet.ac.gov.br/nfce/qrcode"
            , "www.sefaznet.ac.gov.br/nfce/consulta", "www.sefaznet.ac.gov.br/nfce/consulta"),
    AL("AL", "Alagoas", "27", null, null
            , "www.sefaz.al.gov.br/nfce/consulta", "www.sefaz.al.gov.br/nfce/consulta"),
    AP("AP", "Amap\u00E1", "16", "https://www.sefaz.ap.gov.br/nfcehml/nfce.php", "https://www.sefaz.ap.gov.br/nfce/nfce.php"
            , "www.sefaz.ap.gov.br/nfce/consulta", "www.sefaz.ap.gov.br/nfce/consulta"),
    AM("AM", "Amazonas", "13", "http://homnfce.sefaz.am.gov.br/nfceweb/consultarNFCe.jsp", "http://sistemas.sefaz.am.gov.br/nfceweb/consultarNFCe.jsp"
            , "www.sefaz.am.gov.br/nfce/consulta", "www.sefaz.am.gov.br/nfce/consulta"),
    BA("BA", "Bahia", "29", "http://hnfe.sefaz.ba.gov.br/servicos/nfce/modulos/geral/NFCEC_consulta_chave_acesso.aspx", "http://nfe.sefaz.ba.gov.br/servicos/nfce/modulos/geral/NFCEC_consulta_chave_acesso.aspx"
            , "http://hinternet.sefaz.ba.gov.br/nfce/consulta", "www.sefaz.ba.gov.br/nfce/consulta"),
    CE("CE", "Cear\u00E1", "23", null, null
            , "www.sefaz.ce.gov.br/nfce/consulta", "www.sefaz.ce.gov.br/nfce/consulta"),
    DF("DF", "Distrito Federal", "53", "http://dec.fazenda.df.gov.br/ConsultarNFCe.aspx", "http://dec.fazenda.df.gov.br/ConsultarNFCe.aspx"
            , "www.fazenda.df.gov.br/nfce/consulta", "www.fazenda.df.gov.br/nfce/consulta"),
    GO("GO", "Goi\u00E1s", "52", "http://homolog.sefaz.go.gov.br/nfeweb/sites/nfce/danfeNFCe", "http://nfe.sefaz.go.gov.br/nfeweb/sites/nfce/danfeNFCe"
            , "www.sefaz.go.gov.br/nfce/consulta", "www.sefaz.go.gov.br/nfce/consulta"),
    ES("ES", "Esp\u00EDrito Santo", "32", "http://homologacao.sefaz.es.gov.br/ConsultaNFCe/qrcode.aspx", "http://app.sefaz.es.gov.br/ConsultaNFCe/qrcode.aspx"
            , "www.sefaz.es.gov.br/nfce/consulta", "www.sefaz.es.gov.br/nfce/consulta"),
    MA("MA", "Maranh\u00E3o", "21", "http://homologacao.sefaz.ma.gov.br/portal/consultarNFCe.jsp", "http://nfce.sefaz.ma.gov.br/portal/consultarNFCe.jsp"
            , "www.sefaz.ma.gov.br/nfce/consulta", "www.sefaz.ma.gov.br/nfce/consulta"),
    MT("MT", "Mato Grosso", "51", "http://homologacao.sefaz.mt.gov.br/nfce/consultanfce", "http://www.sefaz.mt.gov.br/nfce/consultanfce"
            , "http://homologacao.sefaz.mt.gov.br/nfce/consultanfce", "www.sefaz.mt.gov.br/nfce/consultanfce"),
    MS("MS", "Mato Grosso do Sul", "50", "http://www.dfe.ms.gov.br/nfce/qrcode", "http://www.dfe.ms.gov.br/nfce/qrcode"
            , "http://www.dfe.ms.gov.br/nfce", "http://www.dfe.ms.gov.br/nfce"),
    MG("MG", "Minas Gerais", "31", "https://nfce.fazenda.mg.gov.br/portalnfce/sistema/qrcode.xhtml"
            , "https://nfce.fazenda.mg.gov.br/portalnfce/sistema/qrcode.xhtml", "http://hnfce.fazenda.mg.gov.br/portalnfce", "http://nfce.fazenda.mg.gov.br/portalnfce"),
    PA("PA", "Par\u00E1", "15", "https://appnfc.sefa.pa.gov.br/portal-homologacao/view/consultas/nfce/nfceForm.seam", "https://appnfc.sefa.pa.gov.br/portal/view/consultas/nfce/nfceForm.seam"
            , "www.sefa.pa.gov.br/nfce/consulta", "www.sefa.pa.gov.br/nfce/consulta"),
    PB("PB", "Paraiba", "25", "http://www.receita.pb.gov.br/nfcehom", "http://www.receita.pb.gov.br/nfce"
            , "www.receita.pb.gov.br/nfcehom", "www.receita.pb.gov.br/nfce/consulta"),
    PR("PR", "Paran\u00E1", "41", "http://www.fazenda.pr.gov.br/nfce/qrcode", "http://www.fazenda.pr.gov.br/nfce/qrcode"
            , "http://www.fazenda.pr.gov.br/nfce/consulta", "http://www.fazenda.pr.gov.br/nfce/consulta"),
    PE("PE", "Pernambuco", "26", "http://nfcehomolog.sefaz.pe.gov.br/nfce-web/consultarNFCe", "http://nfce.sefaz.pe.gov.br/nfce-web/consultarNFCe"
            , "http://nfcehomolog.sefaz.pe.gov.br/nfce/consulta", "http://nfce.sefaz.pe.gov.br/nfce/consulta"),
    PI("PI", "Piau\u00ED", "22", "http://webas.sefaz.pi.gov.br/nfceweb-homologacao/consultarNFCe.jsf", "http://webas.sefaz.pi.gov.br/nfceweb/consultarNFCe.jsf"
            , "www.sefaz.pi.gov.br/nfce/consulta", "www.sefaz.pi.gov.br/nfce/consulta"),
    RJ("RJ", "Rio de Janeiro", "33", "http://www4.fazenda.rj.gov.br/consultaNFCe/QRCode", "http://www4.fazenda.rj.gov.br/consultaNFCe/QRCode"
            , "www.fazenda.rj.gov.br/nfce/consulta", "www.fazenda.rj.gov.br/nfce/consulta"),
    RN("RN", "Rio Grande do Norte", "24", "http://hom.nfce.set.rn.gov.br/consultarNFCe.aspx", "http://nfce.set.rn.gov.br/consultarNFCe.aspx"
            , "www.set.rn.gov.br/nfce/consulta", "www.set.rn.gov.br/nfce/consulta"),
    RS("RS", "Rio Grande do Sul", "43", "https://www.sefaz.rs.gov.br/NFCE/NFCE-COM.aspx", "https://www.sefaz.rs.gov.br/NFCE/NFCE-COM.aspx"
            , "www.sefaz.rs.gov.br/nfce/consulta", "www.sefaz.rs.gov.br/nfce/consulta"),
    RO("RO", "Rond\u00F4nia", "11", "http://www.nfce.sefin.ro.gov.br/consultanfce/consulta.jsp", "http://www.nfce.sefin.ro.gov.br/consultanfce/consulta.jsp"
            , "www.sefin.ro.gov.br/nfce/consulta", "www.sefin.ro.gov.br/nfce/consulta"),
    RR("RR", "Roraima", "14", "http://200.174.88.103:8080/nfce/servlet/qrcode", "https://www.sefaz.rr.gov.br/nfce/servlet/qrcode"
            , "www.sefaz.rr.gov.br/nfce/consulta", "www.sefaz.rr.gov.br/nfce/consulta"),
    SP("SP", "S\u00E3o Paulo", "35", "https://www.homologacao.nfce.fazenda.sp.gov.br/NFCeConsultaPublica/Paginas/ConsultaQRCode.aspx", "https://www.nfce.fazenda.sp.gov.br/NFCeConsultaPublica/Paginas/ConsultaQRCode.aspx"
            , "https://www.homologacao.nfce.fazenda.sp.gov.br/consulta", "https://www.nfce.fazenda.sp.gov.br/consulta"),
    SC("SC", "Santa Catarina", "42"),
    SE("SE", "Sergipe", "28", "http://www.hom.nfe.se.gov.br/portal/consultarNFCe.jsp", "http://www.nfce.se.gov.br/portal/consultarNFCe.jsp"
            , "http://www.hom.nfe.se.gov.br/nfce/consulta", "http://www.nfce.se.gov.br/nfce/consulta"),
    TO("TO", "Tocantins", "17", null, null
            , "http://homologacao.sefaz.to.gov.br/nfce/consulta.jsf", "http://www.sefaz.to.gov.br/nfce/consulta.jsf"),
    NACIONAL("NC", "Nacional", "90"),
    RFB("RFB", "RFB", "91"),
    EX("EX", "Exterior", "99");

    private final String codigo;
    private final String descricao;
    private final String codigoIbge;
    private final String qrCodeHomologacao;
    private final String qrCodeProducao;
    private final String consultaChaveAcessoHomologacao;
    private final String consultaChaveAcessoProducao;

    DFUnidadeFederativa(final String codigo, final String descricao, final String codigoIbge, String qrCodeHomologacao, String qrCodeProducao, String consultaChaveAcessoHomologacao, String consultaChaveAcessoProducao) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.codigoIbge = codigoIbge;
        this.qrCodeHomologacao = qrCodeHomologacao;
        this.qrCodeProducao = qrCodeProducao;
        this.consultaChaveAcessoHomologacao = consultaChaveAcessoHomologacao;
        this.consultaChaveAcessoProducao = consultaChaveAcessoProducao;
    }

    DFUnidadeFederativa(final String codigo, final String descricao, final String codigoIbge) {
        this(codigo, descricao, codigoIbge, null, null, null, null);
    }

    public String getCodigo() {
        return this.codigo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public String getCodigoIbge() {
        return this.codigoIbge;
    }

    public String getQrCodeHomologacao() {
        return qrCodeHomologacao;
    }

    public String getQrCodeProducao() {
        return qrCodeProducao;
    }

    public String getConsultaChaveAcessoHomologacao() {
        return consultaChaveAcessoHomologacao;
    }

    public String getConsultaChaveAcessoProducao() {
        return consultaChaveAcessoProducao;
    }

    @Override
    public String toString() {
        return this.getDescricao();
    }

    /**
     * Identifica a UF pela sigla ou pelo codigo IBGE.
     *
     * @param codigo Sigla ou codigo IBGE da UF.
     * @return Objeto da UF.
     */
    public static DFUnidadeFederativa valueOfCodigo(final String codigo) {
        for (final DFUnidadeFederativa uf : DFUnidadeFederativa.values()) {
            if (uf.getCodigo().equalsIgnoreCase(codigo)) {
                return uf;
            } else if (uf.getCodigoIbge().equalsIgnoreCase(codigo)) {
                return uf;
            }
        }
        throw new IllegalArgumentException(String.format("N\u00e3o existe o c\u00f3digo %s no mapeamento.", codigo));
    }
}
