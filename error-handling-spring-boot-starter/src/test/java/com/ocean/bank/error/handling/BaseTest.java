package com.ocean.bank.error.handling;

import com.ocean.bank.error.handling.service.ErrorHandlingService;
import com.ocean.bank.error.handling.testEnvironment.controller.TestController;
import com.ocean.bank.error.handling.testEnvironment.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = {ErrorHandlingService.class, TestController.class, TestService.class})
@AutoConfigureMockMvc
public class BaseTest {
    @Autowired
    MockMvc mockMvc;
}
