package com.milkcoop.data.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayrollProducerVO extends RepresentationModel<PayrollProducerVO> implements Serializable {

    private Long id;
    private ProducerVO producer;
    private LocalDate dataRegister;
    private BigDecimal amount;
    private BigDecimal quantity;
    private List<PayrollProducerDeliveryVO> payrollProducerDeliveries;
}
