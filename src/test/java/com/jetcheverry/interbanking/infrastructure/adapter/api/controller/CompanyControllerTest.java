package com.jetcheverry.interbanking.infrastructure.adapter.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jetcheverry.interbanking.domain.model.Company;
import com.jetcheverry.interbanking.domain.port.in.CompanyServicePort;
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
    private CompanyDtoMapper companyDtoMapper;

    @InjectMocks
    private CompanyController companyController;

    private ObjectMapper objectMapper;
    private CompanyRequestDto requestDto;
    private CompanyResponseDto responseDto;
    private Company company;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();

        requestDto = new CompanyRequestDto("TAXIDA", "Company A");
        responseDto = new CompanyResponseDto(LocalDate.of(2024, 2, 1),  "Company A", "TAXIDA",1L );
        company = new Company(1L, "TAXIDA", "Company A", LocalDate.of(2024, 2, 1));
    }

    @Test
    void shouldReturnCompaniesJoinedLastMonth() throws Exception {
        List<Company> companies = List.of(company);
        List<CompanyResponseDto> companyDtos = List.of(responseDto);

        when(companyService.getCompaniesJoinedLastMonth()).thenReturn(companies);
        when(companyDtoMapper.toResponseDto(company)).thenReturn(responseDto);

        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].taxId").value("TAXIDA"))
                .andExpect(jsonPath("$[0].businessName").value("Company A"));

        verify(companyService, times(1)).getCompaniesJoinedLastMonth();
    }

    @Test
    void shouldReturnCompaniesFilteredByDate() throws Exception {
        String date = "2024-01-01";

        List<Company> companies = List.of(company);
        List<CompanyResponseDto> companyDtos = List.of(responseDto);

        when(companyService.getCompaniesJoinedLastMonth()).thenReturn(companies);
        when(companyDtoMapper.toResponseDto(company)).thenReturn(responseDto);

        mockMvc.perform(get("/companies")
                        .param("joined_after", date))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].taxId").value("TAXIDA"))
                .andExpect(jsonPath("$[0].businessName").value("Company A"));

        verify(companyService, times(1)).getCompaniesJoinedLastMonth();
    }

    @Test
    void shouldRegisterCompanySuccessfully() throws Exception {
        when(companyDtoMapper.toDomain(any(CompanyRequestDto.class))).thenReturn(company);
        when(companyService.registerCompany(company)).thenReturn(company);

        doReturn(responseDto).when(companyDtoMapper).toResponseDto(any(Company.class));

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
    void shouldReturnBadRequestWhenTaxIdIsEmpty() throws Exception {
        CompanyRequestDto invalidRequest = new CompanyRequestDto("", "Company A");

        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

}
