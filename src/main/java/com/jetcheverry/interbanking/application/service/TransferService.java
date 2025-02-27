package com.jetcheverry.interbanking.application.service;

import com.jetcheverry.interbanking.domain.model.Company;
import com.jetcheverry.interbanking.domain.model.Transfer;
import com.jetcheverry.interbanking.domain.port.in.TransferServicePort;
import com.jetcheverry.interbanking.domain.port.out.TransferRepositoryPort;

import java.time.LocalDateTime;
import java.util.List;

public class TransferService implements TransferServicePort {

    private final TransferRepositoryPort transferRepository;

    public TransferService(TransferRepositoryPort transferRepository) {
        this.transferRepository = transferRepository;
    }

    @Override
    public Transfer registerTransfer(Transfer transfer) {
        if (transfer.getTransferDate() == null) {
            transfer.setTransferDate(LocalDateTime.now());
        }

        return transferRepository.save(transfer);
    }

    @Override
    public List<Company> getCompaniesWithTransfersLastMonth() {
        LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);

        return  transferRepository.findCompaniesWithTransfersLastMonth(lastMonth);
    }
}
