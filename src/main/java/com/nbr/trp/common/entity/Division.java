package com.nbr.trp.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "division")
public class Division {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="div_id",nullable = false,unique = true)
    private String divId;

    @Column(name = "name")
    private String name;

    public Division(String id, String name){
        this.divId = id;
        this.name = name;
    }

}
