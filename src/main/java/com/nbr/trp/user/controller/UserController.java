package com.nbr.trp.user.controller;

import com.nbr.trp.common.service.CommonService;
import com.nbr.trp.log.LoggerController;
import com.nbr.trp.user.entity.ApproveITPView;
import com.nbr.trp.user.entity.Role;
import com.nbr.trp.user.entity.User;
import com.nbr.trp.user.repository.RoleRepository;
import com.nbr.trp.user.response.MessageResponse;
import com.nbr.trp.user.service.UserDetailsImpl;
import com.nbr.trp.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 4800)
@RestController
@RequestMapping("/api/v1/users")
//@RequestMapping("/api/test")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CommonService commonService;

    @Autowired
    LoggerController loggerController;


    //@CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/roles")
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllRoles() {
        try{

            List<Role> roles = userService.getRoles();
            return ResponseEntity.ok(roles);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addUser(HttpServletRequest request, @RequestBody User user) {
        System.out.println(user.getRoles());

        try{
       /*     System.out.println("---------------------------------------------");
            System.out.println(user.getRoles());
            Set<Role> roletobeAdded = new HashSet<Role>();
            Role singlerole = roleRepository.findByName(String.valueOf(user.getRoles())).orElse(null);
            System.out.println(singlerole);
            roletobeAdded.add(singlerole);
            System.out.println(roletobeAdded);
            user.setRoles(roletobeAdded);*/
            String ip = commonService.getIPAddress(request);
            String username= user.getUsername();
            String email= user.getEmail();
            String password= user.getPassword();
            String role= String.valueOf(user.getRoles());
            if(username!=""&&email!=""&&password!=""&&role!="")
            {
                User user1 = userService.saveUser(user);
                loggerController.UserAddition(username,role,ip);
                return ResponseEntity.ok(user1);

            }else{
                loggerController.UserAdditionFailed(username,role,ip);
                return ResponseEntity.status(400).body(new MessageResponse("Required Fields Missing"));
            }

        }catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers(HttpServletRequest request) {
        String ip = commonService.getIPAddress(request);
        try{
            List<User> all_users = userService.getAllUsers();
            loggerController.ListGeneration("","All Users","Admin",ip);
            return ResponseEntity.ok(all_users);
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/user/{username}")
    public ResponseEntity<?> getAUser(@PathVariable String username) {
        System.out.println(username);
        try{
            Optional<User> user = userService.getUserByUsername(username);
            return ResponseEntity.ok(user);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/pending-all")
    @PreAuthorize("hasAnyRole('ADMIN','NBR')")
    public ResponseEntity<?> getAllPendingUsers(HttpServletRequest request) {
        String ip = commonService.getIPAddress(request);
        try{
            List<ApproveITPView> users = userService.getAllPendingUsers();
            loggerController.ListGeneration("","Pending Users","Admin",ip);
            return ResponseEntity.ok(users);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/approve/{uuid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> approveUser(HttpServletRequest request,@PathVariable String uuid) {
        String ip = commonService.getIPAddress(request);

        try{
            User user = userService.approveRepuser(uuid);
            loggerController.UserApproval(uuid, ip);
            return ResponseEntity.ok(user);
        }catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/tinapprove/{username}")
    @PreAuthorize("hasAnyRole('ADMIN','NBR')")
    public ResponseEntity<?> approveUserByTin(HttpServletRequest request,@PathVariable String username) {
        String ip = commonService.getIPAddress(request);

        try{
            User user = userService.approveRepUserByTin(username);
            loggerController.UserApproval(username, ip);

            return ResponseEntity.ok(user);
        }catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/reject/{uuid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> rejectUser(HttpServletRequest request, @PathVariable String uuid) {
        String ip = commonService.getIPAddress(request);
        try{
            User user = userService.rejectRepuser(uuid);
            loggerController.UserRejection(uuid, ip);
            return ResponseEntity.ok(user);
        }catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

   // @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/blocked")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> blockeduser(HttpServletRequest request) {
        String ip = commonService.getIPAddress(request);

        try{
            List<User> user = userService.getAllBlockedUsers();
            loggerController.ListGeneration("","Blocked Users","Admin",ip);
            return ResponseEntity.ok(user);
        }catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/unblock/{tin}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> unblock(HttpServletRequest request, @PathVariable String tin) {
        String ip = commonService.getIPAddress(request);

        try{
            loggerController.UserApproval(tin,ip);
            User user = userService.approveRepUserByTin(tin);
            return ResponseEntity.ok(user);
        }catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/denied")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> denieduser(HttpServletRequest request) {
        String ip = commonService.getIPAddress(request);
        try{
            List<User> user = userService.getAllDeniedUsers();
            loggerController.ListGeneration("","Denied Users","Admin",ip);

            return ResponseEntity.ok(user);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }


    //@CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/register")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerUser(HttpServletRequest request, @RequestBody User user) {
        String ip = commonService.getIPAddress(request);
        try{
            User user1 = userService.registerUser(user);
            loggerController.RegistrationSuccess(user.getUsername(),ip);
            return ResponseEntity.ok(user1);
        }catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(HttpServletRequest request, @RequestBody User user) {
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails1 = getDetails();
        try{
            Boolean bool = userService.changePassword(user);
            loggerController.PasswordChange(user.getUsername(),ip);
            return ResponseEntity.ok(bool);
        }catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    private UserDetailsImpl getDetails(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return (UserDetailsImpl) authentication.getPrincipal();
    }

    @GetMapping("/mypassword/{username}")
    public ResponseEntity<?> changePasswordDirect(@PathVariable String username) {
//        String ip = commonService.getIPAddress(request);
//        UserDetailsImpl userDetails1 = getDetails();
        try{
            //Boolean bool = userService.changePassword(user);
            User u = userService.getUserByUsername(username).orElse(null);
            Boolean bool = userService.myPassChange(u,"1234");
            //loggerController.PasswordChange(user.getUsername(),ip);
            return ResponseEntity.ok(bool);
        }catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }



}
