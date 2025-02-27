package com.jetcheverry.interbanking.infrastructure.api.dto;

import jakarta.validation.constraints.NotBlank;

public class CompanyRequestDto {

    @NotBlank
    private String taxId;

    @NotBlank
    private String businessName;

    public CompanyRequestDto() {
    }

    public CompanyRequestDto(String taxId, String businessName) {
        this.taxId = taxId;
        this.businessName = businessName;
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
}
