package com.nbr.trp.ledger.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "ledger")
public class Ledger {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uniqueidentifier default newid()")
    public String lid;

    @Column(name = "taxpayer_id",nullable = false)
    public String taxpayerId;

    @Column(name = "taxpayer_name",nullable = false)
    public String taxpayerName;

    @Column(name = "paid_amount",nullable = false)
    public String paidAmount;

    @Column(name = "minimum_tax",nullable = false)
    public String minimumTax;

    @Column(name = "payment_rule",nullable = false)
    public String paymentRule;

    @Column(name = "reference_no")
    public String referenceNo;

    @Column(name = "challan_no")
    public String challanNo;

    @Column(name = "assessment_year",nullable = false)
    public String assessmentYear;

    @Column(name = "itp_tin",nullable = false)
    public String itpTin;

    @Column(name = "created_at")
    @CreationTimestamp
    public Timestamp created_at;

    @Column(name = "remarks")
    public String remarks;

}
