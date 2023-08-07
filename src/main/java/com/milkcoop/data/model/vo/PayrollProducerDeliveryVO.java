package com.milkcoop.data.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayrollProducerDeliveryVO extends RepresentationModel<PayrollProducerVO> implements Serializable {

    private Long id;
    private ProducerDeliveryVO producerDelivery;

}
