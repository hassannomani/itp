package com.nbr.trp.ledger.controller;

import com.nbr.trp.common.service.CommonService;
import com.nbr.trp.itp.entity.ITP;
import com.nbr.trp.ledger.entity.LedgeAPIResponse;
import com.nbr.trp.ledger.entity.Ledger;
import com.nbr.trp.ledger.entity.LedgerAdminView;
import com.nbr.trp.ledger.service.LedgerService;
import com.nbr.trp.log.LoggerController;
import com.nbr.trp.itp.service.ItpService;
import com.nbr.trp.user.entity.User;
import com.nbr.trp.user.response.MessageResponse;
import com.nbr.trp.user.service.UserDetailsImpl;
import com.nbr.trp.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 4800)
@RestController
@RequestMapping("/api/v1/ledgers")
public class LedgerController {

    @Autowired
    LedgerService ledgerService;

    @Autowired
    CommonService commonService;

    @Autowired
    LoggerController loggerController;

    @Autowired
    ItpService itpService;

    @Autowired
    UserService userService;

    @Value("${own.base-url}")
    private String baseurl;

    @PostMapping("/")
    //@PreAuthorize("hasRole('API')")
    public ResponseEntity<?> saveLedger(HttpServletRequest request, @RequestBody Ledger ld){
        String ip = commonService.getIPAddress(request);
        loggerController.LedgerRequest(ip);
        try{
            List<Ledger> ldPreli = ledgerService
                    .getByAssmentYrAndTin(ld.getAssessmentYear(),ld.getTaxpayerId());
            System.out.println(ld.getAssessmentYear());
            System.out.println(ld.getTaxpayerId());

            User user = userService.checkUsernameByTin(ld.getItpTin());

            if(ldPreli.isEmpty() && user!=null){

                //ledgerService.checkItems(ld);
                Ledger ldg = ledgerService.saveLedger(ld);

                loggerController.ledgerSaved(ld.getItpTin(),ld.getTaxpayerId());
                return ResponseEntity
                        .status(201)
                        .body(
                                new LedgeAPIResponse(
                                        "Data saved",
                                        true,
                                        baseurl+"ledger-representative")
                        );
            }
            else if(!ldPreli.isEmpty() && user!=null){
                System.out.println("bad request");
                loggerController.ledgerDuplicate(ld.getItpTin(),ld.getTaxpayerId(),ld.getAssessmentYear());

                return ResponseEntity
                        .badRequest()
                        .body(
                                new LedgeAPIResponse(
                                        "Duplicate entry not allowed",
                                        false,
                                        "")
                        );

                //                loggerController
                //                        .LedgerRequestDuplicate(
                //                                ip,
                //                                ld.getAgentTin(),
                //                                ld.getRepresentativeTin(),
                //                                ld.getTaxpayerId()
                //                        );

            }
            else if(ldPreli.isEmpty() && user==null){

                loggerController.ledgerITPNot(ld.getItpTin());

                return ResponseEntity
                        .badRequest()
                        .body(new LedgeAPIResponse(
                                "ITP not found",
                                false,
                                "")
                        );

                //                loggerController
                //                        .LedgerRequestDuplicate
                //                                (
                //                                        ip,
                //                                        ld.getAgentTin(),
                //                                        ld.getRepresentativeTin(),
                //                                        ld.getTaxpayerId()
                //                                );
            }
            else{
                loggerController.ledgerError(ld.getItpTin());

                return ResponseEntity
                        .badRequest()
                        .body(
                                new LedgeAPIResponse(
                                        "Bad Request",
                                        false,
                                        "")
                        );
            }

        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new LedgeAPIResponse(e.getMessage(),false,""));
        }
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getLadgers(HttpServletRequest request){
        String ip = commonService.getIPAddress(request);
        try{
            List<LedgerAdminView> ldgs = ledgerService.getAll();
            loggerController.ListGeneration("","All Ledgers", "Admin",ip);
            return ResponseEntity.ok(ldgs);
        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }


    @GetMapping("/itp/{id}")
    public ResponseEntity<?> getLedgersOfARepresentative(HttpServletRequest request,@PathVariable String id){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        try{
            List<Ledger> ldgs = ledgerService.getLedgersOfAnITP(id);
            loggerController.ListGeneration(userDetails.getUsername(),"All ledgers of ITP: "+id, "",ip);
            return ResponseEntity.ok(ldgs);
        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<?> getLedgersOfAdmin(HttpServletRequest request){
        String ip = commonService.getIPAddress(request);
        try{
            List<Ledger> ldgs = ledgerService.getLedgersOfAdmin();
            loggerController.ListGeneration("","All Ledgers", "Admin",ip);
            return ResponseEntity.ok(ldgs);
        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/range/{start}/{end}")

    public ResponseEntity<?> getLedgersRange(HttpServletRequest request,@PathVariable String start, @PathVariable String end){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        try{
            List<Ledger> ldglist = ledgerService.getLedgerWithinRange(start,end);
            loggerController.ListGeneration("","All Ledgers within range "+start+" and:"+end, "Admin",ip);

            return ResponseEntity.ok(ldglist);
        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
    @GetMapping("/range-itp/{itpId}/{start}/{end}")

    public ResponseEntity<?> getLedgersRepresentativeRange(HttpServletRequest request,@PathVariable String itpId, @PathVariable String start, @PathVariable String end){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        try{
            List<Ledger> ldglist = ledgerService.getLedgersOfARepresentativeRange(itpId,start,end);
            loggerController.ListGeneration(userDetails.getUsername(),"All Ledgers of ITP: "+itpId+" within the range "+start+" and "+end,"",ip);
            return ResponseEntity.ok(ldglist);
        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getLedgerById(HttpServletRequest request, @PathVariable String id){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        try{
            Ledger ldg = ledgerService.getByLid(id);
            loggerController.LedgerIndividual(ip,id,userDetails.getUsername());
            return ResponseEntity.ok(ldg);
        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/graph/itp")
    public ResponseEntity<?> getGraphOfTrpForAdmin(HttpServletRequest request){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        try{
            List<Object[]> ob = ledgerService.getGraphData();
            loggerController.ListGeneration(userDetails.getUsername(),"All Data for Graph", "",ip);

            return ResponseEntity.ok(ob);
        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/calc/{num}")
    public ResponseEntity<?> numTest(HttpServletRequest request, @PathVariable String num){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        Double d= Double.parseDouble(num);
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
                System.out.println(df.format(d));
        return ResponseEntity.ok(df.format(d));

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/taxpayeritp/{itp}/{tin}")
    public ResponseEntity<?> getTaxpayerofAnItp(HttpServletRequest request, @PathVariable String itp,@PathVariable String tin){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        try{
            Ledger ldg = ledgerService.getTaxPayerOfAnItp(itp, tin);
            loggerController.LedgerIndividual(ip,itp,userDetails.getUsername());
            return ResponseEntity.ok(ldg);
        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/graph/itp/{tin}")
    public ResponseEntity<?> getGraphItp(HttpServletRequest request, @PathVariable String tin){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        try{
            List<Object[]> ob = ledgerService.getGraphDataForITP(tin);
            loggerController.ListGeneration(userDetails.getUsername(),"Graph plotting for trp: "+tin+" for graph", "",ip);

            return ResponseEntity.ok(ob);
        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/graph/monthwise")
    public ResponseEntity<?> getGraphMonthwise(HttpServletRequest request){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        try{
            List<Object[]> ob = ledgerService.getGraphDataMonthwise();
            loggerController.ListGeneration(userDetails.getUsername(),"All Data for Graph", "",ip);

            return ResponseEntity.ok(ob);
        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("dashboard/total")
    public ResponseEntity<?> getDashBoardTotal(HttpServletRequest request){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        try{
            List<Object[]> ob = ledgerService.getDashBoardDataTotal();
            loggerController.ListGeneration(userDetails.getUsername(),"All Data for Dashboard", "Admin",ip);

            return ResponseEntity.ok(ob);
        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("dashboard/current")
    public ResponseEntity<?> getDashBoardTotalCurrent(HttpServletRequest request){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        try{
            List<Object[]> ob = ledgerService.getDashBoardDataTotalCurrentMonth();
            loggerController.ListGeneration(userDetails.getUsername(),"All Data for Dashboard for Current Month", "Admin",ip);

            return ResponseEntity.ok(ob);
        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("dashboard/itpdata/{tin}")
    public ResponseEntity<?> getDashBoardForITP(HttpServletRequest request,@PathVariable String tin){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        try{
            List<Object[]> ob = ledgerService.getDataForITPDashboard(tin);
            loggerController.ListGeneration(userDetails.getUsername(),"All Data for Dashboard for Current Month", "Admin",ip);

            return ResponseEntity.ok(ob);
        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

}
