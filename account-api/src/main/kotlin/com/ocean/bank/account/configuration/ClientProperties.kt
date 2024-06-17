package com.ocean.bank.account.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("clients-properties")
data class ClientProperties(
    val initialStatus: Long
)
