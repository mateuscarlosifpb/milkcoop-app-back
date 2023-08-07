package com.milkcoop.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "cooperatives")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "seq_id_cooperative", sequenceName = "seq_id_cooperative", allocationSize = 1)
public class Cooperative implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_id_cooperative")
    @Column(name = "id_cooperative")
    private Long id;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "telephone", unique = true)
    private String telephone;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "cooperative", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Product> products;
    @OneToMany(mappedBy = "cooperative", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Producer> producers;
    @OneToMany(mappedBy = "cooperative", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<User> Employees;

}
