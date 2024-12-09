package com.nbr.trp.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "thana")
public class Thana {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;


    @Column(name="division_id")
    private long divisionId;

    @Column(name="division_name")
    private String divisionName;

    @Column(name="district_id")
    private String districtId;

    @Column(name="district_name")
    private String districtName;

    @Column(name="thana_id",nullable = false)
    private String thanaId;

    @Column(name = "name")
    private String name;

    public Thana(long id, long divisionId, String divisionName, String districtId, String districtName, String thanaId, String name) {
        this.id = id;
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.thanaId = thanaId;
        this.name = name;
    }
}
