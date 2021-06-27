package ro.upb.ssec.worker.job;


import ro.upb.ssec.worker.repository.MetricsRepository;
import ro.upb.ssec.worker.service.AwsService;
import ro.upb.ssec.worker.service.MetricsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProcessAndTransferDataJob {
    private final MetricsRepository metricsRepository;
    private final MetricsService metricsService;
    private final AwsService awsService;

    @Value("${metrics.collector.name}")
    private String collector;
    @Value("${metrics.location.name}")
    private String location;

    @Scheduled(cron = "0 */2 * * * *")
    void processAndTransfer() {
        List<String> allTypes = metricsRepository.findAllTypes();
        Map<String, Double> typeAndValuesMap = new HashMap<>();

        allTypes.forEach(
                type -> typeAndValuesMap.put(type, metricsService.processAndTransferData(type))
        );

        typeAndValuesMap.forEach(
                (type, averageValue) -> {
                    if (!averageValue.isNaN()) {

                        log.info("sending to aws data of type={}, having value={}", type, averageValue);
                        try {
                            awsService.writeMetricsToAws(
                                    location,
                                    collector,
                                    type,
                                    averageValue
                            );
                        } catch (Exception e) {
                            log.error("sending to aws failed. reason={}", e.getMessage());
                        }

                    }

                }
        );
    }
}
