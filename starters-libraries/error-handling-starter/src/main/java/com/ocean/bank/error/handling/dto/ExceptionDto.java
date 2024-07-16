package com.ocean.bank.error.handling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ExceptionDto {
    private String time;
    private String message;
    private String cause;
    private Integer status;
}
