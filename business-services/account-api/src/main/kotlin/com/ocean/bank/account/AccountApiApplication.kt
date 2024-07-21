package com.ocean.bank.account

import com.ocean.bank.account.configuration.ClientProperties
import com.ocean.bank.account.configuration.DataSourceProperties
import com.ocean.bank.account.configuration.DictionariesProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableConfigurationProperties(
	DictionariesProperties::class,
	DataSourceProperties::class,
	ClientProperties::class
)
@EnableDiscoveryClient
class AccountApiApplication
fun main(args: Array<String>) {
	runApplication<AccountApiApplication>(*args)
}

