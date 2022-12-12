package com.milkcoop.data.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.milkcoop.data.model.enums.ProductName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVO extends RepresentationModel<ProductVO> implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	@JsonProperty("id")
	private Long key;
	private ProductName productName;
	private BigDecimal price;
	private LocalDate dataCadastro;

}
