package com.jetcheverry.interbanking.domain.port.out;

import com.jetcheverry.interbanking.domain.model.Company;
import com.jetcheverry.interbanking.domain.model.Transfer;

import java.time.LocalDateTime;
import java.util.List;

public interface TransferRepositoryPort {

    Transfer save(Transfer transfer);

    List<Company> findCompaniesWithTransfersLastMonth(LocalDateTime date);
}
