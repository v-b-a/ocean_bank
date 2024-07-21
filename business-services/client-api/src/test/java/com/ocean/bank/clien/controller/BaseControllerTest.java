//package com.ocean.bank.clien.controller;
//
//import com.ocean.bank.client.repository.StatusRepository;
//import com.ocean.bank.client.repository.entity.Status;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringBootConfiguration;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.web.reactive.server.WebTestClient;
//
//import java.util.List;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@EnableAutoConfiguration
//public class BaseControllerTest extends AbstractMongoConfig {
//    @Autowired
//    StatusRepository statusRepository;
//    @Autowired
//    WebTestClient webTestClient;
//
//    @BeforeAll
//    static void init(){
//        mongoDBContainer.start();
//    }
//
//    @BeforeEach
//    public void setup() {
//        statusRepository.saveAll(
//                List.of(
//                        new Status("id1", "ACTIVE", "Active status", 1),
//                        new Status("id2", "INACTIVE", "Inactive status", 2)
//                )
//        );
//    }
//}
