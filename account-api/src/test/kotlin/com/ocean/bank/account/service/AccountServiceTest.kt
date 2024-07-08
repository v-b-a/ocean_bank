package com.ocean.bank.account.service

import com.ocean.bank.account.controller.dto.CreateAccountRq
import com.ocean.bank.account.repository.entity.Account
import com.ocean.bank.account.repository.entity.AccountType
import com.ocean.bank.account.repository.entity.Client
import com.ocean.bank.account.repository.JdbcAccountDao
import com.ocean.bank.account.repository.JdbcTemplateClientDao
import com.ocean.bank.error.handling.excepiton.NotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import java.time.Instant


class AccountServiceTest : BaseServiceTest() {
    private val jdbcAccountDao: JdbcAccountDao = mock()
    private val jdbcTemplateClientDao: JdbcTemplateClientDao = mock()

    private val accountService: AccountService = AccountService(jdbcTemplateClientDao, jdbcAccountDao)

    @Nested
    inner class GetAccountInfoTest {
        @Test
        fun `get account info`() {
            whenever(jdbcTemplateClientDao.findClientByCode(CLIENT_CODE)).thenReturn(testClient)
            whenever(jdbcAccountDao.findAccountByAccountNumberAndClientId(ACCOUNT_NUMBER, testClient)).thenReturn(testAccount)
            val account = accountService.getAccountInfo(ACCOUNT_NUMBER, CLIENT_CODE)
            assertThat(account.accountNumber).isEqualTo(ACCOUNT_NUMBER)
        }

        @Test
        fun `throw exception when account not found`() {
            whenever(jdbcTemplateClientDao.findClientByCode(CLIENT_CODE)).thenReturn(testClient)
            whenever(jdbcAccountDao.findAccountByAccountNumberAndClientId(ACCOUNT_NUMBER, testClient)).thenReturn(null)
            val exception =
                assertThrows<NotFoundException> { accountService.getAccountInfo(ACCOUNT_NUMBER, CLIENT_CODE) }
            assertEquals("Account number $ACCOUNT_NUMBER for client code $CLIENT_CODE is not found", exception.message)
        }

        @Test
        fun `throw exception when client not found`() {
            whenever(jdbcTemplateClientDao.findClientByCode(CLIENT_CODE)).thenReturn(null)
            val exception =
                assertThrows<NotFoundException> { accountService.getAccountInfo(ACCOUNT_NUMBER, CLIENT_CODE) }
            assertEquals("Client with code $CLIENT_CODE is not found", exception.message)
        }
    }

    @Nested
    inner class GetAccountsTest {
        @Test
        fun `get accounts`() {
            whenever(jdbcTemplateClientDao.findClientByCode(CLIENT_CODE)).thenReturn(Client(1, CLIENT_CODE, 1))
            whenever(jdbcAccountDao.findAllByClientId(1)).thenReturn(
                listOf(
                    Account(
                        1, 1, ACCOUNT_NUMBER, USER_ACCOUNT_NAME, USD, PAYMENT_TYPE,
                        ACCOUNT_ACTIVE_STATUS, BigDecimal(0.0), Instant.now()
                    )
                )
            )
            val accounts = accountService.getAccounts(CLIENT_CODE)
            assertAll(
                { assertThat(accounts).hasSize(1) },
                { assertThat(accounts[0].accountNumber).isEqualTo(ACCOUNT_NUMBER) },
                { assertThat(accounts[0].currency).isEqualTo(USD) },
                { assertThat(accounts[0].accountName).isEqualTo(USER_ACCOUNT_NAME) }
            )
            assertThat(accounts).hasSize(1)
        }

        @Test
        fun `throw exception when client not found`() {
            whenever(jdbcTemplateClientDao.findClientByCode(CLIENT_CODE)).thenReturn(null)
            val exception = assertThrows<NotFoundException> { accountService.getAccounts(CLIENT_CODE) }
            assertEquals("Client with code $CLIENT_CODE is not found", exception.message)
        }
    }

    @Nested
    inner class CreateAccountTest {
        @TestFactory
        fun `create account`() =
            mapOf(
                "Payment account" to null,
                USER_ACCOUNT_NAME to USER_ACCOUNT_NAME
            ).map {
                dynamicTest("account name ${it.key}") {
                    whenever(jdbcTemplateClientDao.findClientByCode(CLIENT_CODE)).thenReturn(Client(1, CLIENT_CODE, 1))
                    whenever(jdbcAccountDao.findAccountTypeByTypeCode(PAYMENT_TYPE))
                        .thenReturn(AccountType(PAYMENT_TYPE, "Payment account", "Payment type description"))
                    /* option with
                    - default account name when user's account name is null
                    - user's account name
                    */
                    whenever(jdbcAccountDao.save(any(), any())).thenReturn(testAccount.copy(accountName = it.key))
                    val account = accountService.createAccount(
                        CreateAccountRq(it.value, PAYMENT_TYPE, USD),
                        CLIENT_CODE
                    )
                    assertAll(
                        { assertThat(account.accountNumber).isEqualTo(ACCOUNT_NUMBER) },
                        { assertThat(account.currency).isEqualTo(USD) },
                        { assertThat(account.accountName).isEqualTo(it.key) }
                    )
                }
            }

        @ParameterizedTest
        @ValueSource(strings = ["", " "])
        fun `throw exception when account name is blank`(accountName: String) {
            val exception = assertThrows<IllegalArgumentException> {
                accountService.createAccount(CreateAccountRq(accountName, PAYMENT_TYPE, USD), CLIENT_CODE)
            }
            assertEquals("Account name cannot be blank", exception.message)
        }

        @Test
        fun `throw exception when client not found`() {
            whenever(jdbcTemplateClientDao.findClientByCode(CLIENT_CODE)).thenReturn(null)
            val exception = assertThrows<NotFoundException> {
                accountService.createAccount(CreateAccountRq(USER_ACCOUNT_NAME, PAYMENT_TYPE, USD), CLIENT_CODE)
            }
            assertEquals("Client with code $CLIENT_CODE is not found", exception.message)
        }

        @Test
        fun `throw exception when account type not found`() {
            val incorrectAccountType = "PML"
            whenever(jdbcTemplateClientDao.findClientByCode(CLIENT_CODE)).thenReturn(Client(1, CLIENT_CODE, 1))
            whenever(jdbcAccountDao.findAccountTypeByTypeCode(incorrectAccountType)).thenReturn(null)
            val exception = assertThrows<NotFoundException> {
                accountService.createAccount(CreateAccountRq(null, "PML", USD), CLIENT_CODE)
            }
            assertEquals("Account type with code $incorrectAccountType is not found", exception.message)
        }
    }

    @Nested
    inner class UpdateAccountStatusTest {
        @Test
        fun `update account status`() {
            val account = accountService.updateStatus(ACCOUNT_NUMBER, CLIENT_CODE, ACCOUNT_ACTIVE_STATUS)
            verify(jdbcAccountDao).updateStatusByAccountNumber(ACCOUNT_NUMBER, CLIENT_CODE, ACCOUNT_ACTIVE_STATUS)
            assertAll(
                { assertThat(account.accountNumber).isEqualTo(ACCOUNT_NUMBER) },
                { assertThat(account.newStatus).isEqualTo(ACCOUNT_ACTIVE_STATUS) }
            )
        }
    }

    companion object {
        private val testAccount = Account(
            1, 1, ACCOUNT_NUMBER, USER_ACCOUNT_NAME,
            USD, PAYMENT_TYPE, ACCOUNT_ACTIVE_STATUS, BigDecimal(0.0), Instant.now()
        )
        private val testClient = Client(1, CLIENT_CODE, 1)
    }
}