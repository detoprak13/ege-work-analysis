package com.ege.work;

import java.util.ArrayList;

public class Result {
    private String ikameGrup;
    private ArrayList<SaleInnerDto> dto = new ArrayList<>();

    public String getIkameGrup() {
        return ikameGrup;
    }

    public void setIkameGrup(String ikameGrup) {
        this.ikameGrup = ikameGrup;
    }

    public ArrayList<SaleInnerDto> getDto() {
        return dto;
    }

    public void setDto(ArrayList<SaleInnerDto> dto) {
        this.dto = dto;
    }
}
