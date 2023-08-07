package com.milkcoop.repository;

import com.milkcoop.data.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p" +
            " WHERE :dataRegister IS NULL OR p.dataRegister =:dataRegister" +
            " AND (:price IS NULL OR p.price =:price)")
    Page<Product> findAll(Pageable pageable, @Param("dataRegister") LocalDate dataRegister, @Param("price") BigDecimal price);

    Page<Product> findByDataRegister(Pageable pageable, LocalDate dataRegister);

    Page<Product> findByPrice(Pageable pageable, BigDecimal price);

    Page<Product> findByPriceAndDataRegister(Pageable pageable, BigDecimal price, LocalDate dataRegister);

    @Query("SELECT p FROM Product p WHERE p.dataRegister = (" +
            "SELECT MAX(p2.dataRegister) FROM Product p2 WHERE p2.dataRegister <= :lancamentoDate)")
    Optional<Product> findNearestToLancamento(LocalDate lancamentoDate);
}
