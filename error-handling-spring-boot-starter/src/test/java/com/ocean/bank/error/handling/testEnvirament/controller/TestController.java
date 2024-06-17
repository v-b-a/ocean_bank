package com.ocean.bank.error.handling.testEnvirament.controller;

import com.ocean.bank.error.handling.BaseTest;
import com.ocean.bank.error.handling.excepiton.NotFoundException;
import com.ocean.bank.error.handling.testEnvirament.dto.DemoDto;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@EnableAutoConfiguration
public class TestController extends BaseTest {

    @GetMapping("/testSqlException")
    public void sqlException() throws SQLException {
        testRepository.findAll();
    }

    @PostMapping("/testMissingRequestValueException")
    public void missingRequestValueException(
            @RequestParam(value = "code") String code,
            @RequestBody @Validated DemoDto demoDto
    ) {
    }

    @GetMapping("/testNotFoundException")
    public void notFoundException() {
        throw new NotFoundException("not found exception");
    }
}
