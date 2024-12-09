package com.nbr.trp.certificate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "examinee_tin",nullable = false, unique = true)
    public String examineeTin;

    @Column(name = "examinee_nid",nullable = false)
    public String examineeNid;

    @Column(name = "examinee_license",nullable = false)
    public String examineeLicense;

    @Column(name = "examinee_mobile",nullable = false)
    public String examineeMobile;

    @Column(name="examinee_certno")
    public String examineeCertno;

    @Column(name="examinee_certserial")
    public String examineeCertserial;

    public Certificate(String certid, String examineeTin, String examineeNid, String examineeLicense, String examineeMobile, String examineeCertno, String examineeCertserial) {
        this.certid = certid;
        this.examineeTin = examineeTin;
        this.examineeNid = examineeNid;
        this.examineeLicense = examineeLicense;
        this.examineeMobile = examineeMobile;
        this.examineeCertno = examineeCertno;
        this.examineeCertserial = examineeCertserial;
    }
}
