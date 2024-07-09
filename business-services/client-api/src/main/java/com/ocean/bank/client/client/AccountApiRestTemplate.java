package com.ocean.bank.client.client;

import com.ocean.bank.client.client.dto.ClientStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AccountApiRestTemplate {
    private final RestTemplate restTemplate;
    @Value("${client.account.url}")
    private String accountApiUrl;

    public AccountApiRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void updateClientStatus(String clientCode, ClientStatus statusRq) {
        String uri = accountApiUrl + "/internal/client";
        HttpHeaders headers = new HttpHeaders();
        headers.add("client-code", clientCode);
        HttpEntity<ClientStatus> httpEntity = new HttpEntity<>(statusRq, headers);
        restTemplate.exchange(uri, HttpMethod.PUT, httpEntity, Void.class);
    }
}
