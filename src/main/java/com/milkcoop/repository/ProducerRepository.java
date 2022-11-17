package com.milkcoop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.milkcoop.data.model.Producer;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {

}
