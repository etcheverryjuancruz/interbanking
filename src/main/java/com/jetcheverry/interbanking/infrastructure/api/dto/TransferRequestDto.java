package com.jetcheverry.interbanking.infrastructure.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransferRequestDto {

    @NotBlank
    private String companyTaxId;

    @NotBlank
    private String debitAccount;

    @NotBlank
    private String creditAccount;

    @NotNull
    @Positive
    private BigDecimal amount;

    private LocalDateTime transferDate;

    public TransferRequestDto() {
    }

    public TransferRequestDto(String companyTaxId, String debitAccount, String creditAccount, BigDecimal amount, LocalDateTime tranferDate) {
        this.companyTaxId = companyTaxId;
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
        this.amount = amount;
        this.transferDate = tranferDate;
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
