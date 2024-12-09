package com.nbr.trp.user.repository;

import com.nbr.trp.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface  RoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findByName(String name);
}