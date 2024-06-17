//package com.ocean.bank.account.repository
//
//import com.ocean.bank.account.BaseTest
//import org.junit.jupiter.api.AfterEach
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
//import org.springframework.test.context.DynamicPropertyRegistry
//import org.springframework.test.context.DynamicPropertySource
//import org.testcontainers.containers.PostgreSQLContainer
//import org.testcontainers.junit.jupiter.Container
//import org.testcontainers.junit.jupiter.Testcontainers
//
//@Testcontainers
//@
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class BaseRepositoryTest : BaseTest() {
//    @Autowired
//    lateinit var clientRepository: ClientRepository
//
//    @AfterEach
//    fun cleanDatabase() {
//        clientRepository.deleteAll()
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
//}