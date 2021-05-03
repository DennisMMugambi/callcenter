package com.s.callcenter.POJOS;

public class ServiceHistoryPojo {
    private String technician_name;
    private String dateServiced;

    public ServiceHistoryPojo() {
    }

    public ServiceHistoryPojo(String technician_name,  String dateServiced) {
        this.technician_name = technician_name;
        this.dateServiced = dateServiced;
    }

    public String getTechnician_name() {
        return technician_name;
    }

    public void setTechnician_name(String technician_name) {
        this.technician_name = technician_name;
    }

    public String getDateServiced() {
        return dateServiced;
    }

    public void setDateServiced(String dateServiced) {
        this.dateServiced = dateServiced;
    }
}
