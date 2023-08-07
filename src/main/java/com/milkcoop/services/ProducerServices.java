package com.milkcoop.services;

import com.milkcoop.data.model.vo.ProducerVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProducerServices {

    ProducerVO create(ProducerVO producerVO);

    ProducerVO update(ProducerVO producerVO);

    void delete(Long id);

    Page<ProducerVO> find(Pageable pageable, ProducerVO request);

    ProducerVO findById(Long id);
}
