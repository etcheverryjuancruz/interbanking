package com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.mapper;

import com.jetcheverry.interbanking.domain.model.Company;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.entity.CompanyEntity;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapperEntity {

    public CompanyEntity toEntity(Company company) {
        if(company == null) {
            return null;
        }

        CompanyEntity entity = new CompanyEntity(
                company.getTaxId(),
                company.getBusinessName(),
                company.getJoinDate()
        );
        return entity;
    }

    public Company toDomain(CompanyEntity entity) {
        if(entity == null) {
            return null;
        }
        return new Company(
                entity.getId(),
                entity.getTaxId(),
                entity.getBusinessName(),
                entity.getJoinDate()
        );
    }
}
