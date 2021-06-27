package ro.upb.ssec.worker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AwsDto {
    private String location;
    private String collector;
    private String metricType;
    private Double value;
}
