package com.jetcheverry.interbanking.application.service;

import com.jetcheverry.interbanking.domain.model.Company;
import com.jetcheverry.interbanking.domain.port.out.CompanyRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    private CompanyRepositoryPort companyRepositoryPort;

    @InjectMocks
    private CompanyService companyService;


    @Test
    void shouldReturnCompaniesJoinedLastMonth() {
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        List<Company> expectedCompanies = Arrays.asList(
                new Company( 1L,"TAXIDA", "Company A", lastMonth),
                new Company(2L, "TAXIDB",  "Company B", lastMonth )
        );

        when(companyRepositoryPort.findCompaniesJoinedLastMonth(lastMonth)).thenReturn(expectedCompanies);

        List<Company> result = companyService.getCompaniesJoinedLastMonth();

        assertEquals(expectedCompanies, result);
        verify(companyRepositoryPort, times(1)).findCompaniesJoinedLastMonth(lastMonth);
    }

    @Test
    void shouldReturnEmptyListWhenNoCompaniesJoinedLastMonth() {
        // Arrange
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        when(companyRepositoryPort.findCompaniesJoinedLastMonth(lastMonth)).thenReturn(Collections.emptyList());

        // Act
        List<Company> result = companyService.getCompaniesJoinedLastMonth();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(companyRepositoryPort, times(1)).findCompaniesJoinedLastMonth(lastMonth);
    }

    @Test
    void shouldRegisterCompanySuccessfully() {
        LocalDate lastMonth = LocalDate.now().minusMonths(1);

        Company company = new Company(null,  "TAXIDA", "Company A", null);
        Company savedCompany = new Company(1L,  "TAXIDA", "Company A", lastMonth);

        when(companyRepositoryPort.save(any(Company.class))).thenReturn(savedCompany);

        Company result = companyService.registerCompany(company);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(lastMonth, result.getJoinDate());
        verify(companyRepositoryPort, times(1)).save(any(Company.class));
    }
}
