package com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa;

import com.jetcheverry.interbanking.domain.model.Company;
import com.jetcheverry.interbanking.domain.port.out.CompanyRepositoryPort;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.entity.CompanyEntity;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.mapper.CompanyEntityMapper;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.repository.CompanyJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepositoryAdapter  implements CompanyRepositoryPort {

    private final CompanyJpaRepository companyJpaRepository;
    private final CompanyEntityMapper companyEntityMapper;

    public CompanyRepositoryAdapter(CompanyJpaRepository companyJpaRepository, CompanyEntityMapper companyEntityMapper) {
        this.companyJpaRepository = companyJpaRepository;
        this.companyEntityMapper = companyEntityMapper;
    }
    @Override
    public List<Company> findCompaniesJoinedLastMonth(LocalDate date) {
        return companyJpaRepository.findByJoinDateAfter(date)
                .stream()
                .map(companyEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Company save(Company company) {
        CompanyEntity entity = companyEntityMapper.toEntity(company);
        CompanyEntity savedEntity = companyJpaRepository.save(entity);
        return companyEntityMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByTaxId(String taxId) {
        return companyJpaRepository.existsByTaxId(taxId);
    }
}