package com.ocean.bank.account.service

import com.ocean.bank.account.configuration.ClientProperties
import com.ocean.bank.account.repository.entity.Client
import com.ocean.bank.account.repository.JDBCTemplateClientDao
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class ClientServiceTest : BaseServiceTest() {
    private val jdbcTemplateClientDao: JDBCTemplateClientDao = mock()
    private val clientProperties: ClientProperties = mock()

    private val clientService =
        ClientService(jdbcTemplateClientDao, clientProperties)


    @Test
    fun saveClientTest() {
        clientService.saveClient(CLIENT_CODE)
        verify(jdbcTemplateClientDao).saveClient(Client(code = CLIENT_CODE, statusId = clientProperties.initialStatus))
    }

    @Nested
    inner class UpdateClientTest {

        @Test
        fun updateClientStatusTest() {
            clientService.updateClientStatus(CLIENT_CODE, "BLOCKED")
            verify(jdbcTemplateClientDao, times(1)).updateClientWithStatusName(CLIENT_CODE, "BLOCKED")
        }
    }
}
