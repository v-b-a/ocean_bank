package com.ocean.bank.client.client;

import com.ocean.bank.client.client.dto.ClientAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountApiWebClient {
    private final WebClient webClient;

    @Value("${client.account.url}")
    private String accountApiUrl;

    public Mono<List<ClientAccount>> getClientAccounts(String clientCode) {
        return webClient.get()
                .uri(accountApiUrl)
                .header("client-code", clientCode)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }
}