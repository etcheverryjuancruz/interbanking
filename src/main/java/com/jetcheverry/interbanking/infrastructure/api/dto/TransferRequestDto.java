package com.jetcheverry.interbanking.infrastructure.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransferRequestDto {

    @NotBlank(message = "El Tax ID de la empresa no puede estar vacío")
    private String companyTaxId;

    @NotBlank(message = "La cuenta de debito no puede estar vacía")
    private String debitAccount;

    @NotBlank(message = "La cuenta de crédito no puede estar vacía")
    private String creditAccount;

    @NotNull(message = "El monto de la transferencia no puede ser nulo")
    @Positive(message = "El monto de la transferencia debe ser mayor que 0")
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
