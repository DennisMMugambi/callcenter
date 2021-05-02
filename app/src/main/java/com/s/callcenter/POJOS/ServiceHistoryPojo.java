package com.s.callcenter.POJOS;

public class ServiceHistoryPojo {
    private String companyName, dateServiced,
            service_description, technician_name;

    public ServiceHistoryPojo() {
    }

    public ServiceHistoryPojo(String companyName, String dateServiced, String service_description, String technician_name) {
        this.companyName = companyName;
        this.dateServiced = dateServiced;
        this.service_description = service_description;
        this.technician_name = technician_name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDateServiced() {
        return dateServiced;
    }

    public void setDateServiced(String dateServiced) {
        this.dateServiced = dateServiced;
    }

    public String getService_description() {
        return service_description;
    }

    public void setService_description(String service_description) {
        this.service_description = service_description;
    }

    public String getTechnician_name() {
        return technician_name;
    }

    public void setTechnician_name(String technician_name) {
        this.technician_name = technician_name;
    }
}