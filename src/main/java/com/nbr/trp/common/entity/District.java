package com.nbr.trp.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "district")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="division_id",nullable = false)
    private String divisionId;

    @Column(name="district_id",nullable = false,unique = true)
    private String districtId;
    @Column(name = "name")
    private String name;

    public District(String divId, String districtId, String name){
        this.divisionId = divId;
        this.districtId = districtId;
        this.name = name;
    }
}
