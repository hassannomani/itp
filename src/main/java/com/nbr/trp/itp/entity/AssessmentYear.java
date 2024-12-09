package com.nbr.trp.itp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "assessment_year")

public class AssessmentYear {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uniqueidentifier default newid()")
    public String yearid;

    @Column(name = "year",nullable = false)
    public String year;

    @Column(name = "created")
    @CreationTimestamp
    public Date created;
}
