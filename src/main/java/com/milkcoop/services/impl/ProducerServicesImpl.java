package com.milkcoop.services.impl;

import com.milkcoop.data.model.Producer;
import com.milkcoop.data.model.vo.AddressVO;
import com.milkcoop.data.model.vo.ProducerVO;
import com.milkcoop.exceptions.ResourceNotFoundException;
import com.milkcoop.repository.ProducerRepository;
import com.milkcoop.services.ProducerServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProducerServicesImpl implements ProducerServices {
    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProducerVO create(ProducerVO producerVO) {
        var entity = toConvert(producerVO);
        return toConvert(producerRepository.save(entity));
    }

    @Override
    public ProducerVO update(ProducerVO producerVO) {
        return null;
    }

    @Override
    public void delete(Long id) {
        var entity = producerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to delete in this scenario"));
        producerRepository.delete(entity);
    }

    @Override
    public Page<ProducerVO> find(Pageable pageable, ProducerVO request) {
        var page = producerRepository.findAll(pageable, request.getFullName(), request.getTelephone());
        return page.map(this::convertToProducerVO);
    }

    @Override
    public ProducerVO findById(Long id) {
        return toConvert(producerRepository.findById(id).orElseThrow());
    }

    private ProducerVO convertToProducerVO(Producer producer) {
        return toConvert(producer);

    }

    private ProducerVO toConvert(Producer producer) {
        ProducerVO producerVO = modelMapper.map(producer, ProducerVO.class);
        return producerVO;

    }

    private Producer toConvert(ProducerVO producerVO) {
        return modelMapper.map(producerVO, Producer.class);

    }
}
