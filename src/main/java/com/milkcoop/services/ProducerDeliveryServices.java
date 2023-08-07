package com.milkcoop.services;

import com.milkcoop.data.model.vo.ProducerDeliveryVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Map;

public interface ProducerDeliveryServices {
    ProducerDeliveryVO create(ProducerDeliveryVO producerDeliveryVO) throws ParseException;

    ProducerDeliveryVO update(ProducerDeliveryVO producerDeliveryVO);

    void delete(Long id);

    Page<ProducerDeliveryVO> find(Pageable pageable, Long idProducer);

    ProducerDeliveryVO findById(Long id);

    Map<String, BigDecimal> dashboardProducer(Long idProducer);
    Map<String, Object> organizeInformation();
}
