package com.ocean.bank.account.controller.dto

data class UpdateAccountStatusRs(
    val accountNumber: String,
    val clientCode: String,
    val newStatus: String,
)
