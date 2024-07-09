package com.ocean.bank.account.controller

import com.ocean.bank.account.service.ClientService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank

@RestController
@RequestMapping("/internal/client")
class ClientController(
    val clientService: ClientService
) {
    @PostMapping
    fun addClient(@RequestHeader("client-code") clientCode: String) {
        clientService.saveClient(clientCode)
    }

    @PutMapping
    fun updateClientStatus(
        @RequestHeader("client-code") clientCode: String,
        @RequestParam @Valid @NotBlank status: String
    ) {
        clientService.updateClientStatus(clientCode, status)
    }
}
