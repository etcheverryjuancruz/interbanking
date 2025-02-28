package com.jetcheverry.interbanking.infrastructure.api.mapper;

import com.jetcheverry.interbanking.domain.model.Transfer;
import com.jetcheverry.interbanking.infrastructure.api.dto.TransferRequestDto;
import com.jetcheverry.interbanking.infrastructure.api.dto.TransferResponseDto;

public class TransferDTOMapper {

    public Transfer toDomain(TransferRequestDto dto) {
        if (dto == null) {
            return null;
        }

        return new Transfer(
                null,
                dto.getCompanyTaxId(),
                dto.getAmount(),
                dto.getDebitAccount(),
                dto.getCreditAccount(),
                dto.getTranferDate()
        );
    }

    public TransferResponseDto toResponseDto(Transfer transfer) {
        if (transfer == null) {
            return null;
        }

        return new TransferResponseDto(
                transfer.getId(),
                transfer.getCompanyTaxId(),
                transfer.getDebitAccount(),
                transfer.getCreditAccount(),
                transfer.getAmount(),
                transfer.getTransferDate()
        );
    }
}
