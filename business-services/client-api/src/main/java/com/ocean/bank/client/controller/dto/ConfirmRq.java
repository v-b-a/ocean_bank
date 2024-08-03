package com.ocean.bank.client.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmRq {
    @NotBlank(message = "confirmCode is required")
    private String confirmCode;
}
