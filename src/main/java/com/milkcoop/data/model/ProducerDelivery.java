package com.milkcoop.data.model;

import com.milkcoop.data.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "producer_deliveries")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@SequenceGenerator(name = "id_producer_delivery", sequenceName = "id_producer_delivery", allocationSize = 1)

public class ProducerDelivery implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id_producer_delivery")
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_producer")
	private Producer producer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_product")
	private Product product;

	@Column(name = "quantity")
	private BigDecimal quantity;

	@Column(name = "data_register")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate dataRegister;

	@Column(name = "statusPagamento")
	@Enumerated(value = EnumType.STRING)
	private PaymentStatus status;

}
