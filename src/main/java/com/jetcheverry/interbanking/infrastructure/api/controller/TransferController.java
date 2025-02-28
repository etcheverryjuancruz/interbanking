package com.jetcheverry.interbanking.infrastructure.api.controller;

import com.jetcheverry.interbanking.domain.model.Transfer;
import com.jetcheverry.interbanking.domain.port.in.TransferServicePort;
import com.jetcheverry.interbanking.infrastructure.api.dto.TransferRequestDto;
import com.jetcheverry.interbanking.infrastructure.api.dto.TransferResponseDto;
import com.jetcheverry.interbanking.infrastructure.api.mapper.TransferDTOMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfers")
@Tag(name = "Transfer Controller", description = "Endpoints para gestionar transferencias")
public class TransferController {

    private final TransferServicePort transferService;
    private final TransferDTOMapper transferDtoMapper;

    public TransferController(TransferServicePort transferService, TransferDTOMapper transferDtoMapper) {
        this.transferService = transferService;
        this.transferDtoMapper = transferDtoMapper;
    }

    @Operation(
            summary = "Registrar una nueva transferencia",
            description = "Permite registrar una transferencia entre cuentas bancarias. " +
                    "Si no se especifica `transferDate`, se asignará la fecha actual."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Transferencia registrada exitosamente",
                    content = @Content(schema = @Schema(implementation = TransferResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<TransferResponseDto> registerTransfer(@Valid @RequestBody TransferRequestDto requestDto) {
        Transfer transfer = transferDtoMapper.toDomain(requestDto);
        Transfer savedTransfer = transferService.registerTransfer(transfer);
        return ResponseEntity.status(HttpStatus.CREATED).body(transferDtoMapper.toResponseDto(savedTransfer));
    }
}
