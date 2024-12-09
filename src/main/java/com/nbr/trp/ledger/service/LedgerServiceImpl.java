package com.nbr.trp.ledger.service;

import com.nbr.trp.ledger.entity.Ledger;
import com.nbr.trp.ledger.entity.LedgerAdminView;
import com.nbr.trp.ledger.repository.LedgerRepository;
import com.nbr.trp.user.entity.User;
import com.nbr.trp.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

@Service
public class LedgerServiceImpl implements  LedgerService
{
    @Autowired
    LedgerRepository ledgerRepository;


    @Autowired
    UserRepository userRepository;



    @Override
    public Ledger saveLedger(Ledger ledger) {

 /*       HashMap<String, String> returnedVal = commissionService.calculateCommission(ledger);
        Double commission = Double.valueOf(returnedVal.get("sum"));
        String remarks = returnedVal.get("remarks");
        ledger.setRemarks(remarks);
        Double agent = commission*0.1;
        Double trp = commission *0.9;
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        Double agentFin = Double.valueOf(df.format(agent));
        Double trpFin = Double.valueOf(df.format(trp));
        ledger.setAgentCommission(agentFin);
        ledger.setRepresentativeCommission(trpFin);
        ledger.setBillSubmittedAg("0");
        ledger.setBillSubmittedTrp("0");
        Ledger ldg = ledgerRepository.save(ledger);
        return ldg;*/
        return null;

//        ledger.setAgentCommission();
//            if(trp!=null){
//                Commission cmtrp = new Commission();
//                cmtrp.setDebitCode(bank.getItemCode());
//                cmtrp.setCreditCode(trp.getItemCode());
//                cmtrp.setTaxpayerId(ledger.getTaxpayerId());
//                cmtrp.setAmount(String.valueOf(commission*0.9));
//                cmtrp.setRemarks(remarks);
//                cmtrp.setLedgerId(ledger.getLid());
//                Commission cmsaved = commissionService.saveCommission(cmtrp);
//
//            }
//            if(agent!=null){
//                Commission cmag = new Commission();
//                cmag.setDebitCode(bank.getItemCode());
//                cmag.setCreditCode(agent.getItemCode());
//                cmag.setTaxpayerId(ledger.getTaxpayerId());
//                cmag.setAmount(String.valueOf(commission*0.1));
//                cmag.setRemarks(remarks);
//                cmag.setLedgerId(ledger.getLid());
//                Commission cmsaved = commissionService.saveCommission(cmag);
//            }

    }

    @Override
    public Ledger getLedgerByTin(String tin) {
        return null;
    }

    @Override
    public List<LedgerAdminView> getAll() {
        return ledgerRepository.findAllSorted();
    }

    @Override
    public List<Ledger> getLadgersOfAnAgent(String id){
        return ledgerRepository.findByAgentTin(id);
    }

    public List<Ledger> getLedgersOfARepresentative(String id){
        return ledgerRepository.findByRepresentativeTin(id);
    }

    public List<Ledger> getLedgersOfAdmin(){
        return ledgerRepository.findAll();
    }

    @Override
    public List<Ledger> getLedgersOfARepresentativeRange(String id, String start, String end) {
        java.sql.Timestamp t1 = convertStringToTimestamp(start,0);
        java.sql.Timestamp t2 = convertStringToTimestamp(end,1);
        return ledgerRepository.findAllReprstvLedgerWithinRange(id, t1, t2);
    }

    @Override
    public List<Ledger> getLedgersOfAnAgentRange(String id, String start, String end) {
        java.sql.Timestamp t1 = convertStringToTimestamp(start,0);
        java.sql.Timestamp t2 = convertStringToTimestamp(end,1);
        return ledgerRepository.findAllAgentLedgerWithinRange(id, t1, t2);
    }

    @Override
    public List<Ledger> getLedgerWithinRange(String start, String end){
        java.sql.Timestamp t1 = convertStringToTimestamp(start,0);
        java.sql.Timestamp t2 = convertStringToTimestamp(end,1);
        System.out.println("timestamp 1"+t1);
        System.out.println("timestamp 2"+t2);
        return ledgerRepository.findAllWithinDateRange(t1, t2);
    }

    public java.sql.Timestamp convertStringToTimestamp(String strDate, int flag) {
//        java.sql.Timestamp t1 = java.sql.Timestamp.valueOf(strDate);
//        return t1;
        try {
            LocalDateTime dateTime;
            if(flag ==0)
                dateTime = LocalDate.parse(strDate).atStartOfDay();
            else
                dateTime = LocalDate.parse(strDate).atTime(LocalTime.MAX);

            return Timestamp.valueOf(dateTime);
        } catch(Exception e) {
            // look the origin of excption
            System.out.println(e);
            return null;
        }
    }

    public void checkItems(Ledger ledger){

    }

    public void saveCommission(Ledger ledger){

    }

    public List<Ledger> getByAssmentYrAndTin(String assmnt, String tin){
        List<Ledger> ld = ledgerRepository.findByAssessmentYearAndTaxpayerId(assmnt,tin);
        return ld;
    }

    public Ledger getByLid(String id){
        return ledgerRepository.findByLid(id);
    }

    public List<Object[]> getGraphData(){
        return ledgerRepository.graphDataSample();
    }

    public List<Object[]> getAgentCommissionView(String tin){
        return ledgerRepository.agentCommissionView(tin);
    }


    public List<Object[]> getGraphDataForAgent(String agent){
        return ledgerRepository.graphDataAgent(agent);
    }

    public List<Ledger> getTRPCommissionOfAnAgent(String agent, String trp){
        return ledgerRepository.findByAgentTinAndRepresentativeTin(agent,trp);
    }

    public List<Ledger> getTRPCommissionWithinRange(String agent, String trp, String start, String end){
        java.sql.Timestamp t1 = convertStringToTimestamp(start,0);
        java.sql.Timestamp t2 = convertStringToTimestamp(end,1);
        return ledgerRepository.getTRPCommissionWithinRange(agent, trp, t1, t2);
    }

    public Ledger getTaxPayerOfATRP(String trp, String tin){
        return ledgerRepository.findByRepresentativeTinAndTaxpayerId(trp, tin);
    }

    public List<Object[]> getGraphDataForTrp(String trp){
        return ledgerRepository.graphDataTrp(trp);
    }




}
