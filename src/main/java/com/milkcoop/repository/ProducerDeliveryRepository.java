package com.milkcoop.repository;

import com.milkcoop.data.model.ProducerDelivery;
import com.milkcoop.data.model.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProducerDeliveryRepository extends JpaRepository<ProducerDelivery, Long> {
    Page<ProducerDelivery> findByProducerId(Pageable pageable, Long idProducer);
    List<ProducerDelivery> findByProducerId(Long idProducer);

    List<ProducerDelivery> findByStatus(PaymentStatus status);
    List<ProducerDelivery> findByDataRegisterBetween(LocalDate startDate, LocalDate endDate);


    /*@Query(" SELECT SUM(p.quantity) as quantity" +
            "   FROM ProducerDelivery p" +
            "   WHERE p.producer.id =:idProducer ")
    BigDecimal totalQuantity(@Param("idProducer") Long idProducer);

    @Query(" SELECT SUM(p.quantity) as quantity" +
            "   FROM ProducerDelivery p" +
            "   WHERE p.producer.id =:idProducer " +
            "   AND p.dataRegister BETWEEN :start AND :end ")
    BigDecimal totalMonth(@Param("idProducer") Long idProducer, @Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query(" SELECT SUM(p.quantity) as quantity" +
            "   FROM ProducerDelivery p" +
            "   WHERE p.producer.id =:idProducer " +
            "   AND p.dataRegister BETWEEN :start AND :end ")
    BigDecimal totalYear(@Param("idProducer") Long idProducer, @Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT SUM(MULTIPLY(p.quantity, p.product.price)) AS value " +
            "FROM ProducerDelivery p " +
            "WHERE p.producer.id = :idProducer " +
            "AND p.status = :status")
    BigDecimal totalReceivable(@Param("idProducer") Long idProducer, @Param("status") PaymentStatus status);

    @Query(" SELECT SUM(MULTIPLY(p.quantity, p.product.price)) as value" +
            "   FROM ProducerDelivery p" +
            "   WHERE p.producer.id =:idProducer " +
            "   AND p.dataRegister BETWEEN :start AND :end " +
            "   AND p.status = :status")
    BigDecimal receivedInTheYear(@Param("idProducer") Long idProducer, @Param("start") LocalDate start, @Param("end") LocalDate end, @Param("status") PaymentStatus status);

    @Query(" SELECT SUM(p.quantity) as quantity" +
            "   FROM ProducerDelivery p")
    BigDecimal totalQuantity();

    @Query(" SELECT SUM(p.quantity) as quantity" +
            "   FROM ProducerDelivery p" +
            "   WHERE p.dataRegister BETWEEN :start AND :end ")
    BigDecimal totalMonth(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query(" SELECT SUM(p.quantity) as quantity" +
            "   FROM ProducerDelivery p" +
            "   WHERE p.dataRegister BETWEEN :start AND :end ")
    BigDecimal totalYear(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query(" SELECT SUM(MULTIPLY(p.quantity, p.product.price)) as value" +
            "   FROM ProducerDelivery p" +
            "   WHERE p.status = :status")
    BigDecimal totalReceivable(@Param("status") PaymentStatus status);

    @Query(" SELECT SUM(MULTIPLY(p.quantity, p.product.price)) as value" +
            "   FROM ProducerDelivery p" +
            "   WHERE p.dataRegister BETWEEN :start AND :end " +
            "   AND p.status = :status")
    BigDecimal receivedInTheYear(@Param("start") LocalDate start, @Param("end") LocalDate end, @Param("status") PaymentStatus status);*/
}
