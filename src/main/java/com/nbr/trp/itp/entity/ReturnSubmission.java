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
@Table(name = "returnsubmission")
public class ReturnSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uniqueidentifier default newid()")
    public String submissionid;

    @Column(name = "itp_tin",nullable = false)
    public String itpTin;

    @Column(name = "taxpayer",nullable = false)
    public String taxpayer;

    @Column(name = "mobile",nullable = false)
    public String mobile;

    @Column(name = "assessment_year",nullable = false)
    public String assessmentYear;

    @Column(name = "created")
    @CreationTimestamp
    public Date created;
}
