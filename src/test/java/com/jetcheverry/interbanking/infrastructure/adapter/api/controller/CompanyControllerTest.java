package com.jetcheverry.interbanking.infrastructure.adapter.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jetcheverry.interbanking.domain.model.Company;
import com.jetcheverry.interbanking.domain.model.enums.CompanyFilterType;
import com.jetcheverry.interbanking.domain.port.in.CompanyServicePort;
import com.jetcheverry.interbanking.domain.port.in.TransferServicePort;
import com.jetcheverry.interbanking.infrastructure.api.controller.CompanyController;
import com.jetcheverry.interbanking.infrastructure.api.dto.CompanyRequestDto;
import com.jetcheverry.interbanking.infrastructure.api.dto.CompanyResponseDto;
import com.jetcheverry.interbanking.infrastructure.api.mapper.CompanyDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CompanyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CompanyServicePort companyService;

    @Mock
    private TransferServicePort transferService;

    @Mock
    private CompanyDtoMapper companyDtoMapper;

    @InjectMocks
    private CompanyController companyController;

    private ObjectMapper objectMapper;


    private Map<CompanyFilterType, Supplier<List<Company>>> filterHandlers;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        filterHandlers = Map.of(
                CompanyFilterType.JOINED_LAST_MONTH, () -> List.of(new Company(1L,"123", "Company A", LocalDate.now().minusMonths(1))),
                CompanyFilterType.WITH_TRANSFERS_LAST_MONTH, () -> List.of(new Company(2L, "456", "Company B", LocalDate.now().minusMonths(1)))
        );
        companyController = new CompanyController(companyService, transferService, companyDtoMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();
    }



    @Test
    void shouldRegisterCompanySuccessfully() throws Exception {
        CompanyRequestDto requestDto = new CompanyRequestDto("TAXIDA", "Company A", LocalDate.of(2025, 2, 28));
        Company company = new Company(1L,"TAXIDA", "Company A", LocalDate.of(2025, 2, 28));
        CompanyResponseDto responseDto = new CompanyResponseDto(LocalDate.of(2025, 2, 28), "Company A", "TAXIDA", 1L );

        when(companyDtoMapper.toDomain(any(CompanyRequestDto.class))).thenReturn(company);
        when(companyService.registerCompany(company)).thenReturn(company);
        when(companyDtoMapper.toResponseDto(any(Company.class))).thenReturn(responseDto);

        // 📌 Ejecutamos el request
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.taxId").value("TAXIDA"))
                .andExpect(jsonPath("$.businessName").value("Company A"));

        verify(companyService, times(1)).registerCompany(company);
    }

    @Test
    void shouldReturnCompaniesJoinedLastMonth() throws Exception {
        when(companyService.getCompaniesJoinedLastMonth()).thenReturn(filterHandlers.get(CompanyFilterType.JOINED_LAST_MONTH).get());
        when(companyDtoMapper.toResponseDto(any(Company.class))).thenReturn(new CompanyResponseDto(LocalDate.now().minusMonths(1),"Company A", "123",1L ));

        mockMvc.perform(get("/companies?filter=JOINED_LAST_MONTH"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].taxId").value("123"))
                .andExpect(jsonPath("$[0].businessName").value("Company A"));

        verify(companyService, times(1)).getCompaniesJoinedLastMonth();
    }

    @Test
    void shouldReturnCompaniesWithTransfersLastMonth() throws Exception {
        when(transferService.getCompaniesWithTransfersLastMonth()).thenReturn(filterHandlers.get(CompanyFilterType.WITH_TRANSFERS_LAST_MONTH).get());
        when(companyDtoMapper.toResponseDto(any(Company.class))).thenReturn(new CompanyResponseDto( LocalDate.now().minusMonths(1),  "Company B", "456", 1L));

        mockMvc.perform(get("/companies?filter=WITH_TRANSFERS_LAST_MONTH"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].taxId").value("456"))
                .andExpect(jsonPath("$[0].businessName").value("Company B"));

        verify(transferService, times(1)).getCompaniesWithTransfersLastMonth();
    }

    @Test
    void shouldReturnEmptyListWhenFilterIsInvalid() throws Exception {
        mockMvc.perform(get("/companies?filter=ASD"))
                .andExpect(status().isBadRequest());
    }
}
