package com.ocean.bank.account.repository

import com.ocean.bank.account.repository.entity.Client
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementSetter
import org.springframework.stereotype.Component

@Component
class JDBCTemplateClientDao(
    val jdbcTemplate: JdbcTemplate
) {
    fun saveClient(client: Client) {
        val setter = PreparedStatementSetter { pss ->
            pss.setString(1, client.code)
            pss.setLong(2, client.statusId)
        }
        jdbcTemplate.update("INSERT INTO clients(code, status_id) VALUES (?, ?)", setter)
    }

    fun findClientByCode(clientCode: String): Client? {
        val sql = "SELECT * FROM clients WHERE code = ?"
        return jdbcTemplate.queryForObject(
            sql,
            { rs, _ -> Client(rs.getLong("client_id"), rs.getString("code"), rs.getLong("status_id")) },
            clientCode
        )
    }

    fun updateClientWithStatusName(clientCode: String, statusName: String) {
        val sqlSelect = "SELECT status_id FROM client_statuses WHERE name = ?"
        val statusId = jdbcTemplate.queryForObject(sqlSelect, String::class.java, statusName)
        val sqlUpdate = """
            UPDATE clients 
            SET status_id = $statusId 
            WHERE code = ?""".trimIndent()
        jdbcTemplate.update(sqlUpdate, clientCode)
    }
}