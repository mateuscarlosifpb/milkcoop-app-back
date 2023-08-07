package com.milkcoop.repository;

import com.milkcoop.data.model.PayrollProducerDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayrollProducerDeliveryRepository extends JpaRepository<PayrollProducerDelivery, Long> {
}
