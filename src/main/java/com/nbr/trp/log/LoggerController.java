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
        MDC.clear();
        MDC.put("event_type","login");
        logger.info(uuid + " User is logging in from ip: "+ip);
        MDC.remove("event_type");
    }

    public void Logout(String uuid){
        MDC.clear();
        MDC.put("event_type","logout");
        logger.info(uuid + " User is logging out");
        MDC.remove("event_type");
    }

    public void LoginFailedDeny(String uuid, String ip){
        MDC.clear();
        MDC.put("event_type","login_denied");
        logger.info(uuid + " Denied User is logging out from ip:"+ip);
        MDC.remove("event_type");

    }

    public void LoginFailedSuspended(String uuid, String ip){
        MDC.clear();
        MDC.put("event_type","login_suspended");
        logger.info(uuid + " Suspended User is logging out from ip: "+ip);
        MDC.remove("event_type");
    }

    public void LoginFailedBlocked(String uuid, String ip){
        MDC.clear();
        MDC.put("event_type","login_blocked");
        logger.info(uuid + " Blocked User is logging out from ip: "+ip);
        MDC.remove("event_type");
    }

    public void LoginFailedUnApproved(String uuid, String ip){
        MDC.clear();
        MDC.put("event_type","login_unapproved");
        logger.info(uuid + " Un Approved User is logging out from ip: "+ip);
        MDC.remove("event_type");
    }

    public void LoginError(String uname, String ip){
        MDC.clear();
        MDC.put("event_type","login_error");
        logger.error("Credential mismatched for user "+uname +" from ip: "+ip);
        MDC.remove("event_type");
    }

    public void ReRegistration(String uuemail, String ip){
        MDC.clear();
        MDC.put("event_type","reregistration_error");
        logger.info("Re registration attempt with "+uuemail +" from ip: "+ip);
        MDC.remove("event_type");
    }

    public void AdminRoleTry(String username, String ip){
        MDC.clear();
        MDC.put("event_type","unauth_admin_attempt");
        logger.error("Un authorized attempt with "+username +" from ip: "+ip);
        MDC.remove("event_type");
    }

    public void RoleErrorTry(String username, String role, String ip){
        MDC.clear();
        MDC.put("event_type","unauth_role_attempt");
        logger.error("Un authorized Role attempt from "+username +" from ip: "+ip);
        MDC.remove("event_type");
    }

    public void RegistrationSuccess(String username, String ip){
        MDC.clear();
        MDC.put("event_type","unauth_role_attempt");
        logger.info(username+ " has successfully registered from ip: "+ip);
        MDC.remove("event_type");
    }

    public void ErrorHandler(Exception e){
        logger.error("Error Occurred "+e);
    }

    public void UnAuthorized(String ip){
       // logger.error("Unauthorized attempt from "+ip);
        MDC.clear();
        MDC.put("event_type","unauth_url_jwt_attempt");
        logger.error("Unauthorized attempt from "+ip);
        MDC.remove("event_type");
    }

    public void IncomingRequest(String ip, String url){
        //logger.info("Incoming request from "+ip+ " for accessing the url "+url);
        MDC.clear();
        MDC.put("event_type","url_request");
        logger.info("Incoming request from "+ip+ " for accessing the url "+url);
        MDC.remove("event_type");
    }

    public void UserAddition(String tin, String role, String ip){
        //logger.info("User with tin "+tin+ " and role "+role+" is being added by admin from ip: "+ip);
        MDC.clear();
        MDC.put("event_type","user_addition");
        logger.info("Admin adding "+tin+ " and role "+role+" from ip: "+ip);
        MDC.remove("event_type");
    }

    public void UserAdditionFailed(String tin, String role, String ip){
        //logger.info("User with tin "+tin+ " and role "+role+" is being attempted from ip: "+ip);
        MDC.clear();
        MDC.put("event_type","user_addition_failed");
        logger.info("User addition with tin "+tin+ " and role "+role+" is failed from ip: "+ip);
        MDC.remove("event_type");
    }

    public void UserApproval(String uuid, String ip){
        MDC.clear();
        MDC.put("event_type","user_approval");
        logger.info("User with tin/uuid "+uuid+ " is being added by admin from ip: "+ip);
        MDC.remove("event_type");
    }

    public void UserRejection(String uuid, String ip){
        MDC.clear();
        MDC.put("event_type","user_rejection");
        logger.info("User with tin/uuid "+uuid+ " is being rejected by admin from ip: "+ip);
        MDC.remove("event_type");
    }

    public void ListGeneration(String uname_id, String list, String role, String ip){
        MDC.clear();
        MDC.put("event_type","list");
        logger.info("User with tin/uuid "+uname_id+ "and role "+role+ " is retrieving list of "+list+ " from ip: "+ip,"list");
        MDC.remove("event_type");

    }

    public void ActionSave(String uname_id, String type, String ip){
        MDC.clear();
        MDC.put("event_type","action_save");
        logger.info("User with tin/uuid "+uname_id+ " faced action "+type+ " by admin from ip: "+ip);
        MDC.remove("event_type");
    }

    public void CertificateBulk(String ip){
        MDC.clear();
        MDC.put("event_type","certificate_upload");
        logger.info("Bulk certificate is being uploaded by admin from ip: "+ip);
        MDC.remove("event_type");
    }

    public void CertificateDuplicacyCheck(String ip){
        MDC.clear();
        MDC.put("event_type","certificate_dup_check");
        logger.info("Bulk certificate is being checked for duplicate by admin from ip: "+ip);
        MDC.remove("event_type");
    }

    public void CertificateCheck(String ip){

        MDC.clear();
        MDC.put("event_type","certificate_check");
        logger.info("Certificate is being checked from ip: "+ip);
        MDC.remove("event_type");
    }

    public void OTPRequest(String ip){
        //logger.info("OTP is being requested from ip : "+ip);
        MDC.clear();
        MDC.put("event_type","otp_request");
        logger.info("OTP is being requested from ip : "+ip);
        MDC.remove("event_type");
    }

    public void OTPValidate(String ip){
        MDC.clear();
        MDC.put("event_type","otp_validate");
        logger.info("OTP is being validated from ip : "+ip);
        MDC.remove("event_type");
    }

    public void ITPIndividualRetrival(String id, String trp, String ip){
        MDC.clear();
        MDC.put("event_type","info_retrieval");
        logger.info("ITP  with id "+trp+" is being requested by "+id+" from ip : "+ip);
        MDC.remove("event_type");
    }

    public void LedgerRequest(String ip){
        //logger.info("Ledger Request initiated from ip : "+ip);
        MDC.clear();
        MDC.put("event_type","ledger_save_req");
        logger.info("Ledger Saving Request initiated from ip : "+ip);
        MDC.remove("event_type");
    }


    public void LedgerIndividual(String ip, String id, String viewer){
        MDC.clear();
        MDC.put("event_type","ledger_view");
        logger.info("Ledger with id:"+id+ " is viewed by: "+viewer+" initiated from ip : "+ip);
        MDC.remove("event_type");
    }

    public void InfoSave(String info, String ip){
        MDC.clear();
        MDC.put("event_type","info_save");
        logger.info(info+" being saved from ip: "+ip);
        MDC.remove("event_type");
    }

    public void PasswordChange(String id, String ip){
        MDC.clear();
        MDC.put("event_type","password_change");
        logger.info("User with tin "+id+" is requesting to change password from ip : "+ip);
        MDC.remove("event_type");
    }

    public void ledgerSaved(String itp, String taxpayer){
        MDC.clear();
        MDC.put("event_type","ledger_save");
        logger.info("Ledger of ITP with tin "+itp+" is being saved for taxpayer : "+taxpayer);
        MDC.remove("event_type");

    }

    public void ledgerDuplicate(String itp, String taxpayer, String assmnt_year){
        MDC.clear();
        MDC.put("event_type","ledger_duplicate");
        logger.error("Ledger of ITP with tin "+itp+" for taxpayer : "+taxpayer+" and assessment year "+assmnt_year+" is duplicate.");
        MDC.remove("event_type");
    }

    public void ledgerITPNot(String itp){
        MDC.clear();
        MDC.put("event_type","ledger_not_itp");
        logger.error("Ledger Rejected as ITP with tin "+itp+" not found");
        MDC.remove("event_type");
    }

    public void ledgerError(String itp){

        MDC.clear();
        MDC.put("event_type","ledger_error");
        logger.info("Ledger Error for ITP with tin "+itp);
        MDC.remove("event_type");
    }



}
