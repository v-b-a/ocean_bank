package com.ocean.bank.error.handling.testEnvirament.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DemoDto {
    @JsonProperty
    @NotEmpty
    private String name;

    public DemoDto(String name) {
        this.name = name;
    }
}
