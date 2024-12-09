package com.nbr.trp.itp.service;

import com.nbr.trp.itp.entity.ITP;
import com.nbr.trp.itp.entity.RepresentativeAgentView;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RepresentativeService {
    public ITP saveRepresentative(ITP ITP);

    public List<ITP> getAllRepresentatives();

    public Optional<ITP> getUserByTin(String tin);

    public Optional<ITP> getUserById(String uuid);

    public List<ITP> getAllRepresentativesOfAnAgent(String id);

    public ITP assignAgent(String tin, String agent);

    public RepresentativeAgentView getAgentInfo(String tin);

    public ITP getSingleRepresentativesOfAnAgent(String agent, String trp);


}
