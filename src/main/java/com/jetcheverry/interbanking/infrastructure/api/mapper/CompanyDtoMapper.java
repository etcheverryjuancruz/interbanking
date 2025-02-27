package com.jetcheverry.interbanking.infrastructure.api.mapper;


import com.jetcheverry.interbanking.domain.model.Company;
import com.jetcheverry.interbanking.infrastructure.api.dto.CompanyRequestDto;
import com.jetcheverry.interbanking.infrastructure.api.dto.CompanyResponseDto;
import org.springframework.stereotype.Component;

@Component
public class CompanyDtoMapper {

    public Company toDomain(CompanyRequestDto dto) {
        if (dto == null) {
            return null;
        }

        return new Company(null, dto.getTaxId(), dto.getBusinessName(), null);
    }

    public CompanyResponseDto toResponseDto(Company company) {
        if (company == null) {
            return null;
        }
        return new CompanyResponseDto(company.getJoinDate(), company.getBusinessName(), company.getTaxId(), company.getId());
    }
}
