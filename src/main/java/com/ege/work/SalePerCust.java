package com.ege.work;

import java.util.ArrayList;

public class SalePerCust {
    private String custCardNum;
    private ArrayList<SaleInnerDto> saleResults;

    public String getCustCardNum() {
        return custCardNum;
    }

    public void setCustCardNum(String custCardNum) {
        this.custCardNum = custCardNum;
    }

    public ArrayList<SaleInnerDto> getSaleResults() {
        return saleResults;
    }

    public void setSaleResults(ArrayList<SaleInnerDto> saleResults) {
        this.saleResults = saleResults;
    }
}
