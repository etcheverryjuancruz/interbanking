package com.jetcheverry.interbanking.infrastructure.api.controller;

import com.jetcheverry.interbanking.domain.port.in.CompanyServicePort;
import com.jetcheverry.interbanking.infrastructure.api.dto.CompanyRequestDto;
import com.jetcheverry.interbanking.infrastructure.api.dto.CompanyResponseDto;
import com.jetcheverry.interbanking.infrastructure.api.mapper.CompanyDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyServicePort companyService;
    private final CompanyDtoMapper companyDtoMapper;

    public CompanyController(CompanyServicePort companyServicePort, CompanyDtoMapper companyDtoMapper) {
        this.companyService = companyServicePort;
        this.companyDtoMapper = companyDtoMapper;
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponseDto>> getCompanies(
            @RequestParam(value = "joined_after", required = false) String joinedAfter) {

        LocalDate date = (joinedAfter != null) ? LocalDate.parse(joinedAfter) : LocalDate.now().minusMonths(1);

        List<CompanyResponseDto> companies = companyService.getCompaniesJoinedLastMonth()
                .stream()
                .map(companyDtoMapper::toResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(companies);
    }

    @PostMapping
    public ResponseEntity<CompanyResponseDto> registerCompany(@Valid @RequestBody CompanyRequestDto companyRequestDto) {
        var savedCompany = companyService.registerCompany(companyDtoMapper.toDomain(companyRequestDto));
        return ResponseEntity.status(201).body(companyDtoMapper.toResponseDto(savedCompany));
    }
}
