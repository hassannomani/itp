package com.nbr.trp.log;

import com.nbr.trp.user.controller.AuthController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;


@RestController
public class LoggerController {
    private static final Logger logger =  LoggerFactory.getLogger(LoggerController.class);

    public void Login(String uuid, String ip){
        logger.info(uuid + " User is logging in from ip: "+ip);
    }

    public void Logout(String uuid){
        MDC.clear();
        MDC.put("event_type","logout");
        logger.info(uuid + " User is logging out");
        MDC.remove("event_type");
    }

    public void LoginFailedDeny(String uuid, String ip){
        logger.info(uuid + " Denied User is logging out fromm ip:"+ip);

    }

    public void LoginFailedSuspended(String uuid, String ip){
        logger.info(uuid + " Suspended User is logging out from ip: "+ip);
    }

    public void LoginFailedBlocked(String uuid, String ip){
        logger.info(uuid + " Blocked User is logging out from ip: "+ip);
    }

    public void LoginFailedUnApproved(String uuid, String ip){
        logger.info(uuid + " Un Approved User is logging out from ip "+ip);
    }

    public void LoginError(String uuid, String ip){
        logger.info(uuid + " User is facing login error from ip: "+ip);
    }

    public void ReRegistration(String uuemail, String ip){
        logger.info(uuemail+ " is trying for re registration from ip: "+ip);
    }

    public void AdminRoleTry(String username, String ip){
        logger.warn(username+ " is trying for admin role from ip: "+ip);
    }

    public void RoleErrorTry(String username, String role, String ip){
        logger.warn(username+ " is trying for role "+ role+" from ip: "+ip);
    }

    public void RegistrationSuccess(String username, String ip){
        logger.info(username+ " has successfully registered from ip: "+ip);
    }

    public void ErrorHandler(Exception e){
        logger.error("Error Occurred "+e);
    }

    public void UnAuthorized(String ip){
        logger.error("Unauthorized attempt from "+ip);
    }

    public void IncomingRequest(String ip, String url){
        logger.info("Incoming request from "+ip+ " for accessing the url "+url);
    }

    public void UserAddition(String tin, String role, String ip){
        logger.info("User with tin "+tin+ " and role "+role+" is being added by admin from ip: "+ip);
    }

    public void UserAdditionFailed(String tin, String role, String ip){
        logger.info("User with tin "+tin+ " and role "+role+" is being attempted from ip: "+ip);
    }

    public void UserApproval(String uuid, String ip){
        logger.info("User with tin/uuid "+uuid+ " is being added by admin from ip: "+ip);
    }

    public void UserRejection(String uuid, String ip){
        logger.info("User with tin/uuid "+uuid+ " is being rejected by admin from ip: "+ip);
    }

    public void ListGeneration(String uname_id, String list, String role, String ip){
        MDC.put("event_type","list");
        logger.info("User with tin/uuid "+uname_id+ "and role "+role+ " is retrieving list of "+list+ " from ip: "+ip,"list");
        MDC.remove("event_type");

    }

    public void ActionSave(String uname_id, String type, String ip){
        logger.info("User with tin/uuid "+uname_id+ " faced action "+type+ " by admin from ip: "+ip);
    }

    public void CertificateBulk(String ip){
        logger.info("Bulk certificate is being uploaded by admin from ip: "+ip);
    }

    public void CertificateDuplicacyCheck(String ip){
        logger.info("Bulk certificate is being checked for duplicate by admin from ip: "+ip);
    }

    public void CertificateCheck(String ip){
        logger.info("Certificate is being checked from ip: "+ip);
    }

    public void OTPRequest(String ip){
        logger.info("OTP is being requested from ip : "+ip);
    }

    public void OTPValidate(String ip){
        logger.info("OTP is being validated from ip : "+ip);
    }

    public void ITPIndividualRetrival(String id, String trp, String ip){
        logger.info("ITP  with id "+trp+" is being requested by "+id+" from ip : "+ip);
    }

    public void LedgerRequest(String ip){
        logger.info("Ledger Request initiated from ip : "+ip);
    }


    public void LedgerIndividual(String ip, String id, String viewer){
        logger.info("Ledger with id:"+id+ " is viewed by: "+viewer+" initiated from ip : "+ip);
    }

    public void InfoSave(String info, String ip){
        logger.info(info+" being saved from ip: "+ip);
    }

    public void PasswordChange(String id, String ip){
        logger.info("User with tin "+id+" is requesting to change password from ip : "+ip);
    }

    public void ledgerSaved(String itp, String taxpayer){
        logger.info("Ledger of ITP with tin "+itp+" is being saved for taxpayer : "+taxpayer);

    }

    public void ledgerDuplicate(String itp, String taxpayer, String assmnt_year){
        logger.info("Ledger of ITP with tin "+itp+" for taxpayer : "+taxpayer+" and assessment year "+assmnt_year+" is duplicate.");
    }

    public void ledgerITPNot(String itp){
        logger.info("Ledger Rejected as ITP with tin "+itp+" not found");
    }

    public void ledgerError(String itp){
        logger.info("Ledger Error for ITP with tin "+itp);
    }



}
