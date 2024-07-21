package com.ocean.bank.client.service;

import com.ocean.bank.client.controller.dto.ClientInfo;
import com.ocean.bank.client.controller.dto.ClientRs;
import com.ocean.bank.client.repository.ClientRepository;
import com.ocean.bank.client.repository.entity.Client;
import com.ocean.bank.client.repository.entity.Status;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
@AllArgsConstructor
public class EntityMapper {
    private ClientRepository clientRepository;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final Random random = new Random();


    public Client toNewClientConverter(ClientInfo clientRq, Status status) {
        return Client.builder()
                .clientCode(generateClientCode())
                .address(clientRq.getAddress())
                .identificationDocument(clientRq.getIdentificationDocument())
                .firstName(clientRq.getFirstName())
                .lastName(clientRq.getLastName())
                .surname(clientRq.getSurname())
                .phoneNumber(clientRq.getPhoneNumber())
                .sensitiveData(clientRq.getSensitiveData())
                .status(status.getName())
                .createAt(LocalDate.now())
                .build();
    }

    public ClientRs toClientRs(Client client) {
        return ClientRs.builder()
                .clientCode(client.getClientCode())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .surname(client.getSurname())
                .status(client.getStatus())
                .build();
    }

    public ClientInfo toClientInfo(Client client) {
        return ClientInfo.builder()
                .address(client.getAddress())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .surname(client.getSurname())
                .identificationDocument(client.getIdentificationDocument())
                .phoneNumber(client.getPhoneNumber())
                .build();
    }

    private String generateClientCode() {
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            char randomChar;
            if (random.nextBoolean()) {
                randomChar = (char) ('0' + random.nextInt(10));
            } else {
                randomChar = (char) ('A' + random.nextInt(26));
            }
            randomString.append(randomChar);
        }
        if (clientRepository.findClientByClientCode(randomString.toString()) == null) {
            return randomString.toString();
        } else {
            return generateClientCode();
        }
    }

}
