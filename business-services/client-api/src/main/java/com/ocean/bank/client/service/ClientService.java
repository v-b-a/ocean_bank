package com.ocean.bank.client.service;

//import com.ocean.bank.client.client.AccountApiRestTemplate;
//import com.ocean.bank.client.client.AccountApiWebClient;
import com.ocean.bank.client.controller.dto.ClientAdditionalInfo;
import com.ocean.bank.client.controller.dto.ClientInfo;
import com.ocean.bank.client.controller.dto.ClientRs;
import com.ocean.bank.client.controller.dto.ConfirmRq;
import com.ocean.bank.client.repository.ClientRepository;
import com.ocean.bank.client.repository.StatusRepository;
import com.ocean.bank.client.repository.entity.Client;
import com.ocean.bank.client.repository.entity.Status;
import com.ocean.bank.error.handling.excepiton.LoginException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final StatusRepository statusRepository;
    private final EntityMapper entityMapper;
    //    private final AccountApiFeignClient accountApiFeignClient;
//    private final AccountApiRestTemplate accountRestTemplate;
//    private final AccountApiWebClient accountApiWebClient;

    public ClientRs createClient(ClientInfo clientRq) {
        Status status = statusRepository.findStatusByCode(1).orElseThrow(() -> new RuntimeException("Status not found"));
        Client client = clientRepository.save(entityMapper.toNewClientConverter(clientRq, status));
        // Send client info to account-api to create record by client in PostgreSQL Using OpenFeign
        // accountApiFeignClient.addClient(client.getClientCode());
        return entityMapper.toClientRs(client);
    }

    public ClientRs confirmClient(String clientCode, ConfirmRq confirmCode) throws LoginException {
        // TODO: реализовать валидицию кода подтверждения
        if (confirmCode.getConfirmCode().equals("1111")) {
            Status status = statusRepository.findStatusByCode(2).orElseThrow(() -> new RuntimeException("Status not found"));
            Client client = clientRepository.findClientByClientCode(clientCode);
            client.setStatus(status.getName());
            Client updatedClient = clientRepository.save(client);
            // Using RestTemplate
//            ClientStatus statusRq = new ClientStatus(status.getCode());
            // accountRestTemplate.updateClientStatus(clientCode, statusRq);
            return entityMapper.toClientRs(updatedClient);
        } else {
            throw new LoginException(String.format("Invalid confirmCode: %s", confirmCode));
        }
    }

    public List<ClientRs> getClients() {
        return clientRepository.findAll().stream().map(entityMapper::toClientRs).toList();
    }

    public ClientAdditionalInfo getClintAdditionalInfo(String clientCode) {
        Client client = clientRepository.findClientByClientCode(clientCode);
        // Using WebClient
        // List<ClientAccount> clientAccounts = accountApiWebClient.getClientAccounts(clientCode).block();
        return ClientAdditionalInfo.builder()
                .clientInfo(entityMapper.toClientInfo(client))
                .clientAccounts(List.of())
                .build();
    }
}
