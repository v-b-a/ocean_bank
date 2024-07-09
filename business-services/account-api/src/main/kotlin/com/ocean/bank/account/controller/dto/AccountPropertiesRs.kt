package com.ocean.bank.account.controller.dto

data class AccountPropertiesRs(
    val currencies: Map<String, String>,
    val accountTypes: Set<String>
)