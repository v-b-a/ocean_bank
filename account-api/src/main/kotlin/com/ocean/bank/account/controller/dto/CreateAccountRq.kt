package com.ocean.bank.account.controller.dto

import jakarta.validation.constraints.NotBlank

data class CreateAccountRq(
    val accountName: String?,
    @field:NotBlank(message = "accountType is required")
    val accountType: String,
    @field:NotBlank(message = "currency is required")
    val currency: String
)
