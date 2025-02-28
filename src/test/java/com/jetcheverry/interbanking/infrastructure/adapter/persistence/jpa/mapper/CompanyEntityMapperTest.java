package com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.mapper;

import com.jetcheverry.interbanking.domain.model.Company;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.entity.CompanyEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CompanyEntityMapperTest {

    private CompanyEntityMapper companyMapper;
    private Company company;
    private CompanyEntity companyEntity;

    @BeforeEach
    void setUp() {
        companyMapper = new CompanyEntityMapper();

        company = new Company(1L, "TAXIDA", "Company A", LocalDate.of(2024, 2, 1));
        companyEntity = new CompanyEntity("TAXIDA", "Company A", LocalDate.of(2024, 2, 1));
    }

    @Test
    void shouldConvertToEntityCorrectly() {
        CompanyEntity result = companyMapper.toEntity(company);

        assertNotNull(result);
        assertEquals(company.getTaxId(), result.getTaxId());
        assertEquals(company.getBusinessName(), result.getBusinessName());
        assertEquals(company.getJoinDate(), result.getJoinDate());
    }

    @Test
    void shouldConvertToDomainCorrectly() {
        Company result = companyMapper.toDomain(companyEntity);

        assertNotNull(result);
        assertNull(result.getId());
        assertEquals(companyEntity.getTaxId(), result.getTaxId());
        assertEquals(companyEntity.getBusinessName(), result.getBusinessName());
        assertEquals(companyEntity.getJoinDate(), result.getJoinDate());
    }

    @Test
    void shouldReturnNullWhenConvertingNullToEntity() {
        assertNull(companyMapper.toEntity(null));
    }

    @Test
    void shouldReturnNullWhenConvertingNullToDomain() {
        assertNull(companyMapper.toDomain(null));
    }
}
