package com.milkcoop.repository;

import com.milkcoop.data.model.Producer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {
    @Query("SELECT p FROM Producer p" +
            " WHERE :fullName IS NULL OR UPPER(CAST(p.fullName AS text)) LIKE CONCAT(UPPER(CAST(:fullName AS text)),'%')" +
            " AND (:telephone IS NULL OR UPPER(CAST(p.telephone AS text)) LIKE CONCAT(UPPER(CAST(:telephone AS text)),'%'))")
    Page<Producer> findAll(Pageable pageable, @Param("fullName") String fullName, @Param("telephone") String telephone);
}
