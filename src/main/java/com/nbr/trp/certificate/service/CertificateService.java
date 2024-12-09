package com.nbr.trp.certificate.service;

import com.nbr.trp.certificate.entity.Certificate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CertificateService {

    public Certificate saveCertificate(Certificate certificate);

    public void saveCertificateBulk(List<Certificate> certificates);

    public List<Certificate> getAllCertificates();

    public Boolean getCertificateByTinNNid(String tin, String nid);

    public Certificate getUserById(String id);

    public List<Certificate> checkDuplicacy(List<String> tin);

    public Certificate returnCertificateByTinNNid(String tin, String nid);


}
