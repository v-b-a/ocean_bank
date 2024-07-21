package com.ocean.bank.client.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "client_status")
@Data
public class Status {
    @Id
    private String id;
    private String name;
    private String description;
    private Integer code;
}
