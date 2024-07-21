package com.ocean.bank.account.controller

import com.ocean.bank.account.BaseTest.Companion.CLIENT_CODE
import com.ocean.bank.account.BaseTest.Companion.CLIENT_ID
import com.ocean.bank.account.repository.entity.Client
import com.ocean.bank.test.lib.BaseE2ETest
import org.springframework.test.context.jdbc.Sql

@Sql("/scripts/setUpDatabase.sql", "/scripts/insertMockData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql("/scripts/cleanDatabase.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql("/scripts/cleanMockData.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
abstract class BaseControllerTest : BaseE2ETest() {

    fun createClient(clientId: Long = CLIENT_ID, clientCode: String = CLIENT_CODE) =
        Client(
            clientId = clientId,
            code = clientCode,
            statusId = 1
        )
}

