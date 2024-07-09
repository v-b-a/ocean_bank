package com.ocean.bank.account.controller.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

data class ExceptionDto(
    val time: String,
    val message: String?,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val cause: String?,
    val status: Int,
)
