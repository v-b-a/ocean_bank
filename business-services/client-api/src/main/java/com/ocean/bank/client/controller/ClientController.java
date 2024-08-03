package com.ocean.bank.client.controller;

import com.ocean.bank.client.controller.dto.ClientAdditionalInfo;
import com.ocean.bank.client.controller.dto.ClientInfo;
import com.ocean.bank.client.controller.dto.ClientRs;
import com.ocean.bank.client.controller.dto.ConfirmRq;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.ocean.bank.client.service.ClientService;

import javax.security.auth.login.LoginException;
import java.util.List;

@RestController
@AllArgsConstructor
public class ClientController {
    private ClientService clientService;

    @PostMapping
    public ClientRs createClient(
            @RequestBody @Valid ClientInfo clientInfo
    ) {
        return clientService.createClient(clientInfo);
    }

    @PostMapping("/confirm")
    public ClientRs confirmClient(
            @RequestHeader("client-code") String clientCode,
            @RequestBody @Valid ConfirmRq confirmCode
    ) throws LoginException {
        return clientService.confirmClient(clientCode, confirmCode);
    }

    @GetMapping("/internal")
    public List<ClientRs> getClients() {
        return clientService.getClients();
    }

    @GetMapping("/additional-info")
    public ClientAdditionalInfo getClientAdditionalInfo(
            @RequestHeader("client-code") String clientCode
    ) {
        return clientService.getClintAdditionalInfo(clientCode);
    }
}
