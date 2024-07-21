package com.ocean.bank.client.controller.dto;

import com.ocean.bank.client.repository.entity.Address;
import com.ocean.bank.client.repository.entity.IdentificationDocument;
import com.ocean.bank.client.repository.entity.SensitiveData;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClientInfo {
    @NotBlank(message = "firstName is required")
    private String firstName;
    @NotBlank(message = "lastName is required")
    private String lastName;
    private String surname;
    @NotNull(message = "identificationDocument is required")
    private IdentificationDocument identificationDocument;
    @NotNull(message = "address is required")
    private Address address;
    @NotNull(message = "sensitiveData is required")
    private SensitiveData sensitiveData;
    @NotBlank(message = "phoneNumber is required")
    private String phoneNumber;
}

