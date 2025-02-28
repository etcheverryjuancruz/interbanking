package com.jetcheverry.interbanking.infrastructure.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransferResponseDto {

    private Long id;
    private String companyTaxId;
    private String debitAccount;
    private String creditAccount;
    private BigDecimal amount;
    private LocalDateTime transferDate;

    public TransferResponseDto() {
    }

    public TransferResponseDto(Long id, String companyTaxId, String debitAccount, String creditAccount, BigDecimal amount, LocalDateTime transferDate) {
        this.id = id;
        this.companyTaxId = companyTaxId;
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

    public String getCompanyTaxId() {
        return companyTaxId;
    }

    public void setCompanyTaxId(String companyTaxId) {
        this.companyTaxId = companyTaxId;
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
