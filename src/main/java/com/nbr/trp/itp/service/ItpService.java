package com.nbr.trp.itp.service;

import com.nbr.trp.itp.entity.ITP;
import com.nbr.trp.itp.entity.RepresentativeAgentView;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ItpService {
    public ITP saveRepresentative(ITP ITP);

    public List<ITP> getAllITPs();
    public List<ITP> getAllITPsByType(String type);

    public Optional<ITP> getUserByTin(String tin);

    public Optional<ITP> getUserById(String uuid);


}
