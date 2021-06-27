package com.example.postgres.springbootpostgresdocker;

import com.example.postgres.springbootpostgresdocker.model.AuditorAwareImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableScheduling
public class SpringbootPostgresDockerApplication {

    @Bean
    public AuditorAware<String> auditorAware() {

        return new AuditorAwareImpl();

    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootPostgresDockerApplication.class, args);
    }

}
