package com.jetcheverry.interbanking.infrastructure.api.controller;

import com.jetcheverry.interbanking.domain.model.Company;
import com.jetcheverry.interbanking.domain.model.enums.CompanyFilterType;
import com.jetcheverry.interbanking.domain.port.in.CompanyServicePort;
import com.jetcheverry.interbanking.domain.port.in.TransferServicePort;
import com.jetcheverry.interbanking.infrastructure.api.dto.CompanyRequestDto;
import com.jetcheverry.interbanking.infrastructure.api.dto.CompanyResponseDto;
import com.jetcheverry.interbanking.infrastructure.api.mapper.CompanyDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
@Tag(name = "Company Controller", description = "Endpoints para gestionar empresas")
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

    @Operation(
            summary = "Registrar una nueva empresa",
            description = "Registra una empresa en el sistema. Si no se proporciona la fecha de adhesión (`joinDate`), se asignará la fecha actual.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Empresa registrada exitosamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CompanyResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @PostMapping
    public ResponseEntity<CompanyResponseDto> registerCompany(@Valid @RequestBody CompanyRequestDto companyRequestDto) {
        var savedCompany = companyService.registerCompany(companyDtoMapper.toDomain(companyRequestDto));
        return ResponseEntity.status(201).body(companyDtoMapper.toResponseDto(savedCompany));
    }


    @Operation(
            summary = "Obtener empresas filtradas",
            description = "Obtiene una lista de empresas basadas en el filtro seleccionado. Los filtros disponibles son: " +
                    "`JOINED_LAST_MONTH` (Empresas adheridas el último mes) y `WITH_TRANSFERS_LAST_MONTH` " +
                    "(Empresas que realizaron transferencias en el último mes).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de empresas obtenida exitosamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CompanyResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Filtro inválido"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @GetMapping
    public ResponseEntity<List<CompanyResponseDto>> getCompanies(
            @Parameter(description = "Tipo de filtro para obtener empresas", example = "JOINED_LAST_MONTH")
            @RequestParam("filter") CompanyFilterType filterType) {

        List<Company> companies = filterHandlers.getOrDefault(filterType, Collections::emptyList).get();
        List<CompanyResponseDto> response = companies.stream()
                .map(companyDtoMapper::toResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
