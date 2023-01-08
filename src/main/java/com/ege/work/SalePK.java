package com.ege.work;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SalePK implements Serializable {
    private String custCardNum;
    private String artikelNo;

    public SalePK() {
    }

    public SalePK(String custCardNum, String artikelNo) {
        this.custCardNum = custCardNum;
        this.artikelNo = artikelNo;
    }

    public String getCustCardNum() {
        return custCardNum;
    }

    public void setCustCardNum(String custCardNum) {
        this.custCardNum = custCardNum;
    }

    public String getArtikelNo() {
        return artikelNo;
    }

    public void setArtikelNo(String artikelNo) {
        this.artikelNo = artikelNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalePK salePK = (SalePK) o;
        return Objects.equals(custCardNum, salePK.custCardNum) && Objects.equals(artikelNo, salePK.artikelNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(custCardNum, artikelNo);
    }
}
