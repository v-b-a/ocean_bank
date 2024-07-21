package com.ocean.bank.error.handling.configuration;

import com.ocean.bank.error.handling.service.ErrorHandlingService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorHandlingConfiguration {
    @Bean
    @ConditionalOnMissingBean(ErrorHandlingService.class)
    public ErrorHandlingService errorHandlingService() {
        return new ErrorHandlingService();
    }
}
