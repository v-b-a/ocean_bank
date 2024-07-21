package com.ocean.bank.client.repository.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SensitiveData {
    private String password;
    private String passphrase;
}
