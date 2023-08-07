package com.milkcoop.data.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.milkcoop.data.model.InventorySale;
import com.milkcoop.data.model.Product;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class InventoryVO extends RepresentationModel<InventoryVO> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private BigDecimal quantity;

}
