package com.jetcheverry.interbanking.domain.exception;

public class CompanyNotFoundException extends RuntimeException {

    public CompanyNotFoundException(String companyId) {
        super("Company with id " + companyId + " not found");
    }
}
