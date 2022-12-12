package com.milkcoop.services;

import java.text.ParseException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.milkcoop.data.model.vo.CooperativeVO;

public interface CooperativeServices {

	CooperativeVO create(CooperativeVO cooperativeVO) throws ParseException;

	CooperativeVO update(CooperativeVO cooperativeVO);

	void delete(Long id);

	Page<CooperativeVO> find(Pageable pageable);

	CooperativeVO findById(Long id);
}
