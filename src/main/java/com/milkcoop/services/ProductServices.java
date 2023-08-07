package com.milkcoop.services;

import com.milkcoop.data.model.vo.ProducerVO;
import com.milkcoop.data.model.vo.ProductVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductServices {

    ProductVO create(ProductVO productVO);

    ProductVO update(ProductVO productVO);

    void delete(Long id);

    Page<ProductVO> find(Pageable pageable,ProductVO request);

    ProductVO findById(Long id);

}
