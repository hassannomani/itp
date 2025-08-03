package com.nbr.trp.itp.service;

import com.nbr.trp.itp.entity.ITP;
import com.nbr.trp.itp.entity.RepresentativeAgentView;
import com.nbr.trp.itp.repository.ItpRepository;
import com.nbr.trp.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItpServiceImpl implements ItpService {
    @Autowired
    ItpRepository itpRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ITP saveRepresentative(ITP ITP) {
        Integer trpno = userRepository.findNoOfTRP();
        ITP.setItpId("TRP"+(trpno+1));
        ITP ITP1 = itpRepository.save(ITP);
        return ITP1;
    }

    @Override
    public List<ITP> getAllITPs() {
        return itpRepository.findAll();
    }

    @Override
    public List<ITP> getAllITPsByType(String type) {
        return itpRepository.findAllByType(type);
    }

    @Override
    public Optional<ITP> getUserByTin(String tin) {
        return Optional.ofNullable(itpRepository.findByTinNo(tin).orElse(null));
    }

    @Override
    public Optional<ITP> getUserById(String id) {
        return Optional.ofNullable(itpRepository.findByUserid(id).orElse(null));
    }

    @Override
    public List<Object[]> getAllCount() {
        return itpRepository.findUsersByType();
    }



}
