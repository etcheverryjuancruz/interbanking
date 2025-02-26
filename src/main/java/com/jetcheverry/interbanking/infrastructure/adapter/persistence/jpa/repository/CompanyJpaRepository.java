package com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.repository;

import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CompanyJpaRepository extends JpaRepository<CompanyEntity, Long> {

    List<CompanyEntity> findByJoinDateAfter(LocalDate date);
}