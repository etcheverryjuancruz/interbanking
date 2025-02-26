package com.jetcheverry.interbanking.domain.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Company {

    private String taxId;
    private String businessName;
    private LocalDate joinDate;
}
