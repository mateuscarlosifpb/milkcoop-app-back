package com.milkcoop.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "payrollsProducers")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@SequenceGenerator(name = "id_payroll_producer", sequenceName = "id_payroll_producer", allocationSize = 1)
public class PayrollProducer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id_payroll_producer")
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_payroll")
    private Payroll payroll;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producer")
    private Producer producer;
    @Column(name = "data_register")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dataRegister;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "quantity")
    private BigDecimal quantity;
    @OneToMany(mappedBy = "payrollProducer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PayrollProducerDelivery> payrollProducerDeliveries;

}
