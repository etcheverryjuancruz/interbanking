package com.jetcheverry.interbanking.domain.port.in;

import com.jetcheverry.interbanking.domain.model.Company;

import java.util.List;

public interface CompanyServicePort {

    List<Company> getCompaniesJoinedLastMonth();

    Company registerCompany(Company company);


}
