package ro.upb.ssec.worker.service;

import ro.upb.ssec.worker.dto.AwsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class AwsService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${metrics.aws.ip}")
    private String awsIp;

    public void writeMetricsToAws(
            String location,
            String collector,
            String metricType,
            Double value
    ) {
        AwsDto requestDto = AwsDto.builder()
                .location(location)
                .collector(collector)
                .metricType(metricType)
                .value(value)
                .build();

        ResponseEntity<String> response = restTemplate.exchange(
                "http://" + awsIp + ":8080/metrics",
                HttpMethod.POST,
                new HttpEntity<>(requestDto),
                String.class
        );


        log.info("aws response = {}", response);
    }
}
