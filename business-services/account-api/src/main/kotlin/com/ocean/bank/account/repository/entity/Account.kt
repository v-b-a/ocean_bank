package com.ocean.bank.account.repository.entity

import java.math.BigDecimal
import java.time.Instant

data class Account(
    val accountId: Long = 0,
    val clientId: Long,
    val accountNumber: String,
    val accountName: String,
    val currencyCode: String,
    val accountType: String,
    val accountStatus: String,
    val previousDayBalance: BigDecimal,
    var createAt: Instant
)