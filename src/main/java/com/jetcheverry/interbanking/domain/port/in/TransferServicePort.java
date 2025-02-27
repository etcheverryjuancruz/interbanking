package com.jetcheverry.interbanking.domain.port.in;

import com.jetcheverry.interbanking.domain.model.Company;
import com.jetcheverry.interbanking.domain.model.Transfer;

import java.time.LocalDateTime;
import java.util.List;

public interface TransferServicePort {

    Transfer registerTransfer(Transfer transfer);

    List<Company> getCompaniesWithTransfersLastMonth();
}
