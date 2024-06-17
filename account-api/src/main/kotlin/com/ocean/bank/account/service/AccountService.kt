package com.ocean.bank.account.service

import com.ocean.bank.account.controller.dto.AccountRs
import com.ocean.bank.account.controller.dto.CreateAccountRq
import com.ocean.bank.account.controller.dto.UpdateAccountStatusRs
import com.ocean.bank.account.repository.entity.Account
import com.ocean.bank.account.repository.JdbcAccountDao
import com.ocean.bank.account.repository.JDBCTemplateClientDao
import com.ocean.bank.error.handling.excepiton.NotFoundException
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.Instant

@Service
class AccountService(
    val jdbcTemplateClientDao: JDBCTemplateClientDao,
    val jdbcAccountDao: JdbcAccountDao,
) {
    fun createAccount(request: CreateAccountRq, clientCode: String): AccountRs {
        require(request.accountName?.isNotBlank() != false) { "Account name cannot be blank" }

        val accountName = request.accountName ?: (jdbcAccountDao
            .findAccountTypeByTypeCode(request.accountType)?.name
            ?: throw NotFoundException("Account type with code ${request.accountType} is not found"))

        val client = jdbcTemplateClientDao.findClientByCode(clientCode)
            ?: throw NotFoundException("Client with code $clientCode is not found")

        return Account(
            clientId = client.clientId,
            accountNumber = generateAccountNumber(),
            accountType = request.accountType,
            currencyCode = request.currency,
            previousDayBalance = BigDecimal.ZERO,
            createAt = Instant.now(),
            accountName = accountName,
            accountStatus = AccountStatuses.NEW.code
        )
            .run { jdbcAccountDao.save(this, client) }
            .toAccountRs(client.code)
    }

    fun getAccountInfo(accountNumber: String, clientCode: String): AccountRs {
        val client = jdbcTemplateClientDao.findClientByCode(clientCode)
            ?: throw NotFoundException("Client with code $clientCode is not found")
        return jdbcAccountDao.findAccountByAccountNumberAndClientId(accountNumber, client)
            ?.toAccountRs(client.code)
            ?: throw NotFoundException("Account number $accountNumber for client code $clientCode is not found")
    }

    fun getAccounts(clientCode: String): List<AccountRs> {
        val client = jdbcTemplateClientDao.findClientByCode(clientCode)
            ?: throw NotFoundException("Client with code $clientCode is not found")
        return jdbcAccountDao.findAllByClientId(client.clientId)
            .map { it.toAccountRs(client.code) }
    }

    fun updateStatus(accountNumber: String, clientCode: String, statusCode: String): UpdateAccountStatusRs {
        // TODO: Реализовать проверку на основании state machine
        jdbcAccountDao.updateStatusByAccountNumber(accountNumber, clientCode, statusCode)
        return UpdateAccountStatusRs(accountNumber, clientCode, statusCode)
    }

    private fun generateAccountNumber(): String {
        val fifteenDigitNumber = (0 until 15).map { (0..9).random() }.joinToString("")
        val accountNumber = "$INDIVIDUAL_ACCOUNT_PREFIX$fifteenDigitNumber"
        return if (jdbcAccountDao.findAccountByAccountNumber(accountNumber) == null) {
            "$INDIVIDUAL_ACCOUNT_PREFIX$fifteenDigitNumber"
        } else {
            generateAccountNumber()
        }
    }

    private fun Account.toAccountRs(clientCode: String): AccountRs =
        AccountRs(
            clientCode = clientCode,
            accountNumber = this.accountNumber,
            accountName = this.accountName,
            currency = this.currencyCode,
            balance = this.previousDayBalance,
            status = this.accountStatus
        )

    companion object {
        private const val INDIVIDUAL_ACCOUNT_PREFIX = "40817"

        // TODO: Нужно реализовать статусную модель
        enum class AccountStatuses(val code: String) {
            NEW("NEW"),
            ACTIVE("ACT")
        }
    }
}