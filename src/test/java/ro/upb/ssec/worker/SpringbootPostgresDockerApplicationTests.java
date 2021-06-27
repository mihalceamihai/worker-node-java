package ro.upb.ssec.worker;

import ro.upb.ssec.worker.service.AwsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootPostgresDockerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private AwsService awsService;

    @Test
    void awsTest() {
        awsService.writeMetricsToAws("smart-house",
                "raspbwerry-kw01",
                "temperature",
                69.0
        );
    }

}
