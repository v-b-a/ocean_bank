package com.ocean.bank.account.repository.entity

import java.time.Instant

data class AccountStatusesHistory(
    val id: Int = 0,
    val accountId: Int,
    val previousStatusCode: String,
    val newStatusCode: String,
    val changeDateTime: Instant
)
