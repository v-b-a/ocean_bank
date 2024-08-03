package com.ocean.bank.client.controller;

import com.ocean.bank.client.repository.StatusRepository;
import com.ocean.bank.client.repository.entity.Status;
import com.ocean.bank.test.lib.BaseMongoTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BaseControllerTest extends BaseMongoTest {
    @Autowired
    StatusRepository statusRepository;

    @BeforeEach
    public void setup() {
        statusRepository.saveAll(
                List.of(
                        new Status("id1", "ACTIVE", "Active status", 1),
                        new Status("id2", "INACTIVE", "Inactive status", 2)
                )
        );
    }
}
