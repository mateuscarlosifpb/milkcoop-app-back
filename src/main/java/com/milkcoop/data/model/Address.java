package com.milkcoop.data.model;

import java.io.Serializable;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
public class Address{
	@Column(name = "street")
	private String street;
	@Column(name = "number")
	private String number;
	@Column(name = "cep")
	private String cep;
	@Column(name = "city")
	private String city;
	@Column(name = "state")
	private String state;

}
