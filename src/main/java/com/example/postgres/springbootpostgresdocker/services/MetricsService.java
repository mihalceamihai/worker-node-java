package com.example.postgres.springbootpostgresdocker.services;

import com.example.postgres.springbootpostgresdocker.MetricsRepository;
import com.example.postgres.springbootpostgresdocker.model.Metrics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MetricsService {
    private final MetricsRepository metricsRepository;

    @Transactional
    public Double processAndTransferData(String type) {
        List<Metrics> allNonSynchronizedMetrics = metricsRepository.findAllNonSynchronizedMetricsByType(type);
        Double averagePerType = allNonSynchronizedMetrics
                .stream()
                .mapToDouble(Metrics::getValue)
                .average()
                .orElse(Double.NaN);

        allNonSynchronizedMetrics.forEach(metricsEntry -> metricsEntry.setSyncronized(true));
        metricsRepository.saveAll(allNonSynchronizedMetrics);

        return averagePerType;
    }

}

