package com.ocean.bank.error.handling.testEnvirament.repository;

import com.ocean.bank.error.handling.testEnvirament.entity.DemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<DemoEntity, Long> {
}
