package com.nbr.trp.ledger.service;

import com.nbr.trp.ledger.entity.Ledger;
import com.nbr.trp.ledger.entity.LedgerAdminView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LedgerService {

    public Ledger saveLedger(Ledger ledger);

    public Ledger getLedgerByTin(String tin) ;

    public List<Ledger> getLedgerWithinRange(String start, String end);

    public List<LedgerAdminView> getAll();

    public List<Ledger> getLadgersOfAnAgent(String id);
    public List<Object[]> getAgentCommissionView(String tin);

    public List<Ledger> getLedgersOfARepresentative(String id);

    public List<Ledger> getLedgersOfAdmin();

    public List<Ledger> getLedgersOfARepresentativeRange(String id, String Start, String end);
    public List<Ledger> getLedgersOfAnAgentRange(String id, String Start, String end);

    public void checkItems(Ledger ledger);

    public List<Ledger> getByAssmentYrAndTin(String assmnt, String tin);

    public Ledger getByLid(String id);

    public List<Object[]> getGraphData();

    public List<Object[]> getGraphDataForAgent(String agent);

    public List<Ledger> getTRPCommissionOfAnAgent(String agent, String trp);

    public List<Ledger> getTRPCommissionWithinRange(String agent, String trp, String start, String end);

    public Ledger getTaxPayerOfATRP(String trp, String tin);

    public List<Object[]> getGraphDataForTrp(String agent);


}
