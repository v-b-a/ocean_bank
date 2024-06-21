package com.ocean.bank.client.client.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClientAccount {
    private String clientCode;
    private String accountNumber;
    private String accountName;
    private BigDecimal balance;
    private String currency;
}