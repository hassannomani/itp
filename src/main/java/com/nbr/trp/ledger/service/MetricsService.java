package com.nbr.trp.ledger.service;

import com.nbr.trp.ledger.entity.Ledger;
import com.nbr.trp.ledger.entity.Metrics;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MetricsService {

    public void saveMetrics(Metrics metrics);

    public List<Metrics> getAllRatesOfAssessmentYear(String year);

    public List<Metrics> getAllRatesOfFiscalYear(String year);

    public Metrics getByMid(String mid);

    public List<Metrics> getAllMetrics();



}
