package com.ocean.bank.error.handling.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ExceptionDto {
    private String time;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cause;
    private Integer status;
}
