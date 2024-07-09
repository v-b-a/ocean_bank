package com.ocean.bank.client.repository.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Builder
@Document(collection = "clients-main")
@Data
public class Client {
    @Id
    private String clientId;
    private String clientCode;
    private String firstName;
    private String lastName;
    private String surname;
    private PersonalDocument personalDocument;
    private Address address;
    private SensitiveData sensitiveData;
    private String phoneNumber;
    private String clientStatus;
    @CreatedDate
    private LocalDate createAt;
}
