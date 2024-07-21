package com.ocean.bank.client.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class Address {
    private String registrationAddress;
    @NotBlank(message = "actualAddress is required")
    private String actualAddress;
    private String registrationDate;
}

