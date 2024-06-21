package com.ocean.bank.client.controller.dto;

import com.ocean.bank.client.client.dto.ClientAccount;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClientAdditionalInfo {
    private ClientInfo clientInfo;
    private List<ClientAccount> clientAccounts;
}