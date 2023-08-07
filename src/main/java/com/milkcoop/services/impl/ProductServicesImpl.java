package com.milkcoop.services.impl;

import com.milkcoop.data.model.Product;
import com.milkcoop.data.model.vo.ProductVO;
import com.milkcoop.repository.ProductRepository;
import com.milkcoop.services.ProductServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServicesImpl implements ProductServices {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductVO create(ProductVO productVO) {
        var entity = toConvert(productVO);
        return toConvert(productRepository.save(entity));
    }

    @Override
    public ProductVO update(ProductVO productVO) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Page<ProductVO> find(Pageable pageable, ProductVO request) {
        if (request.getDataRegister() != null && request.getPrice() == null) {
            var page = productRepository.findByDataRegister(pageable, request.getDataRegister());
            return page.map(this::convertToProductVO);
        } else if (request.getDataRegister() == null && request.getPrice() != null) {
            var page = productRepository.findByPrice(pageable, request.getPrice());
            return page.map(this::convertToProductVO);
        } else if (request.getDataRegister() != null && request.getPrice() != null) {
            var page = productRepository.findByPriceAndDataRegister(pageable, request.getPrice(), request.getDataRegister());
            return page.map(this::convertToProductVO);
        } else {
            var page = productRepository.findAll(pageable);
            return page.map(this::convertToProductVO);
        }
    }

    @Override
    public ProductVO findById(Long id) {
        return toConvert(productRepository.findById(id).orElseThrow());

    }

    private ProductVO convertToProductVO(Product product) {
        return toConvert(product);

    }

    private ProductVO toConvert(Product product) {
        ProductVO productVO = modelMapper.map(product, ProductVO.class);
        return productVO;
    }

    private Product toConvert(ProductVO productVO) {
        return modelMapper.map(productVO, Product.class);

    }
}
