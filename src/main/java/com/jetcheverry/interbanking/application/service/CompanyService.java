package com.jetcheverry.interbanking.application.service;

import com.jetcheverry.interbanking.domain.model.Company;
import com.jetcheverry.interbanking.domain.port.in.CompanyServicePort;
import com.jetcheverry.interbanking.domain.port.out.CompanyRepositoryPort;

import java.time.LocalDate;
import java.util.List;

public class CompanyService implements CompanyServicePort {

    private final CompanyRepositoryPort companyRepository;

    public CompanyService(CompanyRepositoryPort companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getCompaniesJoinedLastMonth() {
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        return companyRepository.findCompaniesJoinedLastMonth(lastMonth);
    }

    @Override
    public Company registerCompany(Company company) {
        company.setJoinDate(LocalDate.now());
        return companyRepository.save(company);
    }

}
