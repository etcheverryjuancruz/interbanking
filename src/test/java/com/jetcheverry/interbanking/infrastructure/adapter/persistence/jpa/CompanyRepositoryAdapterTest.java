package com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa;

import com.jetcheverry.interbanking.domain.model.Company;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.entity.CompanyEntity;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.mapper.CompanyMapperEntity;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.repository.CompanyJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class CompanyRepositoryAdapterTest {

    @Mock
    private CompanyJpaRepository companyJpaRepository;

    @Mock
    private CompanyMapperEntity companyMapperEntity;

    @InjectMocks
    private CompanyRepositoryAdapter companyRepositoryAdapter;

    private CompanyEntity companyEntity;
    private Company company;

    @BeforeEach
    void setUp() {
        companyEntity = new CompanyEntity("TAXIDA", "Company A", LocalDate.of(2024, 2, 1));
        company = new Company(1L, "TAXIDA", "Company A", LocalDate.of(2024, 2, 1));
    }

    @Test
    void shouldSaveCompanySuccessfully() {
        when(companyMapperEntity.toEntity(company)).thenReturn(companyEntity);
        when(companyJpaRepository.save(companyEntity)).thenReturn(companyEntity);
        when(companyMapperEntity.toDomain(companyEntity)).thenReturn(company);

        Company result = companyRepositoryAdapter.save(company);

        assertNotNull(result);
        assertEquals(company.getTaxId(), result.getTaxId());
        assertEquals(company.getBusinessName(), result.getBusinessName());
        verify(companyJpaRepository, times(1)).save(companyEntity);
    }

    @Test
    void shouldReturnCompaniesJoinedLastMonth() {
        LocalDate lastMonth = LocalDate.of(2024, 1, 1);
        List<CompanyEntity> companyEntities = List.of(companyEntity);
        List<Company> expectedCompanies = List.of(company);

        when(companyJpaRepository.findByJoinDateAfter(lastMonth)).thenReturn(companyEntities);
        when(companyMapperEntity.toDomain(companyEntity)).thenReturn(company);

        List<Company> result = companyRepositoryAdapter.findCompaniesJoinedLastMonth(lastMonth);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedCompanies, result);
        verify(companyJpaRepository, times(1)).findByJoinDateAfter(lastMonth);
    }

    @Test
    void shouldReturnEmptyListWhenNoCompaniesJoinedLastMonth() {
        LocalDate lastMonth = LocalDate.of(2024, 1, 1);

        when(companyJpaRepository.findByJoinDateAfter(lastMonth)).thenReturn(Collections.emptyList());

        List<Company> result = companyRepositoryAdapter.findCompaniesJoinedLastMonth(lastMonth);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(companyJpaRepository, times(1)).findByJoinDateAfter(lastMonth);
    }
}
