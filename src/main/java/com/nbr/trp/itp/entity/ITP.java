package com.nbr.trp.itp.entity;

import com.nbr.trp.common.entity.Address;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "itp")
public class ITP {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uniqueidentifier default newid()")
    public String userid;

    @Column(name = "itp_name",nullable = false)
    public String itpName;

    @Column(name = "agent_id",nullable = false)
    public String agentId="000000000000";

    @Column(name = "tin_no",nullable = false)
    public String tinNo;

    @Column(name = "type",nullable = false)
    public String type;

    @Column(name = "itp_dob")
    public Date itpDob;

    @Column(name = "itp_mobile_no")
    public String itpMobileNo;

    @Column(name = "itp_photo")
    public String itpPhoto;

    @Column(name = "nid")
    public String nid;

    @Column(name="itp_id", nullable = false)
    public String itpId;

    @Column(name="lic_no", nullable = false)
    public String licNo;

    @Column(name="name_bar_assoc")
    public String nameBarAssoc;

    @Column(name="reg_bar_assoc")
    public String regBarAssoc;

    @Column(name = "cert_pass")
    public Date certPass;

    @Column(name = "cert_issue")
    public Date certIssue;

    @Column(name = "cert_expiry")
    public Date certExpiry;

    @Column(name="file_path", nullable = false)
    public String filePath;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "itp_address",
            joinColumns = @JoinColumn(name = "itp_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private Set<Address> re_address = new HashSet<>();

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "representative_bankinformation",
//            joinColumns = @JoinColumn(name = "representative_id"),
//            inverseJoinColumns = @JoinColumn(name = "bank_id")
//    )
//    private Set<BankInformationDetails> re_bankinformation = new HashSet<>();


    public ITP(String userid, String itpName, String agentId, String tinNo, String type, Date itpDob, String itpMobileNo, String itpPhoto, String nid, String itpId, String licNo, Date certPass, Date certIssue, Date certExpiry, String filePath, Set<Address> re_address) {
        this.userid = userid;
        this.itpName = itpName;
        this.agentId = agentId;
        this.tinNo = tinNo;
        this.type = type;
        this.itpDob = itpDob;
        this.itpMobileNo = itpMobileNo;
        this.itpPhoto = itpPhoto;
        this.nid = nid;
        this.itpId = itpId;
        this.licNo = licNo;
        this.certPass = certPass;
        this.certIssue = certIssue;
        this.certExpiry = certExpiry;
        this.filePath = filePath;
        this.re_address = re_address;
    }
}

