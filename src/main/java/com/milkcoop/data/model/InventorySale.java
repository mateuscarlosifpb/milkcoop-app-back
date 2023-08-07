package com.milkcoop.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "inventory_sales")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@SequenceGenerator(name = "id_inventory_sale", sequenceName = "id_inventory_sale", allocationSize = 1)
public class InventorySale {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id_inventory_sale")
    @Column(name = "id")
    private Long id;
    @Column(name = "total_price")
    private BigDecimal price;
    @Column(name = "quantity")
    private BigDecimal quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product")
    private Product product;

    @Column(name = "data_register")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dataRegister;
}
