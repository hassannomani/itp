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
        return ledgerRepository.save(ledger);
    }

    @Override
    public Ledger getLedgerByTin(String tin) {
        return null;
    }

    @Override
    public List<LedgerAdminView> getAll() {
        return ledgerRepository.findAllSorted();
    }

    public List<Ledger> getLedgersOfAnITP(String id){
        return ledgerRepository.findByItpTin(id);
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


//    public List<Object[]> getGraphDataForAgent(String agent){
//        return ledgerRepository.graphDataAgent(agent);
//    }
//
//    public List<Ledger> getTRPCommissionOfAnAgent(String agent, String trp){
//        return ledgerRepository.findByAgentTinAndRepresentativeTin(agent,trp);
//    }
//
//    public List<Ledger> getTRPCommissionWithinRange(String agent, String trp, String start, String end){
//        java.sql.Timestamp t1 = convertStringToTimestamp(start,0);
//        java.sql.Timestamp t2 = convertStringToTimestamp(end,1);
//        return ledgerRepository.getTRPCommissionWithinRange(agent, trp, t1, t2);
//    }

    public Ledger getTaxPayerOfAnItp(String itp, String tin){
        return ledgerRepository.findByItpTinAndTaxpayerId(itp, tin);
    }

    public List<Object[]> getGraphDataForITP(String trp){
        return ledgerRepository.graphDataTrp(trp);
    }

    public List<Object[]> getGraphDataMonthwise(){
        return ledgerRepository.sumMonthWise();
    }
    public List<Object[]> getDashBoardDataTotal(){
        return ledgerRepository.dashBoardDataTotal();
    }

    public List<Object[]> getDashBoardDataTotalCurrentMonth(){
        return ledgerRepository.dashBoardDataCurrentMonth();
    }




}
