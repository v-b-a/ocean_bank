package com.ocean.bank.account.repository

import com.ocean.bank.account.configuration.DataSourceProperties
import org.springframework.stereotype.Component
import java.lang.reflect.Proxy
import java.sql.Connection
import java.sql.DriverManager
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
import jakarta.annotation.PreDestroy

@Component
class ConnectionPoolManager(
    private val dataSourceProperties: DataSourceProperties
) {
    private lateinit var pool: BlockingQueue<Connection>
    private val sourceConnection: MutableList<Connection> = mutableListOf()

    init {
        initConnectionPool()
    }

    fun getConnection(): Connection = pool.take()

    fun getCount(): Int = pool.size

    private fun initConnectionPool() {
        val poolSize = dataSourceProperties.poolSize ?: DEFAULT_POOL_SIZE
        pool = ArrayBlockingQueue(poolSize)
        for (i in 0 until poolSize) {
            val connection = createJDBCConnection()
            val proxyConnection = Proxy.newProxyInstance(
                ConnectionPoolManager::class.java.classLoader, arrayOf(Connection::class.java)
            ) { _, method, args ->
                if (method.name == "close") pool.add(connection) else method.invoke(connection, *args.orEmpty())
            } as Connection
            pool.add(proxyConnection)
            sourceConnection.add(connection)
        }
    }

    private fun createJDBCConnection(): Connection =
        DriverManager.getConnection(
            dataSourceProperties.url,
            dataSourceProperties.username,
            dataSourceProperties.password
        )

    companion object {
        private const val DEFAULT_POOL_SIZE = 10
    }

    @PreDestroy
    fun close() {
        sourceConnection.forEach { it.close() }
    }
}