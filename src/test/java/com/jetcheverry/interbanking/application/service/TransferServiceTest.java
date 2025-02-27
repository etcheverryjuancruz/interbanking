package com.jetcheverry.interbanking.application.service;

import com.jetcheverry.interbanking.domain.model.Company;
import com.jetcheverry.interbanking.domain.model.Transfer;
import com.jetcheverry.interbanking.domain.port.out.TransferRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {
    @Mock
    private TransferRepositoryPort transferRepository;

    @InjectMocks
    private TransferService transferService;

    private Transfer transfer;
    private Company company;

    @BeforeEach
    void setUp() {

        transfer = new Transfer(null, "TAXIDA", new BigDecimal("1500.50") ,"123", "456", null );

        company = new Company(1L,"TAXIDA", "Company A", LocalDateTime.now().minusMonths(2).toLocalDate());
    }

    @Test
    void shouldRegisterTransferSuccessfully() {
        Transfer savedTransfer = new Transfer(1L, "TAXIDA", new BigDecimal("1500.50") ,"123", "456", LocalDateTime.now() );

        when(transferRepository.save(any(Transfer.class))).thenReturn(savedTransfer);

        Transfer result = transferService.registerTransfer(transfer);

        assertNotNull(result);
        assertNotNull(result.getTransferDate());
        assertEquals(savedTransfer.getAmount(), result.getAmount());
        verify(transferRepository, times(1)).save(any(Transfer.class));
    }


    @Test
    void shouldRegisterTransferWithProvidedDate() {
        LocalDateTime customDate = LocalDateTime.of(2024, 1, 15, 10, 0);
        transfer.setTransferDate(customDate);

        Transfer savedTransfer = new Transfer(
                1L, "TAXIDA", new BigDecimal("1500.50") ,"123", "456",  customDate);

        when(transferRepository.save(any(Transfer.class))).thenReturn(savedTransfer);

        Transfer result = transferService.registerTransfer(transfer);

        assertNotNull(result);
        assertEquals(customDate, result.getTransferDate());
        verify(transferRepository, times(1)).save(any(Transfer.class));
    }

    @Test
    void shouldReturnCompaniesWithTransfersLastMonth() {
        LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);
        List<Company> expectedCompanies = Arrays.asList(
                new Company(1L, "TAXIDA", "Company A", lastMonth.toLocalDate()),
                new Company(2L,"TAXIDB", "Company B", lastMonth.toLocalDate())
        );

        when(transferRepository.findCompaniesWithTransfersLastMonth(any(LocalDateTime.class)))
                .thenReturn(expectedCompanies);

        List<Company> result = transferService.getCompaniesWithTransfersLastMonth();

        assertEquals(expectedCompanies, result);
        assertFalse(result.isEmpty());

        verify(transferRepository, times(1)).findCompaniesWithTransfersLastMonth(any(LocalDateTime.class));
    }

    @Test
    void shouldReturnEmptyListWhenNoCompaniesWithTransfers() {
        LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);

        when(transferRepository.findCompaniesWithTransfersLastMonth(any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

       // when(transferRepository.findCompaniesWithTransfersLastMonth(lastMonth)).thenReturn(Collections.emptyList());

        List<Company> result = transferService.getCompaniesWithTransfersLastMonth();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(transferRepository, times(1)).findCompaniesWithTransfersLastMonth(any(LocalDateTime.class));
    }

}
