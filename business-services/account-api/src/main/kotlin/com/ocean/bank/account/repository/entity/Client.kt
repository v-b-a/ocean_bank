package com.ocean.bank.account.repository.entity

data class Client(
    val clientId: Long = 0,
    val code: String,
    val statusId: Long
)
