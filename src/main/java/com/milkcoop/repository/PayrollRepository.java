package com.milkcoop.repository;

import com.milkcoop.data.model.Payroll;
import com.milkcoop.data.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {
    Page<Payroll> findByDataRegister(Pageable pageable, LocalDate dataRegister);

    Page<Payroll> findByAmount(Pageable pageable, BigDecimal price);

    Page<Payroll> findByAmountAndDataRegister(Pageable pageable, BigDecimal price, LocalDate dataRegister);
}
