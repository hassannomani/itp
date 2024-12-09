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

//    @Column(name = "payment_method",nullable = false)
//    public String paymentMethod;

    @Column(name = "reference_no")
    public String referenceNo;

    @Column(name = "challan_no")
    public String challanNo;

    @Column(name = "assessment_year",nullable = false)
    public String assessmentYear;

    @Column(name = "agent_tin",nullable = false)
    public String agentTin;

    @Column(name = "representative_tin",nullable = false)
    public String representativeTin;

    @Column(name = "agent_commission",nullable = false)
    public Double agentCommission;

    @Column(name = "representative_commission",nullable = false)
    public Double representativeCommission;

    @Column(name = "bill_submitted_ag")
    public String billSubmittedAg;

    @Column(name = "bill_submitted_trp")
    public String billSubmittedTrp;

    @Column(name="bill_date")
    @Temporal(TemporalType.TIMESTAMP)
    private String billDate;

    @Column(name = "year_no")
    public String yearNo;

    @Column(name = "created_at")
    @CreationTimestamp
    public Timestamp created_at;

    @Column(name = "remarks")
    public String remarks;

}
