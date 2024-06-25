package com.ocean.bank.error.handling.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Data
@Setter
@Getter
@NoArgsConstructor
public class ExceptionDto {
    private String time;
    private String message;
//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cause;
    private Integer status;
}
