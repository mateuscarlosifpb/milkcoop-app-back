package com.milkcoop.data.model.vo;

import com.milkcoop.data.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayrollVO extends RepresentationModel<PayrollVO> implements Serializable {
    private Long id;
    private LocalDate dataRegister;
    private BigDecimal amount;
    private BigDecimal quantity;
    private List<PayrollProducerVO> payrollProducerList;
    private PaymentStatus status;
}
