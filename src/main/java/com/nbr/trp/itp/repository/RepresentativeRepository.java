package com.nbr.trp.itp.repository;

import com.nbr.trp.itp.entity.ITP;
import com.nbr.trp.itp.entity.RepresentativeAgentView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface RepresentativeRepository extends JpaRepository<ITP, String> {
    Optional<ITP> findByTinNo(String tin);

    ITP save(ITP ITP);
    Optional<ITP> findByUserid(String userid);
    Boolean existsByTinNo(String tin);


    @Query(value = "select * from representative left join users on users.username=representative.tin_no where agent_id = :agentTin and status='1'",nativeQuery = true)
    List<ITP> findByAgentId(@Param("agentTin") String tin);

    @Query(value="select * from representative join users on representative.tin_no=users.username where users.status='1'", nativeQuery = true)
    List<ITP> findAll();

    @Query(value = "select * from representative where tin_no = :trpTin",nativeQuery = true)
    ITP findByTin(@Param("trpTin") String tin);


    @Query(value = "select * from representative join agent on representative.agent_id=agent.tin where representative.tin_no = :trpTin",nativeQuery = true)
    RepresentativeAgentView findAgentInfoByTin(@Param("trpTin") String tin);

    @Query(value = "select * from representative where tin_no = :trpTin and agent_id = :agTin",nativeQuery = true)
    ITP findSingleTRPOfAgent(@Param("agTin") String agTin, @Param("trpTin") String trpTin);




}
