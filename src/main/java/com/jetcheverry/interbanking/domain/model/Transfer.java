package com.jetcheverry.interbanking.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Transfer {

    private Long id;
    private String companyId;
    private BigDecimal amount;
    private String debitAccount;
    private String creditAccount;
    private LocalDateTime transferDate;
}
