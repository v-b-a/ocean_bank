//package com.ocean.bank.account.repository
//
//import org.junit.jupiter.api.AfterEach
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
//import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
//import org.springframework.test.context.DynamicPropertyRegistry
//import org.springframework.test.context.DynamicPropertySource
//import org.springframework.test.context.jdbc.Sql
//import org.testcontainers.containers.PostgreSQLContainer
//import org.testcontainers.junit.jupiter.Container
//import org.testcontainers.junit.jupiter.Testcontainers
//
//
//@Testcontainers
//@JdbcTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class ClientRepositoryTest {
//    @Autowired
//    private lateinit var jdbcTemplateClientDao: JDBCTemplateClientDao
//
//    @Test
//    @Sql("/scripts/init_client_statuses.sql")
//    fun contextLoads() {
//        jdbcTemplateClientDao.updateClientWithStatusName("AAAAA", "ACTIVE")
//
//        val client = clientRepository.findClientByCode("AAAAA")
//        assertEquals(2, client?.statusId)
//    }
//
//    companion object {
//        @Container
//        val container = PostgreSQLContainer<Nothing>("postgres:12")
//            .apply {
//                withDatabaseName("testdb")
//                withUsername("duke")
//                withPassword("s3crEt")
//            }
//
//        @JvmStatic
//        @DynamicPropertySource
//        fun properties(registry: DynamicPropertyRegistry) {
//            registry.add("spring.datasource.url", container::getJdbcUrl);
//            registry.add("spring.datasource.password", container::getPassword);
//            registry.add("spring.datasource.username", container::getUsername);
//        }
//    }
//
//
//}
