package com.nbr.trp.certificate.entity;

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
@Table(name = "certificates")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uniqueidentifier default newid()")
    public String certid;

    @Column(name = "tin",nullable = false, unique = true)
    public String tin;

    @Column(name = "nid",nullable = false)
    public String nid;

    @Column(name = "mobile",nullable = false)
    public String mobile;

    @Column(name = "name")
    public String name;

    @Column(name = "category",nullable = false)
    public String category;

    @Column(name = "registration_no")
    public String registrationNo;

    @Column(name = "registration_date")
    public Date registrationDate;

    @Column(name = "taxesbar_assoc")
    public String taxesbarAssoc;

    @Column(name = "taxesbar_assoc_reg")
    public String taxesbarAssocReg;

    @Column(name = "created")
    @CreationTimestamp
    public Date created;


}
