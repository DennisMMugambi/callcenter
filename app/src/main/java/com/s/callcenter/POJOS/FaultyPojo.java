package com.s.callcenter.POJOS;

public class FaultyPojo {
    private String companyName, fault;

    public FaultyPojo(String companyName, String fault) {
        this.companyName = companyName;
        this.fault = fault;
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
}
