package com.nbr.trp.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "taxesbarassoc")
public class TaxesBarAssociation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="barid")
    private long barid;

    @Column(name = "name")
    private String name;
}
