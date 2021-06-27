package com.example.postgres.springbootpostgresdocker;

import com.example.postgres.springbootpostgresdocker.model.Metrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface MetricsRepository extends JpaRepository<Metrics, Integer> {
    @Query("select avg(m.value)" +
            " from Metrics m" +
            " where m.metricType = :metricType" +
            " and m.isSyncronized=false")
    Double findAverageOfValuesByType(@Param("metricType") String metricType);

    @Query("select m" +
            " from Metrics m" +
            " where m.metricType = :metricType" +
            " and m.isSyncronized=false")
    List<Metrics> findAllNonSynchronizedMetricsByType(@Param("metricType") String metricType);

    @Modifying
    @Transactional
    @Query("update Metrics m set m.isSyncronized = true where m.isSyncronized = false and m.metricType = :metricType")
    void markAllAsSynchronizedByType(@Param("metricType") String metricType);

    @Query("select distinct m.metricType from Metrics m")
    List<String> findAllTypes();
}
