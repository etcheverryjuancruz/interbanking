package com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.repository;

import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.entity.CompanyEntity;
import com.jetcheverry.interbanking.infrastructure.adapter.persistence.jpa.entity.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransferJpaRepository  extends JpaRepository<TransferEntity, Long> {

    List<TransferEntity> findDistinctByTransferDateAfter(LocalDateTime lastMonth);
}
