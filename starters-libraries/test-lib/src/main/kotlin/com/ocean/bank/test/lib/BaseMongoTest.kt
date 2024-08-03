package com.ocean.bank.test.lib

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.utility.DockerImageName
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.function.Supplier

class BaseMongoTest : BaseTest() {

    companion object {
        @Container
        val mongoDBContainer: MongoDBContainer = MongoDBContainer(
            DockerImageName.parse("mongo:5")
        )

        @DynamicPropertySource
        @JvmStatic
        fun setProperties(registry: DynamicPropertyRegistry) {
            mongoDBContainer.start()
            mongoDBContainer.setWaitStrategy(
                Wait.defaultWaitStrategy()
                    .withStartupTimeout(Duration.of(300, ChronoUnit.SECONDS))
            )
            registry.add("spring.data.mongodb.uri", (Supplier {
                "${mongoDBContainer.replicaSetUrl}?serverSelectionTimeoutMS=200&connectTimeoutMS=200"
            }))
        }
    }
}
