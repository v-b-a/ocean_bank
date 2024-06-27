package com.ocean.bank.error.handling.testEnvironment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

//@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
//@AllArgsConstructor
public class DemoDto {
    @JsonProperty
    @NotEmpty
    private String name;
    private Integer age;

    public DemoDto(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
