package com.ocean.bank.account.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated
import jakarta.validation.constraints.NotEmpty

@ConfigurationProperties("spring.datasource")
@Validated
data class DataSourceProperties(
    @field:NotEmpty val url: String,
    @field:NotEmpty val username: String,
    @field:NotEmpty val password: String,
    val poolSize: Int?,
)