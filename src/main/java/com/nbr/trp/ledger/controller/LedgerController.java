package com.nbr.trp.ledger.controller;

import com.nbr.trp.common.service.CommonService;
import com.nbr.trp.itp.entity.ITP;
import com.nbr.trp.ledger.entity.LedgeAPIResponse;
import com.nbr.trp.ledger.entity.Ledger;
import com.nbr.trp.ledger.entity.LedgerAdminView;
import com.nbr.trp.ledger.service.LedgerService;
import com.nbr.trp.log.LoggerController;
import com.nbr.trp.itp.service.RepresentativeService;
import com.nbr.trp.user.response.MessageResponse;
import com.nbr.trp.user.service.UserDetailsImpl;
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
@RequestMapping("/api/ledgers")
public class LedgerController {

    @Autowired
    LedgerService ledgerService;

    @Autowired
    CommonService commonService;

    @Autowired
    LoggerController loggerController;

    @Autowired
    RepresentativeService representativeService;

    @Value("${own.base-url}")
    private String baseurl;

    @PostMapping("/")
    public ResponseEntity<?> saveLedger(HttpServletRequest request, @RequestBody Ledger ld){
        String ip = commonService.getIPAddress(request);
        loggerController.LedgerRequest(ip);
        try{
            List<Ledger> ldPreli = ledgerService
                    .getByAssmentYrAndTin(ld.getAssessmentYear(),ld.getTaxpayerId());

            ITP rep = representativeService
                    .getSingleRepresentativesOfAnAgent(ld.getAgentTin()
                            ,ld.getRepresentativeTin());

            if(ldPreli.isEmpty() && rep!=null){

                ledgerService.checkItems(ld);
                Ledger ldg = ledgerService.saveLedger(ld);

                loggerController
                        .LedgerRequestSaved(
                                ip,
                                ldg.getAgentTin(),
                                ldg.getRepresentativeTin(),
                                ldg.getTaxpayerId()
                        );
                //ledgerService.saveCommission(ldg);
                return ResponseEntity
                        .status(201)
                        .body(
                                new LedgeAPIResponse(
                                        "Data saved",
                                        true,
                                        baseurl+"ledger-representative")
                        );
            }
            else if(!ldPreli.isEmpty() && rep!=null){
                System.out.println("bad request");
                loggerController
                        .LedgerRequestDuplicate(
                                ip,
                                ld.getAgentTin(),
                                ld.getRepresentativeTin(),
                                ld.getTaxpayerId()
                        );
                return ResponseEntity
                        .badRequest()
                        .body(
                                new LedgeAPIResponse(
                                        "Duplicate entry not allowed",
                                        false,
                                        "")
                        );

            } else if(ldPreli.isEmpty() && rep==null){
                loggerController
                        .LedgerRequestDuplicate
                                (
                                        ip,
                                        ld.getAgentTin(),
                                        ld.getRepresentativeTin(),
                                        ld.getTaxpayerId()
                                );
                return ResponseEntity
                        .badRequest()
                        .body(new LedgeAPIResponse(
                                "TRP & Agent doesn't match",
                                false,
                                "")
                        );
            }else{
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

    @GetMapping("/agent/{id}")
    public ResponseEntity<?> getLadgersOfAnAgent(HttpServletRequest request,@PathVariable String id){
        String ip = commonService.getIPAddress(request);
        try{
            List<Ledger> ldgs = ledgerService.getLadgersOfAnAgent(id);
            loggerController.ListGeneration("","All Ledgers of agent: "+id, "Admin",ip);
            return ResponseEntity.ok(ldgs);
        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/agent/commission/{id}")
    public ResponseEntity<?> getAgentCommissionView(HttpServletRequest request,@PathVariable String id){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        try{
            List<Object[]> ldgs = ledgerService.getAgentCommissionView(id);
            loggerController.ListGeneration(userDetails.getUsername(),"All Commission of agent: "+id, "",ip);

            return ResponseEntity.ok(ldgs);
        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/representative/{id}")
    public ResponseEntity<?> getLedgersOfARepresentative(HttpServletRequest request,@PathVariable String id){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        try{
            List<Ledger> ldgs = ledgerService.getLedgersOfARepresentative(id);
            loggerController.ListGeneration(userDetails.getUsername(),"All Commission of Representative: "+id, "",ip);
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
    @GetMapping("/range-representative/{representativeId}/{start}/{end}")

    public ResponseEntity<?> getLedgersRepresentativeRange(HttpServletRequest request,@PathVariable String representativeId, @PathVariable String start, @PathVariable String end){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        try{
            List<Ledger> ldglist = ledgerService.getLedgersOfARepresentativeRange(representativeId,start,end);
            loggerController.ListGeneration(userDetails.getUsername(),"All Ledgers of TRP: "+representativeId+" within the range "+start+" and "+end,"",ip);
            return ResponseEntity.ok(ldglist);
        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/range-agent/{agentId}/{start}/{end}")

    public ResponseEntity<?> getLedgersAgentRange(HttpServletRequest request,@PathVariable String agentId, @PathVariable String start, @PathVariable String end){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        try{
            List<Ledger> ldglist = ledgerService.getLedgersOfAnAgentRange(agentId,start,end);
            loggerController.ListGeneration(userDetails.getUsername(),"All Ledgers of Agent: "+agentId+" within the range "+start+" and "+end,"",ip);

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
    @GetMapping("/graph/trp")
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
    @GetMapping("/graph/agent/{tin}")
    public ResponseEntity<?> getGraphTrp(HttpServletRequest request, @PathVariable String tin){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        try{
            List<Object[]> ob = ledgerService.getGraphDataForAgent(tin);
            loggerController.ListGeneration(userDetails.getUsername(),"All Ledgers of agent: "+tin+" for graph", "",ip);

            return ResponseEntity.ok(ob);
        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/agenttrp/{agent}/{trp}")
    public ResponseEntity<?> getGraphTrp(HttpServletRequest request, @PathVariable String agent, @PathVariable String trp){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        try{
            List<Ledger> ob = ledgerService.getTRPCommissionOfAnAgent(agent,trp);
            loggerController.ListGeneration(userDetails.getUsername(),"All Ledgers of TRP: "+trp+" of Agent: "+agent, "",ip);
            return ResponseEntity.ok(ob);
        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/rangetrp/{agent}/{trp}/{start}/{end}")
    public ResponseEntity<?> getTrpOfAnAgentLedgerRange(HttpServletRequest request, @PathVariable String agent,@PathVariable String trp,@PathVariable String start, @PathVariable String end){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        try{
            List<Ledger> ldglist = ledgerService.getTRPCommissionWithinRange(agent, trp,start,end);
            loggerController.ListGeneration(userDetails.getUsername(),"All Ledgers of TRP: "+trp+" of Agent: "+agent+" within range: "+start+" and "+end, "",ip);

            return ResponseEntity.ok(ldglist);
        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/taxpayertrp/{trp}/{tin}")
    public ResponseEntity<?> getTaxpayerofAnTrp(HttpServletRequest request, @PathVariable String trp,@PathVariable String tin){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        try{
            Ledger ldg = ledgerService.getTaxPayerOfATRP(trp, tin);
            loggerController.LedgerIndividual(ip,trp,userDetails.getUsername());
            return ResponseEntity.ok(ldg);
        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/graph/trp/{tin}")
    public ResponseEntity<?> getGraphRepresentative(HttpServletRequest request, @PathVariable String tin){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        try{
            List<Object[]> ob = ledgerService.getGraphDataForTrp(tin);
            loggerController.ListGeneration(userDetails.getUsername(),"Graph plotting for trp: "+tin+" for graph", "",ip);

            return ResponseEntity.ok(ob);
        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }


}
