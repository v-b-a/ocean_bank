package com.ocean.bank.error.handling;

import com.ocean.bank.error.handling.testEnvirament.repository.TestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "local.server.port=0",
                "spring.datasource.url=jdbc:postgresql://localhost:5432/testdb",
                "spring.datasource.username=postgres",
                "spring.datasource.password=postgres",
                "spring.datasource.driver-class-name=org.postgresql.Driver"
        }
)
@SpringBootApplication
@EnableAutoConfiguration
@Testcontainers
public class BaseTest {
    @Autowired
    WebApplicationContext wac;
    @Autowired
    protected WebTestClient webTestClient;
    @Autowired
    protected TestRepository testRepository;
    @LocalServerPort
    private int port;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("postgres");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", postgreSQLContainer::getDriverClassName);
    }

    @BeforeEach
    void setUp(ApplicationContext context) {
        var mockServerSpec = MockMvcWebTestClient.bindToApplicationContext(this.wac);
        this.webTestClient = mockServerSpec.configureClient().baseUrl("http://localhost:" + port).build();
    }
}
