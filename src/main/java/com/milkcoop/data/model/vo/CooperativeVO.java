package com.milkcoop.data.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CooperativeVO extends RepresentationModel<CooperativeVO> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String telephone;
	private AddressVO addressVO;
	private List<InventoryVO> inventory = new ArrayList<>();
	private List<ProducerVO> producers = new ArrayList<>();
	private List<UserVO> Employees = new ArrayList<>();

}
