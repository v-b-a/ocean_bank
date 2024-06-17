package com.ocean.bank.error.handling.configuration;

import com.ocean.bank.error.handling.service.ErrorHandlingService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AutoConfiguration
public class ErrorHandlingConfiguration {
    @Bean
    public ErrorHandlingService errorHandlingService() {
        return new ErrorHandlingService();
    }
}
