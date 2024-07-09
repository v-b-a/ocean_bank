package com.ocean.bank.client.repository.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "client_status")
@Getter
public class Status {
    @Id
    private String id;
    @Field(name = "status_name")
    private String name;
    @Field(name = "status_description")
    private String description;
    @Field(name = "status_code")
    private Integer code;
}
