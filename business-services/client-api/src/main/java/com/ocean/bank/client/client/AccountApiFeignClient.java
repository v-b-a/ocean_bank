//package com.ocean.bank.client.client;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//
//@FeignClient(name = "account-api", url = "${client.account.url}")
//public interface AccountApiFeignClient {
//    @PostMapping("/internal/client")
//    void addClient(@RequestHeader("client-code") String clientCode);
//}