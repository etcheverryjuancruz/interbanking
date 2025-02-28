package com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
@Table(name = "companies")
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "tax_id", unique = true, nullable = false)
    private String taxId;

    @NotBlank
    @Column(nullable = false)
    private String businessName;

    private LocalDate joinDate;

    public CompanyEntity() {
    }

    public CompanyEntity(String taxId, String businessName, LocalDate joinDate) {
        this.taxId = taxId;
        this.businessName = businessName;
        this.joinDate = joinDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    @Override
    public String toString() {
        return "CompanyEntity{" +
                "id=" + id +
                ", taxId='" + taxId + '\'' +
                ", businessName='" + businessName + '\'' +
                ", joinDate=" + joinDate +
                '}';
    }
}
