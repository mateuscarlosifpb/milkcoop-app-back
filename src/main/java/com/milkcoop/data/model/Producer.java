package com.milkcoop.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "producers")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@SequenceGenerator(name = "id_producer", sequenceName = "id_producer", allocationSize = 1)
public class Producer implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id_producer")
    @Column(name = "id")
    private Long id;
    @Column(name = "cpf", unique = true)
    private String cpf;
    @Column(name = "full_name")
    private String fullName;
    @Embedded
    private Address address;
    @Column(name = "telephone")
    private String telephone;
    @OneToMany(mappedBy = "producer", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProducerDelivery> producerDeliveries;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cooperativa")
    private Cooperative cooperative;

}
