package com.jetcheverry.interbanking.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Transferencia {

    private Long id;
    private String idEmpresa;
    private BigDecimal importe;
    private String cuentaDebito;
    private String cuentaCredito;
    private LocalDateTime fechaTransferencia;
}
