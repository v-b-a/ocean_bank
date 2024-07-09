package com.ocean.bank.account.repository

import com.ocean.bank.account.repository.entity.Account
import com.ocean.bank.account.repository.entity.AccountType
import com.ocean.bank.account.repository.entity.Client
import com.ocean.bank.error.handling.excepiton.NotFoundException
import mu.KLogging
import org.springframework.stereotype.Component
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Timestamp
import java.time.Instant
import javax.sql.DataSource

@Component
class JdbcAccountDao(
    private val dataSource: DataSource
) {
    fun updateStatusByAccountNumber(accountNumber: String, clientCode: String, statusCode: String) {
        logger.info { "Starting update account status for account number $accountNumber and client $clientCode" }
        dataSource.connection.use { connection ->
            try {
                connection.autoCommit = false
                val account = connection.getAccount(accountNumber, clientCode)
                    ?: throw NotFoundException("Account number $accountNumber for client $clientCode not found")
                connection.saveAccountStatusesHistory(account, statusCode)
                connection.updateAccounts(accountNumber, account, statusCode)
                connection.commit()
            } catch (e: Exception) {
                connection.rollback()
                e.printStackTrace()
                throw e
            }
        }
    }

    private fun Connection.getAccount(accountNumber: String, clientCode: String): Account? =
        this.prepareStatement(GET_ACCOUNT_BY_ACCOUNT_NUMBER).use { statement ->
            logger.info { "Starting get account for account number $accountNumber and client $clientCode" }
            statement.queryTimeout = 30
            statement.setString(1, accountNumber)
            statement.setString(2, clientCode)
            val resultSet = statement.executeQuery()
            try {
                if (!resultSet.next()) return null
                val accountId = resultSet.getLong("account_id")
                val clientId = resultSet.getLong("client_id")
                val accountName = resultSet.getString("name")
                val currencyCode = resultSet.getString("currency_code")
                val statusCode = resultSet.getString("account_status_code")
                val accountType = resultSet.getString("account_type_code")
                val previousDayBalance = resultSet.getBigDecimal("previous_day_balance")
                val createAt = resultSet.getTimestamp("created_at").toInstant()
                return Account(
                    accountId, clientId, accountNumber, accountName, currencyCode,
                    accountType, statusCode, previousDayBalance, createAt
                )
            } catch (e: SQLException) {
                e.printStackTrace()
                throw SQLException("Get query was not executed correctly")
            }
        }

    private fun Connection.saveAccountStatusesHistory(account: Account, statusCode: String) {
        logger.info { "Starting save account statuses history for account ${account.accountId} with status code $statusCode" }
        this.prepareStatement(SAVE_TO_ACCOUNT_STATUS_HISTORY).use { statement ->
            statement.setString(1, account.accountStatus)
            statement.setString(2, statusCode)
            statement.setTimestamp(3, Timestamp.from(Instant.now()))
            statement.setLong(4, account.accountId)
            try {
                statement.executeUpdate()
            } catch (e: SQLException) {
                e.printStackTrace()
                throw SQLException("Update account statuses history query was not executed correctly")
            }
        }
    }

    private fun Connection.updateAccounts(accountNumber: String, account: Account, statusCode: String) =
        this.prepareStatement(UPDATE_ACCOUNT_STATUS_IN_ACCOUNTS)
            .use { statement ->
                logger.info { "Starting update accounts for account ${account.accountId} with status code $statusCode" }
                statement.setString(1, statusCode)
                statement.setString(2, accountNumber)
                statement.setLong(3, account.clientId)
                if (statement.executeUpdate() != 1) {
                    throw SQLException("Update accounts query was not executed correctly")
                }
            }

    fun save(account: Account, client: Client): Account {
        dataSource.connection.use { connection ->
            connection.prepareStatement(INSERT_ACCOUNT).use { statement ->
                logger.info { "Saving account ${account.accountId}" }
                statement.setLong(1, account.clientId)
                statement.setString(2, account.currencyCode)
                statement.setBigDecimal(3, account.previousDayBalance)
                statement.setTimestamp(4, Timestamp.from(account.createAt))
                statement.setString(5, account.accountType)
                statement.setString(6, account.accountStatus)
                statement.setString(7, account.accountName)
                statement.setString(8, account.accountNumber)
                try {
                    statement.executeUpdate()
                } catch (e: Exception) {
                    e.printStackTrace()
                    throw e
                }
            }
            return connection.getAccount(account.accountNumber, client.code)
                ?: throw SQLException("Account was not saved correctly")
        }
    }

    fun findAccountByAccountNumberAndClientId(accountNumber: String, client: Client): Account? {
        return dataSource.connection.getAccount(accountNumber, client.code)
    }

    fun findAllByClientId(clientId: Long): List<Account> {
        dataSource.connection.use { connection ->
            connection.prepareStatement(SELECT_ACCOUNTS_BY_CLIENT).use { statement ->
                statement.setLong(1, clientId)
                val resultSet = statement.executeQuery()
                val accounts = mutableListOf<Account>()
                while (resultSet.next()) {
                    accounts.add(accountConverter(resultSet))
                }
                return accounts
            }
        }
    }

    fun findAccountByAccountNumber(accountNumber: String): Account? {
        dataSource.connection.use { connection ->
            connection.prepareStatement(SELECT_ACCOUNT_BY_ACCOUNT_ID).use { statement ->
                statement.setString(1, accountNumber)
                val resultSet = statement.executeQuery()
                return if (resultSet.next()) accountConverter(resultSet) else null
            }
        }
    }

    fun findAccountTypeByTypeCode(accountType: String): AccountType? {
        dataSource.connection.use { connection ->
            connection.prepareStatement(SELECT_ACCOUNT_TYPE_BY_TYPE_CODE).use { statement ->
                logger.info { "Get account type by type code $accountType" }
                statement.setLong(1, accountType.toLong())
                val resultSet = statement.executeQuery()
                return if (resultSet.next()) {
                    AccountType(
                        resultSet.getString("type_code"),
                        resultSet.getString("name"),
                        resultSet.getString("type_description")
                    )
                } else {
                    null
                }
            }
        }
    }

    private fun accountConverter(resultSet: ResultSet) = Account(
        resultSet.getLong("account_id"),
        resultSet.getLong("client_id"),
        resultSet.getString("account_number"),
        resultSet.getString("name"),
        resultSet.getString("currency_code"),
        resultSet.getString("account_type_code"),
        resultSet.getString("account_status_code"),
        resultSet.getBigDecimal("previous_day_balance"),
        resultSet.getTimestamp("created_at").toInstant()
    )

    companion object : KLogging() {
        const val GET_ACCOUNT_BY_ACCOUNT_NUMBER = """
            SELECT ac.account_id, ac.client_id, ac.account_number, ac.name, ac.currency_code, ac.account_type_code, 
                    ac.account_status_code, ac.account_status_code, ac.previous_day_balance, ac.created_at
            FROM accounts as ac
            JOIN clients as cl ON cl.client_id = ac.client_id
            WHERE account_number = ? and cl.code = ?;"""
        const val SAVE_TO_ACCOUNT_STATUS_HISTORY = """
            INSERT INTO account_statuses_history (previous_status_code, new_status_code, date_time_of_change, account_id)
            VALUES (?, ?, ?, ?);
        """
        const val UPDATE_ACCOUNT_STATUS_IN_ACCOUNTS = """
            UPDATE accounts 
            SET account_status_code = ?
            WHERE account_number = ? and client_id = ?;
            """

        const val INSERT_ACCOUNT = """
            INSERT INTO accounts (client_id, currency_code, previous_day_balance, created_at, account_type_code, account_status_code, name, account_number)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?);
        """

        const val SELECT_ACCOUNTS_BY_CLIENT = """
            SELECT account_id, client_id, currency_code, previous_day_balance, created_at, account_type_code, account_status_code, name, account_number
            FROM accounts
            WHERE client_id = ?;
        """

        const val SELECT_ACCOUNT_BY_ACCOUNT_ID = "SELECT * FROM accounts WHERE account_number = ?;"

        const val SELECT_ACCOUNT_TYPE_BY_TYPE_CODE = """
            SELECT name, description, type_code
            FROM account_types
            WHERE type_code = ?;
        """
    }

}
