package com.ocean.bank.account.controller

import com.ocean.bank.account.controller.dto.CreateAccountRq
import com.ocean.bank.account.repository.JDBCTemplateClientDao
import com.ocean.bank.account.repository.JdbcAccountDao
import com.ocean.bank.account.repository.entity.Account
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal
import java.time.Instant

class AccountControllerTest : BaseIntegrationTest() {

    @Autowired
    lateinit var jdbcAccountDao: JdbcAccountDao
    @Autowired
    private lateinit var clientRepository: JDBCTemplateClientDao

    @Test
    fun createAccountTest() {
        clientRepository.saveClient(createClient())

        val response = webTestClient.post()
            .uri { it.path("").build() }
            .header("client-code", CLIENT_CODE)
            .bodyValue(CreateAccountRq(USER_ACCOUNT_NAME, PAYMENT_TYPE, USD))
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .consumeWith(::print)

        assertAll(
            { assertThat(response.jsonPath("$.clientCode").isEqualTo(CLIENT_CODE)) },
            { assertThat(response.jsonPath("$.currency").isEqualTo(USD)) },
            { assertThat(response.jsonPath("$.accountNumber").isNotEmpty) }
        )

        /*
        val countRowsInTableWhere = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "accounts", "account_number = '$ACCOUNT_NUMBER'")
        assertThat(countRowsInTableWhere).isEqualTo(1)
         */

//         FIXME: данные вроде сохраняются, но достать их снова я не могу
//        val account = jdbcAccountDao.findAccountByAccountNumber(ACCOUNT_NUMBER)
//        assertAll(
//            { assertThat(account).isNotNull },
//            { assertThat(account?.accountNumber).isEqualTo(ACCOUNT_NUMBER) },
//            { assertThat(account?.accountName).isEqualTo(USER_ACCOUNT_NAME) },
//            { assertThat(account?.accountType).isEqualTo(PAYMENT_TYPE) },
//            { assertThat(account?.currencyCode).isEqualTo(USD) }
//        )

    }

    @Test
    fun getAccountInfoTest() {
        clientRepository.saveClient(createClient())
        jdbcAccountDao.save(createAccount(), createClient())

        val response = webTestClient.get()
            .uri { it.path("/$ACCOUNT_NUMBER").build() }
            .header("client-code", CLIENT_CODE)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .consumeWith(::print)

        assertAll(
            { assertThat(response.jsonPath("$.clientCode").isEqualTo(CLIENT_CODE)) },
            { assertThat(response.jsonPath("$.accountNumber").isEqualTo(ACCOUNT_NUMBER)) },
            { assertThat(response.jsonPath("$.accountName").isEqualTo(USER_ACCOUNT_NAME)) }
        )
    }

    @Test
    fun getAccountsTest() {
        clientRepository.saveClient(createClient())
        jdbcAccountDao.save(createAccount(), createClient())
        jdbcAccountDao.save(createAccount(ACCOUNT_NUMBER_2, USER_ACCOUNT_NAME_2), createClient())

        val response = webTestClient.get()
            .uri { it.path("").build() }
            .header("client-code", CLIENT_CODE)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .consumeWith(::print)

        assertAll(
            { assertThat(response.jsonPath("$[0].accountNumber").isEqualTo(ACCOUNT_NUMBER)) },
            { assertThat(response.jsonPath("$[1].accountNumber").isEqualTo(ACCOUNT_NUMBER_2)) },
            { assertThat(response.jsonPath("$[0].accountName").isEqualTo(USER_ACCOUNT_NAME)) },
            { assertThat(response.jsonPath("$[1].accountName").isEqualTo(USER_ACCOUNT_NAME_2)) }
        )
    }

    @Test
    fun updateAccountStatusTest() {
        clientRepository.saveClient(createClient())
        jdbcAccountDao.save(createAccount(), createClient())

        val response = webTestClient.put()
            .uri { it.path("/$ACCOUNT_NUMBER").queryParam("statusCode", ACCOUNT_BLOCKED_STATUS).build() }
            .header("client-code", CLIENT_CODE)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .consumeWith(::print)

        assertAll(
            { assertThat(response.jsonPath("$.clientCode").isEqualTo(CLIENT_CODE)) },
            { assertThat(response.jsonPath("$.accountNumber").isEqualTo(ACCOUNT_NUMBER)) },
            { assertThat(response.jsonPath("$.newStatus").isEqualTo(ACCOUNT_BLOCKED_STATUS)) }
        )
    }

    private fun createAccount(accountNumber: String = ACCOUNT_NUMBER, accountName: String = USER_ACCOUNT_NAME) =
        Account(
            clientId = 1,
            accountNumber = accountNumber,
            accountType = PAYMENT_TYPE,
            currencyCode = USD,
            accountName = accountName,
            accountStatus = ACCOUNT_ACTIVE_STATUS,
            createAt = Instant.now(),
            previousDayBalance = BigDecimal.ZERO
        )
}