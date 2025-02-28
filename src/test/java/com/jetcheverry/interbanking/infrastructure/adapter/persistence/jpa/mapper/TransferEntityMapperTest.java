package com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.mapper;

import com.jetcheverry.interbanking.domain.model.Transfer;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.entity.CompanyEntity;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.entity.TransferEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TransferEntityMapperTest {

    private TransferEntityMapper transferEntityMapper;

    private CompanyEntity companyEntity;
    private Transfer transfer;
    private TransferEntity transferEntity;

    @BeforeEach
    void setUp() {
        transferEntityMapper = new TransferEntityMapper();

        companyEntity = new CompanyEntity("A12345", "Company A", LocalDate.now());

        transfer = new Transfer(
                1L,
                "A12345",
                new BigDecimal("1000.00"),
                "123",
                "456",
                LocalDateTime.now()
        );

        transferEntity = new TransferEntity(
                1L,
                companyEntity,
                "123",
                "456",
                new BigDecimal("1000.00"),
                LocalDateTime.now()
        );
    }

    @Test
    void shouldMapTransferToTransferEntity() {
        TransferEntity entity = transferEntityMapper.toEntity(transfer, companyEntity);

        assertNotNull(entity);
        assertNull(entity.getId());
        assertEquals(companyEntity, entity.getCompany());
        assertEquals(transfer.getAmount(), entity.getAmount());
        assertEquals(transfer.getDebitAccount(), entity.getDebitAccount());
        assertEquals(transfer.getCreditAccount(), entity.getCreditAccount());
        assertEquals(transfer.getTransferDate(), entity.getTransferDate());
    }

    @Test
    void shouldMapTransferEntityToTransfer() {
        Transfer mappedTransfer = transferEntityMapper.toDomain(transferEntity);

        assertNotNull(mappedTransfer);
        assertEquals(transferEntity.getId(), mappedTransfer.getId());
        assertEquals(transferEntity.getCompany().getTaxId(), mappedTransfer.getCompanyTaxId());
        assertEquals(transferEntity.getAmount(), mappedTransfer.getAmount());
        assertEquals(transferEntity.getDebitAccount(), mappedTransfer.getDebitAccount());
        assertEquals(transferEntity.getCreditAccount(), mappedTransfer.getCreditAccount());
        assertEquals(transferEntity.getTransferDate(), mappedTransfer.getTransferDate());
    }


}
