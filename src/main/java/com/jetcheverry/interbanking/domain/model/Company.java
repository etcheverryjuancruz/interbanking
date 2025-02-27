package com.jetcheverry.interbanking.domain.model;


import java.time.LocalDate;



public class Company {


    private Long id;
    private String taxId;
    private String businessName;
    private LocalDate joinDate;

    public Company() {
    }

    public Company(Long id, String taxId, String businessName, LocalDate joinDate) {
        this.id = id;
        this.businessName = businessName;
        this.joinDate = joinDate;
        this.taxId = taxId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
}
