package ro.upb.ssec.worker.repository;

import ro.upb.ssec.worker.model.Metrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MetricsRepository extends JpaRepository<Metrics, Integer> {
    @Query("select m" +
            " from Metrics m" +
            " where m.metricType = :metricType" +
            " and m.isSyncronized=false")
    List<Metrics> findAllNonSynchronizedMetricsByType(@Param("metricType") String metricType);

    @Query("select distinct m.metricType from Metrics m")
    List<String> findAllTypes();
}
