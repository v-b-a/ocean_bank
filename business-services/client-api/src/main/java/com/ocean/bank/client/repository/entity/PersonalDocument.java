package com.ocean.bank.client.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class PersonalDocument {
    private String documentName;
    private String number;
    private String code;
    private LocalDate issueDate;
    private String issuerCode;
    private String issuerName;
}

