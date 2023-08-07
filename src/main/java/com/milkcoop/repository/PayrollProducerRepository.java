package com.milkcoop.repository;

import com.milkcoop.data.model.PayrollProducer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayrollProducerRepository extends JpaRepository<PayrollProducer, Long> {
}
