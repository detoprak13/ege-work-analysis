package com.ege.work;

import java.util.Objects;

public class SaleDto {
    private String artikelNo;
    private Double toplamUrunAdedi;

    public String getArtikelNo() {
        return artikelNo;
    }

    public void setArtikelNo(String artikelNo) {
        this.artikelNo = artikelNo;
    }

    public Double getToplamUrunAdedi() {
        return toplamUrunAdedi;
    }

    public void setToplamUrunAdedi(Double toplamUrunAdedi) {
        this.toplamUrunAdedi = toplamUrunAdedi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleDto saleDto = (SaleDto) o;
        return Objects.equals(artikelNo, saleDto.artikelNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artikelNo);
    }
}
