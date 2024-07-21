package com.ocean.bank.client.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class IdentificationDocument {
    @NotBlank(message = "documentName is required")
    private String documentName;
    @NotBlank(message = "number is required")
    private String number;
    private String code;
    private String issueDate;
    private String issuerCode;
    private String issuerName;
}

