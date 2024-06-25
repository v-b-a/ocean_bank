package com.ocean.bank.error.handling.testEnvironment.service;

import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class TestService {
    public void testSqlException() throws SQLException {
        throw new SQLException("ERROR: relation \"demo_entity\" does not exist");
    }
}
