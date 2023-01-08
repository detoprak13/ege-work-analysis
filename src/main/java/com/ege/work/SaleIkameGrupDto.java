package com.ege.work;

import java.util.ArrayList;

public class SaleIkameGrupDto {
    private String ikameGrup;
    private ArrayList<SalePerCust> salePerCusts;

    public String getIkameGrup() {
        return ikameGrup;
    }

    public void setIkameGrup(String ikameGrup) {
        this.ikameGrup = ikameGrup;
    }

    public ArrayList<SalePerCust> getSalePerCusts() {
        return salePerCusts;
    }

    public void setSalePerCusts(ArrayList<SalePerCust> salePerCusts) {
        this.salePerCusts = salePerCusts;
    }
}
