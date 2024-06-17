package com.ocean.bank.account.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated
import jakarta.validation.constraints.NotEmpty

@Validated
@ConfigurationProperties("dictionaries")
data class DictionariesProperties(
    @field:NotEmpty
    val currency: Map<String, String>,
) {
    fun getCurrencyCodeByName(currencyName: String) = currency.entries
    .firstOrNull { it.value == currencyName }?.key ?: throw IllegalArgumentException("Currency name $currencyName is not found")

}