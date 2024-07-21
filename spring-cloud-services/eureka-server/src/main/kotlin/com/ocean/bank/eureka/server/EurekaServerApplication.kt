package com.ocean.bank.eureka.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
open class EurekaServerApplication
fun main(args: Array<String>) {
    runApplication<EurekaServerApplication>(*args)
}