package com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfers")
public class TransferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_tax_id", referencedColumnName = "tax_id", nullable = false)
    private CompanyEntity company;

    @Column(name = "debit_account", nullable = false)
    private String debitAccount;

    @Column(name = "credit_account", nullable = false)
    private String creditAccount;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "transfer_date", nullable = false)
    private LocalDateTime transferDate;

    public TransferEntity() {
    }

    public TransferEntity(Long id, CompanyEntity company, String debitAccount, String creditAccount, BigDecimal amount, LocalDateTime transferDate) {
        this.id = id;
        this.company = company;
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
        this.amount = amount;
        this.transferDate = transferDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public String getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(String debitAccount) {
        this.debitAccount = debitAccount;
    }

    public String getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(String creditAccount) {
        this.creditAccount = creditAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDateTime transferDate) {
        this.transferDate = transferDate;
    }
}
