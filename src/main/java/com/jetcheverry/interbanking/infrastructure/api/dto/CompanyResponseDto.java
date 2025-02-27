package com.jetcheverry.interbanking.infrastructure.api.dto;

import java.time.LocalDate;

public class CompanyResponseDto {

    private Long id;
    private String taxId;
    private String businessName;
    private LocalDate joinDate;

    public CompanyResponseDto() {
    }

    public CompanyResponseDto(LocalDate joinDate, String businessName, String taxId, Long id) {
        this.joinDate = joinDate;
        this.businessName = businessName;
        this.taxId = taxId;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
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
}
