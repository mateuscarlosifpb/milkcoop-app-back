package com.milkcoop.data.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cooperatives")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cooperative implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", unique = true)
	private String name;

	@Column(name = "telephone", unique = true)
	private String telephone;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cooperative_id")
	private Address address;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "cooperative_has_inventory", joinColumns = {
			@JoinColumn(name = "cooperative_id") }, inverseJoinColumns = { @JoinColumn(name = "inventory_id") })
	private List<Inventory> inventory;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "cooperative_has_producer", joinColumns = {
			@JoinColumn(name = "cooperative_id") }, inverseJoinColumns = { @JoinColumn(name = "producer_id") })
	private List<Producer> producers;

	@OneToMany(mappedBy = "User", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	private List<User> Employees;

}
