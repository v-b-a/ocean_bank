package com.ocean.bank.client.service;

import com.ocean.bank.client.controller.dto.ClientInfo;
import com.ocean.bank.client.controller.dto.ClientRs;
import com.ocean.bank.client.repository.ClientRepository;
import com.ocean.bank.client.repository.entity.Address;
import com.ocean.bank.client.repository.entity.Client;
import com.ocean.bank.client.repository.entity.PersonalDocument;
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
        Address clientAddress = Address.builder()
                .registrationAddress(clientRq.getAddress().getRegistrationAddress())
                .actualAddress(clientRq.getAddress().getActualAddress())
                .registrationDate(clientRq.getAddress().getRegistrationDate())
                .build();
        PersonalDocument clientPersonalDocument = PersonalDocument.builder()
                .documentName(clientRq.getPersonalDocument().getDocumentName())
                .number(clientRq.getPersonalDocument().getNumber())
                .code(clientRq.getPersonalDocument().getCode())
                .issueDate(clientRq.getPersonalDocument().getIssueDate())
                .issuerCode(clientRq.getPersonalDocument().getIssuerCode())
                .issuerName(clientRq.getPersonalDocument().getIssuerName())
                .build();

        return Client.builder()
                .clientCode(generateClientCode())
                .address(clientAddress)
                .personalDocument(clientPersonalDocument)
                .firstName(clientRq.getFirstName())
                .lastName(clientRq.getLastName())
                .surname(clientRq.getSurname())
                .phoneNumber(clientRq.getPhoneNumber())
                .sensitiveData(clientRq.getSensitiveData())
                .clientStatus(status.getName())
                .createAt(LocalDate.now())
                .build();
    }

    public ClientRs toClientRs(Client client) {
        return ClientRs.builder()
                .clientCode(client.getClientCode())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .surname(client.getSurname())
                .status(client.getClientStatus())
                .build();
    }

    public ClientInfo toClientInfo(Client client) {
        return ClientInfo.builder()
                .address(client.getAddress())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .surname(client.getSurname())
                .personalDocument(client.getPersonalDocument())
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
