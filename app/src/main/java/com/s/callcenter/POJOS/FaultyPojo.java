package com.s.callcenter.POJOS;

public class FaultyPojo {
    private String companyName, fault, date;

    public FaultyPojo(String companyName, String fault, String date) {
        this.companyName = companyName;
        this.fault = fault;
        this.date = date;
    }


    public FaultyPojo() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFault() {
        return fault;
    }

    public void setFaulty(String fault) {
        this.fault = fault;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}