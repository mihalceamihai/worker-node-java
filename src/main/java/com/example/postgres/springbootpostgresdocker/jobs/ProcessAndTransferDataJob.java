package com.example.postgres.springbootpostgresdocker.jobs;


import com.example.postgres.springbootpostgresdocker.MetricsRepository;
import com.example.postgres.springbootpostgresdocker.services.MetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProcessAndTransferDataJob {
    private final MetricsRepository metricsRepository;
    private final MetricsService metricsService;

    @Scheduled(cron = "0 */2 * * * *")
    void processAndTransfer() {
        List<String> allTypes = metricsRepository.findAllTypes();
        Map<String, Double> typeAndValuesMap = new HashMap<>();

        allTypes.forEach(
                type -> typeAndValuesMap.put(type, metricsService.processAndTransferData(type))
        );

        typeAndValuesMap.forEach(
                (type, averageValue) -> {
                    System.out.println(type);
                    System.out.println(averageValue);
                }
        );
    }

    void inconsistentProcessAndTransfer(){
        List<String> allTypes = metricsRepository.findAllTypes();
        Map<String, Double> typeAndValuesMap = new HashMap<>();

        allTypes.forEach(
                type -> {
                    typeAndValuesMap.put(type, metricsRepository.findAverageOfValuesByType(type));
                    metricsRepository.markAllAsSynchronizedByType(type);
                }
        );
    }
}
