package com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa;

import com.jetcheverry.interbanking.domain.model.Company;
import com.jetcheverry.interbanking.domain.port.out.CompanyRepositoryPort;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.entity.CompanyEntity;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.mapper.CompanyMapperEntity;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.repository.CompanyJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepositoryAdapter  implements CompanyRepositoryPort {

    private final CompanyJpaRepository companyJpaRepository;
    private final CompanyMapperEntity companyMapperEntity;


    public CompanyRepositoryAdapter(CompanyJpaRepository companyJpaRepository, CompanyMapperEntity companyMapperEntity) {
        this.companyJpaRepository = companyJpaRepository;
        this.companyMapperEntity = companyMapperEntity;
    }
    @Override
    public List<Company> findCompaniesJoinedLastMonth(LocalDate date) {
        return companyJpaRepository.findByJoinDateAfter(date)
                .stream()
                .map(companyMapperEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Company save(Company company) {
        CompanyEntity entity = companyMapperEntity.toEntity(company);
        CompanyEntity savedEntity = companyJpaRepository.save(entity);
        return companyMapperEntity.toDomain(savedEntity);
    }
}
