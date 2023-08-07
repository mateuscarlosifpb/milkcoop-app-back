package com.milkcoop.data.model.vo;

import com.milkcoop.data.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class InventorySaleVO extends RepresentationModel<InventorySaleVO> implements Serializable {
    private Long id;
    private BigDecimal price;
    private BigDecimal quantity;
    private ProductVO product;
    private LocalDate dataRegister;
}
