package com.ocean.bank.error.handling.testEnvironment.controller;

import com.ocean.bank.error.handling.excepiton.NotFoundException;
import com.ocean.bank.error.handling.testEnvironment.dto.DemoDto;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class TestController {

    @GetMapping(value = "/testSqlException",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void sqlException() throws SQLException {
        throw new SQLException("ERROR: relation \"demo_entity\" does not exist");
    }

    @PostMapping("/testMissingRequestValueException")
    public void missingRequestValueException(
            @RequestParam("code") String code,
            @RequestBody @Validated DemoDto demoDto
    ) {
    }

    @GetMapping("/testNotFoundException")
    public void notFoundException() {
        throw new NotFoundException("not found exception");
    }
}
