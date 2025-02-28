package com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.mapper;

import com.jetcheverry.interbanking.domain.model.Transfer;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.entity.CompanyEntity;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.entity.TransferEntity;
import org.springframework.stereotype.Component;

@Component
public class TransferEntityMapper {

    public TransferEntity toEntity(Transfer transfer, CompanyEntity companyEntity) {
        return new TransferEntity(
                null,
                companyEntity,
                transfer.getDebitAccount(),
                transfer.getCreditAccount(),
                transfer.getAmount(),
                transfer.getTransferDate()
        );
    }
    public Transfer toDomain(TransferEntity entity) {
        return new Transfer(
                entity.getId(),
                entity.getCompany().getTaxId(),
                entity.getAmount(),
                entity.getDebitAccount(),
                entity.getCreditAccount(),
                entity.getTransferDate()
        );
    }
}
