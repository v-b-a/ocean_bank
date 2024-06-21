package com.ocean.bank.client.repository;

import com.ocean.bank.client.repository.entity.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends MongoRepository<Client, String> {
    Client findClientByClientCode(String clientCode);
}
