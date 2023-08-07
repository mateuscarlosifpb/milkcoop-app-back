package com.milkcoop.repository;

import com.milkcoop.data.model.InventorySale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface InventorySaleRepository extends JpaRepository<InventorySale, Long> {
    Page<InventorySale> findByDataRegister(Pageable pageable, LocalDate dataRegister);
}
