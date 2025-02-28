package com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa;

import com.jetcheverry.interbanking.domain.model.Company;
import com.jetcheverry.interbanking.domain.model.Transfer;
import com.jetcheverry.interbanking.domain.port.out.TransferRepositoryPort;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.entity.CompanyEntity;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.entity.TransferEntity;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.mapper.CompanyEntityMapper;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.mapper.TransferEntityMapper;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.repository.CompanyJpaRepository;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.repository.TransferJpaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public class TransferRepositoryAdapter implements TransferRepositoryPort {

    private final TransferJpaRepository transferJpaRepository;

    private final CompanyJpaRepository companyJpaRepository;

    private final CompanyEntityMapper companyEntityMapper;

    private final TransferEntityMapper transferEntityMapper;

    public TransferRepositoryAdapter(TransferJpaRepository transferJpaRepository, CompanyJpaRepository companyJpaRepository, CompanyEntityMapper companyEntityMapper, TransferEntityMapper transferEntityMapper) {
        this.transferJpaRepository = transferJpaRepository;
        this.companyJpaRepository = companyJpaRepository;
        this.companyEntityMapper = companyEntityMapper;
        this.transferEntityMapper = transferEntityMapper;
    }


    @Override
    public Transfer save(Transfer transfer) {
        Optional<CompanyEntity> companyEntityOpt = companyJpaRepository.findByTaxId(transfer.getCompanyTaxId());

        if (companyEntityOpt.isEmpty()) {
            throw new IllegalArgumentException("Company with Tax ID " + transfer.getCompanyTaxId() + " not found.");
        }

        CompanyEntity companyEntity = companyEntityOpt.get();

        TransferEntity savedEntity = transferJpaRepository.save(
                transferEntityMapper.toEntity(transfer, companyEntity)
        );

        return transferEntityMapper.toDomain(savedEntity);
    }

    @Override
    public List<Company> findCompaniesWithTransfersLastMonth(LocalDateTime lastMonth) {
        return transferJpaRepository.findDistinctByTransferDateAfter(lastMonth).stream()
                .map(TransferEntity::getCompany) // Obtener CompanyEntity
                .distinct()
                .map(companyEntityMapper::toDomain) // Convertir a Company
                .collect(Collectors.toList());
    }

}
