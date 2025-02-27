package com.jetcheverry.interbanking.config;

import com.jetcheverry.interbanking.application.service.CompanyService;
import com.jetcheverry.interbanking.domain.port.in.CompanyServicePort;
import com.jetcheverry.interbanking.domain.port.out.CompanyRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public CompanyServicePort companyService(CompanyRepositoryPort companyRepository) {
        return new CompanyService(companyRepository);
    }
}
