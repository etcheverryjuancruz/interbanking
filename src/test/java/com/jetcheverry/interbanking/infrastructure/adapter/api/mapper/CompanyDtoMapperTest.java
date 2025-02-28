package com.jetcheverry.interbanking.infrastructure.adapter.api.mapper;

import com.jetcheverry.interbanking.domain.model.Company;
import com.jetcheverry.interbanking.infrastructure.api.dto.CompanyRequestDto;
import com.jetcheverry.interbanking.infrastructure.api.dto.CompanyResponseDto;
import com.jetcheverry.interbanking.infrastructure.api.mapper.CompanyDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CompanyDtoMapperTest {

    private CompanyDtoMapper companyDtoMapper;

    @BeforeEach
    void setUp() {
        companyDtoMapper = new CompanyDtoMapper();
    }

    @Test
    void shouldConvertRequestDtoToDomainCorrectly() {
        CompanyRequestDto requestDto = new CompanyRequestDto("TAXIDA", "Company A", null);

        Company result = companyDtoMapper.toDomain(requestDto);

        assertNotNull(result);
        assertNull(result.getId());
        assertEquals(requestDto.getTaxId(), result.getTaxId());
        assertEquals(requestDto.getBusinessName(), result.getBusinessName());
        assertNull(result.getJoinDate());
    }

    @Test
    void shouldConvertDomainToResponseDtoCorrectly() {
        Company company = new Company(1L, "TAXIDA", "Company A", LocalDate.of(2025, 2, 1));

        CompanyResponseDto result = companyDtoMapper.toResponseDto(company);

        assertNotNull(result);
        assertEquals(company.getId(), result.getId());
        assertEquals(company.getTaxId(), result.getTaxId());
        assertEquals(company.getBusinessName(), result.getBusinessName());
        assertEquals(company.getJoinDate(), result.getJoinDate());
    }

    @Test
    void shouldReturnNullWhenConvertingNullToDomain() {
        assertNull(companyDtoMapper.toDomain(null));
    }

    @Test
    void shouldReturnNullWhenConvertingNullToResponseDto() {
        assertNull(companyDtoMapper.toResponseDto(null));
    }
}
