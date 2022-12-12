package com.milkcoop.services.impl;

import java.text.ParseException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.milkcoop.data.model.Address;
import com.milkcoop.data.model.Cooperative;
import com.milkcoop.data.model.vo.CooperativeVO;
import com.milkcoop.exceptions.ResourceNotFoundException;
import com.milkcoop.repository.CooperativeRepository;
import com.milkcoop.services.CooperativeServices;

@Service
public class CooperativeServicesImpl implements CooperativeServices {

	@Autowired
	private CooperativeRepository repository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CooperativeVO create(CooperativeVO cooperativeVO) throws ParseException {
		var entity = toConvert(cooperativeVO);
		return toConvert(repository.save(entity));

	}

	@Override
	public CooperativeVO update(CooperativeVO cooperativeVO) {
		var entity = repository.findById(cooperativeVO.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("Not records found for thins ID"));
		entity.setName(cooperativeVO.getName());
		entity.setAddress(modelMapper.map(cooperativeVO.getAddress(), Address.class));
		entity.setTelephone(cooperativeVO.getTelephone());
		return toConvert(repository.save(entity));

	}

	@Override
	public void delete(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Unable to delete in this scenario"));
		repository.delete(entity);
	}

	@Override
	public Page<CooperativeVO> find(Pageable pageable) {
		var page = repository.findAll(pageable);
		return page.map(this::convertToCooperativeVO);
	}

	@Override
	public CooperativeVO findById(Long id) {
		return toConvert(repository.findById(id).orElseThrow());
	}

	private CooperativeVO convertToCooperativeVO(Cooperative cooperative) {
		return toConvert(cooperative);

	}

	private CooperativeVO toConvert(Cooperative cooperative) {
		return modelMapper.map(cooperative, CooperativeVO.class);

	}

	private Cooperative toConvert(CooperativeVO cooperativeVO) throws ParseException {
		return modelMapper.map(cooperativeVO, Cooperative.class);

	}

}
