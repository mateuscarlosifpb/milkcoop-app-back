package com.milkcoop.services.impl;

import com.milkcoop.data.model.InventorySale;
import com.milkcoop.data.model.vo.InventorySaleVO;
import com.milkcoop.exceptions.ResourceNotFoundException;
import com.milkcoop.repository.InventorySaleRepository;
import com.milkcoop.repository.ProductRepository;
import com.milkcoop.services.InventorySaleServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class InventorySaleServicesImpl implements InventorySaleServices {
    @Autowired
    private InventorySaleRepository inventorySaleRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public InventorySaleVO create(InventorySaleVO inventorySaleVO) {
        var entity = toConvert(inventorySaleVO);
        var product = productRepository.findById(entity.getProduct().getId()).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrato!"));
        product.setQuantity(product.getQuantity().subtract(entity.getQuantity()));
        entity.setProduct(product);
        return toConvert(inventorySaleRepository.save(entity));
    }

    @Override
    public InventorySaleVO update(InventorySaleVO inventorySaleVO) {
        return null;
    }

    @Override
    public void delete(Long id) {
        var entity = inventorySaleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to delete in this scenario"));
        inventorySaleRepository.delete(entity);
    }

    @Override
    public Page<InventorySaleVO> find(Pageable pageable, LocalDate dataRegister) {
        if(dataRegister != null){
            var page = inventorySaleRepository.findByDataRegister(pageable, dataRegister);
            return page.map(this::convertToInventorySaleVO);
        }else{
            var page = inventorySaleRepository.findAll(pageable);
            return page.map(this::convertToInventorySaleVO);
        }

    }

    @Override
    public InventorySaleVO findById(Long id) {
        return toConvert(inventorySaleRepository.findById(id).orElseThrow());
    }

    private InventorySaleVO convertToInventorySaleVO(InventorySale inventorySale) {
        return toConvert(inventorySale);

    }

    private InventorySaleVO toConvert(InventorySale inventorySale) {
        return modelMapper.map(inventorySale, InventorySaleVO.class);

    }

    private InventorySale toConvert(InventorySaleVO inventorySaleVO) {
        return modelMapper.map(inventorySaleVO, InventorySale.class);

    }
}
