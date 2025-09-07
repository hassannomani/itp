package com.nbr.trp.action.controller;

import com.nbr.trp.action.entity.Action;
import com.nbr.trp.action.service.ActionService;
import com.nbr.trp.common.service.CommonService;
import com.nbr.trp.log.LoggerController;
import com.nbr.trp.user.entity.Role;
import com.nbr.trp.user.response.MessageResponse;
import com.nbr.trp.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/action")
public class ActionController {
    @Autowired
    ActionService actionService;

    @Autowired
    UserService userService;

    @Autowired
    LoggerController loggerController;

    @Autowired
    CommonService commonService;



    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ADMIN','NBR')")
    public ResponseEntity<?> saveAction(HttpServletRequest request, @RequestBody Action act) {
        String ip = commonService.getIPAddress(request);

        try{
            Action action = actionService.save(act);
            String actionType = action.getActionType();
            if(actionType.equals("DENY")){
                userService.rejectRepuser(action.getReceiver());
                loggerController.ActionSave(action.getReceiver(),"DENY",ip);
            }
            else if(actionType.equals("CANCEL")){
                userService.blockRepuser(action.getReceiver());
                loggerController.ActionSave(action.getReceiver(),"CANCEL",ip);
            }
            else if(actionType.equals("SUSPEND")){
                userService.suspendRepuser(action.getReceiver());
                loggerController.ActionSave(action.getReceiver(),"SUSPEND",ip);
            }
            return ResponseEntity.ok(action);
        }catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/status/{username}")
    public ResponseEntity<?> getAction(@PathVariable String username) {
        try{
           List<Action>  action = actionService.getActionTrp(username);
            return ResponseEntity.ok(action);
        }catch(Exception e){
            System.out.println(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/message/{id}")
    public ResponseEntity<?> getMessage(@PathVariable String id) {
        try{
            Action  action = actionService.getAction(id);
            actionService.markRead(id);

            return ResponseEntity.ok(action);
        }catch(Exception e){
            System.out.println(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/blocked")
    public ResponseEntity<?> getBlockedUsers(HttpServletRequest request) {
        String ip = commonService.getIPAddress(request);
        try{
            List<Action>  action = actionService.getActionByType("BLOCK");
            loggerController.ListGeneration("","Blocked Users","Admin",ip);
            return ResponseEntity.ok(action);
        }catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }


}
