package com.jetcheverry.interbanking.config;

import com.jetcheverry.interbanking.application.service.CompanyService;
import com.jetcheverry.interbanking.application.service.TransferService;
import com.jetcheverry.interbanking.domain.port.in.CompanyServicePort;
import com.jetcheverry.interbanking.domain.port.in.TransferServicePort;
import com.jetcheverry.interbanking.domain.port.out.CompanyRepositoryPort;
import com.jetcheverry.interbanking.domain.port.out.TransferRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public CompanyServicePort companyService(CompanyRepositoryPort companyRepository) {
        return new CompanyService(companyRepository);
    }

    @Bean
    public TransferServicePort transferService(TransferRepositoryPort transferRepository) {
        return new TransferService(transferRepository);
    }
}
