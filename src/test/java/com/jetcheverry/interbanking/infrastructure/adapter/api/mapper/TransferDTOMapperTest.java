package com.jetcheverry.interbanking.infrastructure.adapter.api.mapper;

import com.jetcheverry.interbanking.domain.model.Transfer;
import com.jetcheverry.interbanking.infrastructure.api.dto.TransferRequestDto;
import com.jetcheverry.interbanking.infrastructure.api.dto.TransferResponseDto;
import com.jetcheverry.interbanking.infrastructure.api.mapper.TransferDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TransferDTOMapperTest {

    private TransferDTOMapper transferDtoMapper;

    @BeforeEach
    void setUp() {
        transferDtoMapper = new TransferDTOMapper();
    }

    @Test
    void shouldConvertRequestDtoToDomain() {
        TransferRequestDto requestDto = new TransferRequestDto("TAXIDA",  "123", "456", new BigDecimal("1000.00"),null);

        Transfer result = transferDtoMapper.toDomain(requestDto);

        assertNotNull(result);
        assertEquals("TAXIDA", result.getCompanyTaxId());
        assertEquals("123", result.getDebitAccount());
        assertEquals("456", result.getCreditAccount());
        assertEquals(new BigDecimal("1000.00"), result.getAmount());
    //    assertNotNull(result.getTransferDate());
    }

    @Test
    void shouldConvertDomainToResponseDto() {
        Transfer transfer = new Transfer(1L, "TAXIDA", new BigDecimal("1000.00"), "123", "456",  LocalDateTime.now());

        TransferResponseDto result = transferDtoMapper.toResponseDto(transfer);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("TAXIDA", result.getCompanyTaxId());
        assertEquals("123", result.getDebitAccount());
        assertEquals("456", result.getCreditAccount());
        assertEquals(new BigDecimal("1000.00"), result.getAmount());
    }

    @Test
    void shouldReturnNullWhenConvertingNullToDomain() {
        assertNull(transferDtoMapper.toDomain(null));
    }

    @Test
    void shouldReturnNullWhenConvertingNullToResponseDto() {
        assertNull(transferDtoMapper.toResponseDto(null));
    }
}
