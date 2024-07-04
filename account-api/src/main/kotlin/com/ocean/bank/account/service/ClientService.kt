package com.ocean.bank.account.service

import com.ocean.bank.account.configuration.ClientProperties
import com.ocean.bank.account.repository.entity.Client
import com.ocean.bank.account.repository.JDBCTemplateClientDao
import org.springframework.stereotype.Service

@Service
class ClientService(
    val jdbcTemplateClientDao: JDBCTemplateClientDao,
    val clientProperties: ClientProperties,
) {
    fun saveClient(clientCode: String) {
        jdbcTemplateClientDao.save(Client(code = clientCode, statusId = clientProperties.initialStatus))
    }

    fun updateClientStatus(clientCode: String, statusName: String) {
        jdbcTemplateClientDao.updateClientWithStatusName(clientCode, statusName)
    }
}