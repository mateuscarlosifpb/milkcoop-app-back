package com.milkcoop.data.model;

import com.milkcoop.data.model.enums.ProductName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@SequenceGenerator(name = "id_product", sequenceName = "id_product", allocationSize = 1)

public class Product implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id_product")
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    @Enumerated(value = EnumType.STRING)
    private ProductName productName;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "quantity")
    private BigDecimal quantity;
    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InventorySale> inventorySales;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cooperativa")
    private Cooperative cooperative;
    @Column(name = "data_register")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dataRegister;

}
