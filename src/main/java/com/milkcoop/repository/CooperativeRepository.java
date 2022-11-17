package com.milkcoop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.milkcoop.data.model.Cooperative;

@Repository
public interface CooperativeRepository extends JpaRepository<Cooperative, Long> {

}
