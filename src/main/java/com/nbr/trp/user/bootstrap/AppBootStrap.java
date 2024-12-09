package com.nbr.trp.user.bootstrap;

import com.nbr.trp.user.entity.ERole;
import com.nbr.trp.user.entity.Role;
import com.nbr.trp.user.entity.User;
import com.nbr.trp.user.repository.RoleRepository;
import com.nbr.trp.user.repository.UserRepository;
import com.nbr.trp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.nbr.trp.user.entity.ERole.ROLE_ADMIN;
@Component
public class AppBootStrap implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;


    @Autowired
    public AppBootStrap(UserRepository userRepository, RoleRepository roleRepository, UserService userService){

        this.userService=userService;
        this.roleRepository=roleRepository;
        this.userRepository=userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            if (roleRepository.findAll().isEmpty()) {
                Role role = new Role();
                role.setName(ERole.ROLE_ADMIN.name());
                roleRepository.save(role);
                Role role2 = new Role();
                role2.setName(ERole.ROLE_VIEWER.name());
                roleRepository.save(role2);
                Role role3 = new Role();
                role3.setName(ERole.ROLE_NBR.name());
                roleRepository.save(role3);
                Role role4 = new Role();
                role4.setName(ERole.ROLE_ITP.name());
                roleRepository.save(role4);

                Role role5 = new Role();
                role5.setName(ERole.ROLE_API.name());
                roleRepository.save(role5);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Optional<User> user1 = userRepository.findByUsername("000000000000");
        if (user1.isEmpty()){
            Set<Role> roleadmin = new HashSet<Role>();
            Role adminsingle = roleRepository.findByName(String.valueOf(ROLE_ADMIN)).orElse(null);
            roleadmin.add(adminsingle);
            User userX = new User(UUID.randomUUID().toString(), "000000000000", "admin", "Admin" ,"User","admin@itp.gov.bd","SYSTEM", "127.0.0.1","1",roleadmin,"");
            User saved = userService.saveUser(userX);
            //System.out.println(saved);

        }

//        Optional<User> user2 = Optional.ofNullable(userRepository.findByUsername("admin"));
//        if (user2.isEmpty()){
//            User userY = new User(UUID.randomUUID().toString(), "admin", "admin", "ROLE_ADMIN", "000000000000" ,"admin@nbr.gov.bd","1","1", Date.valueOf("1970-01-01"));
//            User usera = userService.saveUser(userY);
//            activationService.saveActivationBootstrap(usera);
//
//        }
    }
}
