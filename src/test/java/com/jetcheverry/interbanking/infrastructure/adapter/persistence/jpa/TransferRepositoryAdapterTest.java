package com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa;

import com.jetcheverry.interbanking.domain.model.Company;
import com.jetcheverry.interbanking.domain.model.Transfer;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.entity.CompanyEntity;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.entity.TransferEntity;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.mapper.CompanyEntityMapper;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.mapper.TransferEntityMapper;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.repository.CompanyJpaRepository;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.repository.TransferJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransferRepositoryAdapterTest {

    @Mock
    private TransferJpaRepository transferJpaRepository;

    @Mock
    private CompanyJpaRepository companyJpaRepository;

    @Mock
    private CompanyEntityMapper companyEntityMapper;

    @Mock
    private TransferEntityMapper transferEntityMapper;

    @InjectMocks
    private TransferRepositoryAdapter transferRepositoryAdapter;

    private CompanyEntity companyEntity;
    private Transfer transfer;
    private TransferEntity transferEntity;

    @BeforeEach
    void setUp() {

        companyEntity = new CompanyEntity( "A12345", "Company A", LocalDate.now().minusMonths(3));

        transfer = new Transfer(
                null,
                "TAXIDA",
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
    void shouldSaveTransferSuccessfully() {
        when(companyJpaRepository.findByTaxId(anyString())).thenReturn(Optional.of(companyEntity));
        when(transferEntityMapper.toEntity(transfer, companyEntity)).thenReturn(transferEntity);
        when(transferJpaRepository.save(any(TransferEntity.class))).thenReturn(transferEntity);
        when(transferEntityMapper.toDomain(transferEntity)).thenReturn(transfer);

        // ✅ Ejecutamos el método a probar
        Transfer savedTransfer = transferRepositoryAdapter.save(transfer);

        assertNotNull(savedTransfer);
        assertEquals("TAXIDA", savedTransfer.getCompanyTaxId());
        assertEquals(new BigDecimal("1000.00"), savedTransfer.getAmount());

        verify(companyJpaRepository, times(1)).findByTaxId(anyString());
        verify(transferEntityMapper, times(1)).toEntity(transfer, companyEntity);
        verify(transferJpaRepository, times(1)).save(transferEntity);
        verify(transferEntityMapper, times(1)).toDomain(transferEntity);
    }

    @Test
    void shouldThrowExceptionWhenCompanyNotFound() {
        // ✅ Simulamos que la empresa NO existe en la BD
        when(companyJpaRepository.findByTaxId(anyString())).thenReturn(Optional.empty());

        // ✅ Ejecutamos el método y validamos la excepción
        Exception exception = assertThrows(IllegalArgumentException.class, () -> transferRepositoryAdapter.save(transfer));

        // ✅ Verificamos que la excepción tiene el mensaje esperado
        assertEquals("Company with Tax ID TAXIDA not found.", exception.getMessage());

        // ✅ Verificamos que NO se haya intentado guardar la transferencia
        verify(transferJpaRepository, never()).save(any(TransferEntity.class));
    }

    @Test
    void shouldFindCompaniesWithTransfersLastMonth() {
        // ✅ Simulamos que hay transferencias recientes
        List<TransferEntity> transferEntities = List.of(transferEntity);

        when(transferJpaRepository.findDistinctByTransferDateAfter(any(LocalDateTime.class))).thenReturn(transferEntities);
        when(companyEntityMapper.toDomain(companyEntity)).thenReturn(new Company(1L,"A12345", "Company A", LocalDate.now().minusMonths(3)));

        // ✅ Ejecutamos el método a probar
        List<Company> companies = transferRepositoryAdapter.findCompaniesWithTransfersLastMonth(LocalDateTime.now().minusMonths(1));

        // ✅ Validamos que solo se devuelve una empresa
        assertEquals(1, companies.size());
        assertEquals("A12345", companies.get(0).getTaxId());

        // ✅ Verificamos que los métodos fueron llamados correctamente
        verify(transferJpaRepository, times(1)).findDistinctByTransferDateAfter(any(LocalDateTime.class));
        verify(companyEntityMapper, times(1)).toDomain(companyEntity);
    }

    @Test
    void shouldReturnEmptyListWhenNoTransfersLastMonth() {
        // Simulamos que el repositorio devuelve una lista vacía
        when(transferJpaRepository.findDistinctByTransferDateAfter(any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        List<Company> result = transferRepositoryAdapter.findCompaniesWithTransfersLastMonth(LocalDateTime.now().minusMonths(1));

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(transferJpaRepository, times(1)).findDistinctByTransferDateAfter(any(LocalDateTime.class));
    }
}
