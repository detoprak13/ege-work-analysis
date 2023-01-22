package com.ege.work;

import jakarta.persistence.*;

@Entity
@Table(name = "table_name_2")
@IdClass(SalePK.class)
public class Sale {
    private String custCardNum;
    private String ikameGrup;
    private String flag;
    private Integer esasUrunFlag;
    private String artikelNo;
    private Double toplamUrunAdedi;

    public Sale() {
    }

    public Sale(String custCardNum, String ikameGrup, String flag, Integer esasUrunFlag, String artikelNo, Double toplamUrunAdedi) {
        this.custCardNum = custCardNum;
        this.ikameGrup = ikameGrup;
        this.flag = flag;
        this.esasUrunFlag = esasUrunFlag;
        this.artikelNo = artikelNo;
        this.toplamUrunAdedi = toplamUrunAdedi;
    }

    @Id
    @Column(name = "custcardnumber")
    public String getCustCardNum() {
        return custCardNum;
    }

    public void setCustCardNum(String custCardNum) {
        this.custCardNum = custCardNum;
    }

    @Column(name = "ikame_grup")
    public String getIkameGrup() {
        return ikameGrup;
    }

    public void setIkameGrup(String ikameGrup) {
        this.ikameGrup = ikameGrup;
    }

    @Column(name = "flag")
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Column(name = "esas_urun_flag")
    public Integer getEsasUrunFlag() {
        return esasUrunFlag;
    }

    public void setEsasUrunFlag(Integer esasUrunFlag) {
        this.esasUrunFlag = esasUrunFlag;
    }

    @Id
    @Column(name = "artikel_no")
    public String getArtikelNo() {
        return artikelNo;
    }

    public void setArtikelNo(String artikelNo) {
        this.artikelNo = artikelNo;
    }

    @Column(name = "toplam_urun_adedi")
    public Double getToplamUrunAdedi() {
        return toplamUrunAdedi;
    }

    public void setToplamUrunAdedi(Double toplamUrunAdedi) {
        this.toplamUrunAdedi = toplamUrunAdedi;
    }
}
