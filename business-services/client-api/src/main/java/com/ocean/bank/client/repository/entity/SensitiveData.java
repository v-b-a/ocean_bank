package com.ocean.bank.client.repository.entity;

import lombok.Data;

@Data
public class SensitiveData {
    private String password;
    private String passphrase;
}
