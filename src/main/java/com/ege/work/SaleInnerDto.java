package com.ege.work;

import java.util.ArrayList;
import java.util.Objects;

public class SaleInnerDto {
    private String artikelNo;
    private ArrayList<SaleDto> sales = new ArrayList<>();

    public String getArtikelNo() {
        return artikelNo;
    }

    public void setArtikelNo(String artikelNo) {
        this.artikelNo = artikelNo;
    }

    public ArrayList<SaleDto> getSales() {
        return sales;
    }

    public void setSales(ArrayList<SaleDto> sales) {
        this.sales = sales;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleInnerDto that = (SaleInnerDto) o;
        return Objects.equals(artikelNo, that.artikelNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artikelNo);
    }
}
