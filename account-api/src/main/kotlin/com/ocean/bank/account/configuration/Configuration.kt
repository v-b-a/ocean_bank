package com.ocean.bank.account.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource


@Configuration
class Configuration(
    val dataSourceProperties: DataSourceProperties
) {
    @Bean
    fun dataSource(): DataSource =
        DriverManagerDataSource(
            dataSourceProperties.url,
            dataSourceProperties.username,
            dataSourceProperties.password
        )

    @Bean
    fun jdbcTemplate(): JdbcTemplate = JdbcTemplate(dataSource())
}