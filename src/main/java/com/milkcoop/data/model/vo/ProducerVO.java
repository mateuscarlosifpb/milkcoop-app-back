package com.milkcoop.data.model.vo;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProducerVO extends RepresentationModel<ProductVO> implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private Long key;
	private String userName;
	private String fullName;
	private AddressVO address;
	private String telephone;

}
