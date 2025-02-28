package com.jetcheverry.interbanking.infrastructure.api.controller;

import com.jetcheverry.interbanking.domain.model.Company;
import com.jetcheverry.interbanking.domain.model.enums.CompanyFilterType;
import com.jetcheverry.interbanking.domain.port.in.CompanyServicePort;
import com.jetcheverry.interbanking.domain.port.in.TransferServicePort;
import com.jetcheverry.interbanking.infrastructure.api.dto.CompanyRequestDto;
import com.jetcheverry.interbanking.infrastructure.api.dto.CompanyResponseDto;
import com.jetcheverry.interbanking.infrastructure.api.mapper.CompanyDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyServicePort companyService;
    private final TransferServicePort transferService;
    private final CompanyDtoMapper companyDtoMapper;
    private final Map<CompanyFilterType, Supplier<List<Company>>> filterHandlers;


    public CompanyController(CompanyServicePort companyServicePort, TransferServicePort transferService, CompanyDtoMapper companyDtoMapper) {
        this.companyService = companyServicePort;
        this.transferService = transferService;
        this.companyDtoMapper = companyDtoMapper;

        this.filterHandlers = Map.of(
                CompanyFilterType.JOINED_LAST_MONTH, companyService::getCompaniesJoinedLastMonth,
                CompanyFilterType.WITH_TRANSFERS_LAST_MONTH, transferService::getCompaniesWithTransfersLastMonth
        );
    }

    @PostMapping
    public ResponseEntity<CompanyResponseDto> registerCompany(@Valid @RequestBody CompanyRequestDto companyRequestDto) {
        var savedCompany = companyService.registerCompany(companyDtoMapper.toDomain(companyRequestDto));
        return ResponseEntity.status(201).body(companyDtoMapper.toResponseDto(savedCompany));
    }


    @GetMapping
    public ResponseEntity<List<CompanyResponseDto>> getCompanies(
            @RequestParam("filter") CompanyFilterType filterType) {

        List<Company> companies = filterHandlers.getOrDefault(filterType, Collections::emptyList).get();
        List<CompanyResponseDto> response = companies.stream()
                .map(companyDtoMapper::toResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
