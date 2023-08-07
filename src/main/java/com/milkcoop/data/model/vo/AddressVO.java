package com.milkcoop.data.model.vo;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressVO extends RepresentationModel<AddressVO> implements Serializable {

	private String street;
	private String number;
	private String cep;
	private String city;
	private String state;
}
