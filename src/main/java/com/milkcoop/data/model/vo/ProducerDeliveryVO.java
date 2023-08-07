package com.milkcoop.data.model.vo;

import com.milkcoop.data.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProducerDeliveryVO extends RepresentationModel<ProducerDeliveryVO> implements Serializable {

    private Long id;
    private ProducerVO producer;
    private ProductVO product;
    private BigDecimal quantity;
    private LocalDate dataRegister;
    private PaymentStatus status;
}
