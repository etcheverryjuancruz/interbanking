package com.jetcheverry.interbanking.domain.model;


import java.math.BigDecimal;
import java.time.LocalDateTime;


public class Transfer {

    private Long id;
    private String companyTaxId;
    private BigDecimal amount;
    private String debitAccount;
    private String creditAccount;
    private LocalDateTime transferDate;

    public Transfer() {
    }

    public Transfer(Long id, String companyTaxId, BigDecimal amount, String debitAccount, String creditAccount, LocalDateTime transferDate) {
        this.id = id;
        this.companyTaxId = companyTaxId;
        this.amount = amount;
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
        this.transferDate = transferDate;
    }

    public Long getId() {
        return id;
    }

    public String getCompanyTaxId() {
        return companyTaxId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDebitAccount() {
        return debitAccount;
    }

    public String getCreditAccount() {
        return creditAccount;
    }

    public LocalDateTime getTransferDate() {
        return transferDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCompanyId(String companyTaxId) {
        this.companyTaxId = companyTaxId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDebitAccount(String debitAccount) {
        this.debitAccount = debitAccount;
    }

    public void setCreditAccount(String creditAccount) {
        this.creditAccount = creditAccount;
    }

    public void setTransferDate(LocalDateTime transferDate) {
        this.transferDate = transferDate;
    }
}
