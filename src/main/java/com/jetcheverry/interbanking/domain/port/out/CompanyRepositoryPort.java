package com.jetcheverry.interbanking.domain.port.out;

import com.jetcheverry.interbanking.domain.model.Company;

import java.time.LocalDate;
import java.util.List;

public interface CompanyRepositoryPort {

    List<Company> findCompaniesJoinedLastMonth(LocalDate date);

    Company save(Company company);

    boolean existsByTaxId(String taxId);

}
