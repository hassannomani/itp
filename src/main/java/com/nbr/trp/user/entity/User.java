package com.nbr.trp.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uniqueidentifier default newid()")
    private String uuid;


    @Column(name = "username",nullable = false,unique = true)
    public String username;

    @Column(name = "password")
    public String password;

    @Column(name = "first_name")
    public String firstName;

    @Column(name = "last_name")
    public String lastName;

    @Column(name = "email")
    public String email;

    @Column(name = "added_by")
    public String addedBy;

    @Column(name = "added_date")
    @CreationTimestamp
    public Date addedDate;

    @Column(name = "added_from_ip")
    public String addedFromIP;

    @Column(name = "updated_by")
    public String updatedBy;

    @Column(name = "updated_date")
    @UpdateTimestamp
    public Date  updatedDate;

    @Column(name = "updated_from_ip")
    public String updatedFromIP;

    @Column(name = "status")
    public String status;

    @Column(name = "photo")
    public String photo;

    @ManyToMany(/*cascade = CascadeType.ALL,*/ fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User(String uuid,String username, String password, String Firstname, String Lastname, String email, String AddedBy, String AddedFromIp, String Status, Set<Role> roles, String photo) {
        this.uuid = uuid;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = Firstname;
        this.lastName = Lastname;
        this.addedBy = AddedBy;
        this.addedFromIP = AddedFromIp;
        this.roles = roles;
        this.status = Status;
        this.photo = photo;
    }

}
