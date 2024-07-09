package com.ocean.bank.client.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class Address {
    private String registrationAddress;
    private String actualAddress;
    private LocalDate registrationDate;
}

