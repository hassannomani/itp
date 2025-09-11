package com.nbr.trp.ledger.repository;

import com.nbr.trp.ledger.entity.Ledger;
import com.nbr.trp.ledger.entity.LedgerAdminView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface LedgerRepository extends JpaRepository<Ledger, String>{

    Ledger findByTaxpayerId(String id);

    Ledger save(Ledger ledger);

    Ledger findByLid(String id);

    List<Ledger> findAll();

    @Query(value = "select ledger.*, agent.name as name, representative.re_name as re_name from ledger \n" +
            "join agent on \n" +
            "ledger.agent_tin=agent.tin  \n" +
            "join representative on\n" +
            "ledger.representative_tin=representative.tin_no order by created_at desc",nativeQuery = true)
    List<LedgerAdminView> findAllSorted();


    List<Ledger> findByItpTin(String id);

    @Query(value = "select * from ledger where created_at >= :startDate AND created_at <=:endDate",nativeQuery = true)
    List<Ledger>findAllWithinDateRange(@Param("startDate") Timestamp startDate, @Param("endDate")Timestamp endDate);

    @Query(value = "select * from ledger where created_at >= :startDate AND created_at <=:endDate AND itp_tin=:repId",nativeQuery = true)
    List<Ledger>findAllReprstvLedgerWithinRange(@Param("repId") String repId, @Param("startDate") Timestamp startDate, @Param("endDate")Timestamp endDate);

    @Query(value = "select * from ledger where created_at >= :startDate AND created_at <=:endDate AND agent_tin=:agId",nativeQuery = true)
    List<Ledger>findAllAgentLedgerWithinRange(@Param("agId") String repId, @Param("startDate") Timestamp startDate, @Param("endDate")Timestamp endDate);

    List<Ledger> findByAssessmentYearAndTaxpayerId(String year, String id);

    @Query(value = "select top 10 sum(isnull(cast(paid_amount as float),0)) as sum, representative_tin,representative.re_name from\n" +
            "ledger join representative on ledger.representative_tin=representative.tin_no group by representative_tin,re_name",nativeQuery = true)
    List<Object[]> graphDataSample();

    @Query(value = "select sum(isnull(cast(paid_amount as float),0)) as amount, sum(representative_commission) as trp, sum(agent_commission) as agent, representative_tin\n" +
            "from ledger where agent_tin = :agentTin group by representative_tin",nativeQuery = true)
    List<Object[]> agentCommissionView(@Param("agentTin") String id);

/*    @Query(value = "select top 10 sum(isnull(cast(paid_amount as float),0)) as sum, representative_tin,representative.re_name from ledger join representative on ledger.representative_tin=representative.tin_no where agent_tin = :agentTin group by representative_tin,re_name",nativeQuery = true)
    List<Object[]> graphDataAgent(@Param("agentTin") String tin);*/

    @Query(value = "SELECT SUM(representative_commission) [TotalAmount], DATEPART(Year, created_at) Year, DATEPART(Month, created_at) Month \n" +
            "FROM ledger where representative_tin = :trpTin\n" +
            "GROUP BY DATEPART(Year, created_at), DATEPART(Month, created_at)\n" +
            "ORDER BY Year, Month",nativeQuery = true)
    List<Object[]> graphDataTrp(@Param("trpTin") String tin);

    //List<Ledger> findByAgentTinAndRepresentativeTin(String agent, String trp);

    @Query(value = "select * from ledger where created_at >= :startDate AND created_at <=:endDate AND agent_tin=:agTin AND representative_tin=:trpTin",nativeQuery = true)
    List<Ledger> getTRPCommissionWithinRange(@Param("agTin") String agTin, @Param("trpTin") String trpTin, @Param("startDate") Timestamp startDate, @Param("endDate")Timestamp endDate);

    @Query(value = "select top 1 * from ledger where itp_tin = :itp AND taxpayer_id = :taxpayer",nativeQuery = true)
    Ledger findByItpTinAndTaxpayerId(String itp, String taxpayer);

    @Query(value = "select * from ledger where bill_submitted_ag=0 and agent_tin = :agTin",nativeQuery = true)
    List<Ledger> findAgentBillable(@Param("agTin") String agTin);

    @Query(value = "select * from ledger where bill_submitted_trp=0 and representative_tin = :repTin",nativeQuery = true)
    List<Ledger> findTRPBillable(@Param("repTin") String repTin);

    @Query(value = "SELECT \n" +
            "  FORMAT(created_at, 'MM-yyyy') AS month,\n" +
            "  sum(isnull(cast(paid_amount as float),0)) AS total_paid\n" +
            "FROM ledger\n" +
            "GROUP BY FORMAT(created_at, 'MM-yyyy')\n" +
            "ORDER BY month;",nativeQuery = true)
    List<Object[]> sumMonthWise();

    @Query(value = "select sum(isnull(cast(paid_amount as float),0)) total, " +
            "count(lid) paid from ledger \n",nativeQuery = true)
    List<Object[]> dashBoardDataTotal();

    @Query(value = "select sum(isnull(cast(paid_amount as float),0)) total_current, " +
            "count(lid) paid_current from ledger where \n" +
            "YEAR(created_at) = YEAR(GETDATE()) and " +
            "MONTH(created_at)= MONTH(GETDATE())",nativeQuery = true)
    List<Object[]> dashBoardDataCurrentMonth();


    @Query(value = "select count(lid) as total from ledger where itp_tin = :tin \n" +
            "union \n" +
            "select count(lid) as total from ledger where itp_tin = :tin and assessment_year='2025-2026'",nativeQuery = true)
    List<Object[]> dashBoardDataForITP(@Param("tin") String tin);


}




