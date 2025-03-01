package com.jetcheverry.interbanking.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class CompanyRequestDto {

    @NotBlank(message = "El taxId es obligatorio")
    private String taxId;

    @NotBlank(message = "El nombre de la empresa es obligatorio")
    private String businessName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate joinDate;

    public CompanyRequestDto() {
    }

    public CompanyRequestDto(String taxId, String businessName, LocalDate joinDate) {
        this.taxId = taxId;
        this.businessName = businessName;
        this.joinDate = joinDate;
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
