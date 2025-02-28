package com.jetcheverry.interbanking.infrastructure.api.controller;

import com.jetcheverry.interbanking.domain.model.Transfer;
import com.jetcheverry.interbanking.domain.port.in.TransferServicePort;
import com.jetcheverry.interbanking.infrastructure.api.dto.TransferRequestDto;
import com.jetcheverry.interbanking.infrastructure.api.dto.TransferResponseDto;
import com.jetcheverry.interbanking.infrastructure.api.mapper.TransferDTOMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfers")
public class TransferController {

    private final TransferServicePort transferService;
    private final TransferDTOMapper transferDtoMapper;

    public TransferController(TransferServicePort transferService, TransferDTOMapper transferDtoMapper) {
        this.transferService = transferService;
        this.transferDtoMapper = transferDtoMapper;
    }

    @PostMapping
    public ResponseEntity<TransferResponseDto> registerTransfer(@Valid @RequestBody TransferRequestDto requestDto) {
        Transfer transfer = transferDtoMapper.toDomain(requestDto);
        Transfer savedTransfer = transferService.registerTransfer(transfer);
        return ResponseEntity.status(HttpStatus.CREATED).body(transferDtoMapper.toResponseDto(savedTransfer));
    }
}
