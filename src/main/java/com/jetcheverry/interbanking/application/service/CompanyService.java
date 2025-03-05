package com.jetcheverry.interbanking.application.service;

import com.jetcheverry.interbanking.domain.model.Company;
import com.jetcheverry.interbanking.domain.exception.CompanyAlreadyExistsException;
import com.jetcheverry.interbanking.domain.port.in.CompanyServicePort;
import com.jetcheverry.interbanking.domain.port.out.CompanyRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

public class CompanyService implements CompanyServicePort {

    private final CompanyRepositoryPort companyRepository;

    private  final Logger logger = LoggerFactory.getLogger(CompanyService.class);

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
        boolean companyAlreadyExists = companyRepository.existsByTaxId(company.getTaxId());
        if (companyAlreadyExists) {
            throw  new CompanyAlreadyExistsException(company.getTaxId());
        }

        if (company.getJoinDate() == null) {
            company.setJoinDate(LocalDate.now());
        }

        return companyRepository.save(company);
    }

}
