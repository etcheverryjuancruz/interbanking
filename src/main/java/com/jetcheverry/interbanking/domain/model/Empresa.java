package com.jetcheverry.interbanking.domain.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Empresa {

    private String cuit;
    private String razonSocial;
    private LocalDate fechaAdhesion;
}
