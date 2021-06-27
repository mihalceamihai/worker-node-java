package com.example.postgres.springbootpostgresdocker.controller;

import com.example.postgres.springbootpostgresdocker.MetricsRepository;
import com.example.postgres.springbootpostgresdocker.model.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MetricsController {

    @Autowired
    private MetricsRepository metricsRepository;

    @PostMapping("/metrics")
    public Metrics addMetrics(@RequestBody Metrics metrics){

       return metricsRepository.save(metrics);

    }
    @GetMapping("/metrics")
    public ResponseEntity<List<Metrics>> findAll(){

       return ResponseEntity.ok(metricsRepository.findAll());

    }

    @GetMapping("/metrics/{id}")
    public ResponseEntity<Metrics> findMetricId(@PathVariable(value = "id") Integer id){

        Metrics metrics=metricsRepository.findById(id).orElseThrow( ()-> new ResourceNotFoundException("Metric not found"
                + id));
        return ResponseEntity.ok().body(metrics);
    }

    @DeleteMapping("/metrics/{id}")
    public ResponseEntity<Void> deleteMetric(@PathVariable(value = "id") Integer id){
        Metrics metrics=metricsRepository.findById(id).orElseThrow( ()-> new ResourceNotFoundException("Metric not found"
                + id));
        metricsRepository.delete(metrics);
        return ResponseEntity.ok().build();
    }

}
