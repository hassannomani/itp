package com.nbr.trp.itp.service;

import com.nbr.trp.itp.entity.ITP;
import com.nbr.trp.itp.entity.RepresentativeAgentView;
import com.nbr.trp.itp.repository.RepresentativeRepository;
import com.nbr.trp.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RepresentativeServiceImpl implements RepresentativeService{
    @Autowired
    RepresentativeRepository  representativeRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ITP saveRepresentative(ITP ITP) {
        Integer trpno = userRepository.findNoOfTRP();
        ITP.setItpId("TRP"+(trpno+1));
        ITP ITP1 = representativeRepository.save(ITP);
        return ITP1;
    }

    @Override
    public List<ITP> getAllRepresentatives() {
        return representativeRepository.findAll();
    }

    @Override
    public Optional<ITP> getUserByTin(String tin) {
        return Optional.ofNullable(representativeRepository.findByTinNo(tin).orElse(null));
    }

    @Override
    public Optional<ITP> getUserById(String id) {
        return Optional.ofNullable(representativeRepository.findByUserid(id).orElse(null));
    }

    @Override
    public List<ITP> getAllRepresentativesOfAnAgent(String id){
        return representativeRepository.findByAgentId(id);
    }

    @Override
    public ITP assignAgent(String tin, String agent){
        ITP rep = representativeRepository.findByTin(tin);
        rep.setAgentId(agent);
        return representativeRepository.save(rep);
    }
    @Override
    public RepresentativeAgentView getAgentInfo(String tin){
        return representativeRepository.findAgentInfoByTin(tin);
    }

    @Override
    public ITP getSingleRepresentativesOfAnAgent(String agent, String trp){
        return representativeRepository.findSingleTRPOfAgent(agent, trp);
    }
}
