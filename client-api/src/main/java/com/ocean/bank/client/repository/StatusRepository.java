package com.ocean.bank.client.repository;

import com.ocean.bank.client.repository.entity.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends MongoRepository<Status, String> {
    Status findClientStatusByCode(Integer code);
}
