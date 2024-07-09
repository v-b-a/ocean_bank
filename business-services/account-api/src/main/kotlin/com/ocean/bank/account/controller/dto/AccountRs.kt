package com.ocean.bank.account.controller.dto

import java.math.BigDecimal

data class AccountRs(
    val clientCode: String,
    val accountNumber: String,
    val accountName: String,
    val balance: BigDecimal,
    val currency: String,
    val status: String,
)