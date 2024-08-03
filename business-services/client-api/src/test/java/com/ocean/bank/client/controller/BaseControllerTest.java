package com.ocean.bank.client.controller;

import com.ocean.bank.client.controller.dto.ClientInfo;
import com.ocean.bank.client.repository.ClientRepository;
import com.ocean.bank.client.repository.StatusRepository;
import com.ocean.bank.client.repository.entity.Address;
import com.ocean.bank.client.repository.entity.Client;
import com.ocean.bank.client.repository.entity.IdentificationDocument;
import com.ocean.bank.client.repository.entity.SensitiveData;
import com.ocean.bank.client.repository.entity.Status;
import com.ocean.bank.test.lib.BaseMongoTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BaseControllerTest extends BaseMongoTest {
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    ClientRepository clientRepository;

    final String TEST_CLIENT_CODE = "F1G1D";
    final String TEST_CLIENT_FIRST_NAME = "John";

    ClientInfo getTestClientInfo() {
        return  ClientInfo.builder()
                .firstName(TEST_CLIENT_FIRST_NAME)
                .lastName("Doe")
                .identificationDocument(
                        IdentificationDocument.builder().documentName("passport").number("123456").code("123456").build())
                .address(Address.builder().registrationAddress("registrationAddress").build())
                .sensitiveData(SensitiveData.builder().passphrase("passphrase").password("password").build())
                .phoneNumber("123456")
                .build();
    }

    Client getTestClient() {
        return Client.builder()
                .clientId("1")
                .clientCode(TEST_CLIENT_CODE)
                .firstName(TEST_CLIENT_FIRST_NAME)
                .lastName("Doe")
                .identificationDocument(
                        IdentificationDocument.builder().documentName("passport").number("123456").code("123456").build())
                .address(Address.builder().registrationAddress("registrationAddress").build())
                .sensitiveData(SensitiveData.builder().passphrase("passphrase").password("password").build())
                .phoneNumber("123456")
                .status("UNVERIFIED")
                .build();
    }

    @BeforeEach
    public void setup() {
        statusRepository.saveAll(
                List.of(
                        new Status("id1", "UNVERIFIED", "Pending client approval status", 1),
                        new Status("id2", "ACTIVE", "Client active status", 2)
                )
        );
    }
}
