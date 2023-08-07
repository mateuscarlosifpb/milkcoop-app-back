package com.milkcoop.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "payrollsProducerDeliverys")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@SequenceGenerator(name = "id_payroll_producer_delivery", sequenceName = "id_payroll_producer_delivery", allocationSize = 1)
public class PayrollProducerDelivery {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id_payroll_producer_delivery")
    @EqualsAndHashCode.Include
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_producer_delivery", nullable = false)
    private ProducerDelivery producerDelivery;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_payroll_producer", nullable = false)
    private PayrollProducer payrollProducer;
}
