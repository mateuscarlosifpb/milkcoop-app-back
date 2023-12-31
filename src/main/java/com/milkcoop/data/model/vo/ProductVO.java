package com.milkcoop.data.model.vo;

import com.milkcoop.data.model.enums.ProductName;
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
@AllArgsConstructor
@Builder
public class ProductVO extends RepresentationModel<ProductVO> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private ProductName productName;
    private BigDecimal price;
    private BigDecimal quantity;
    private LocalDate dataRegister;

}
