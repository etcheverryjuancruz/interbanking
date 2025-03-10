package com.jetcheverry.interbanking.domain.exception;

public class CompanyAlreadyExistsException extends RuntimeException {

    public CompanyAlreadyExistsException(String taxId) {
        super("Company with id " + taxId + " already exists");
    }
}
