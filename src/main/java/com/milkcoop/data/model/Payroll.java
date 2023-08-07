package com.milkcoop.data.model;


import com.milkcoop.data.model.enums.PaymentStatus;
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
@Table(name = "payrolls")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@SequenceGenerator(name = "id_payroll", sequenceName = "id_payroll", allocationSize = 1)
public class Payroll implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id_payroll")
    @Column(name = "id")
    private Long id;
    @Column(name = "data_register")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dataRegister;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "quantity")
    private BigDecimal quantity;
    @OneToMany(mappedBy = "payroll", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PayrollProducer> payrollProducerList;

    @Column(name = "statusPagamento")
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus status;
}
