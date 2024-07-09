package com.ocean.bank.account.controller

import com.ocean.bank.account.controller.dto.CreateAccountRq
import com.ocean.bank.account.controller.dto.AccountRs
import com.ocean.bank.account.controller.dto.UpdateAccountStatusRs
import com.ocean.bank.account.service.AccountService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank

@RestController
class AccountController(
    val accountService: AccountService
) {
    @PostMapping
    fun createAccount(
        @RequestHeader("client-code") clientCode: String,
        @Valid @RequestBody request: CreateAccountRq,
    ): AccountRs = accountService.createAccount(request, clientCode)

    @GetMapping("/{$ACCOUNT_NUMBER}")
    fun getAccountInfo(
        @RequestHeader("client-code") clientCode: String,
        @PathVariable accountNumber: String,
    ): AccountRs = accountService.getAccountInfo(accountNumber, clientCode)

    @GetMapping
    fun getAccounts(@RequestHeader("client-code") clientCode: String): List<AccountRs> =
        accountService.getAccounts(clientCode)

    @PutMapping("/{$ACCOUNT_NUMBER}")
    fun updateAccountStatus(
        @RequestHeader("client-code") clientCode: String,
        @PathVariable accountNumber: String,
        @RequestParam @Valid @NotBlank statusCode: String
    ): UpdateAccountStatusRs = accountService.updateStatus(accountNumber, clientCode, statusCode)

    companion object {
        private const val ACCOUNT_NUMBER = "accountNumber"
    }
}