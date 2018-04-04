package home.batch.backend.jobs.repositories;

import home.batch.backend.jobs.entities.BatchJobExecutionEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface JobExecutionRepository extends PagingAndSortingRepository<BatchJobExecutionEntity, Long> {

    @RestResource(path = "status", rel = "status")
    Iterable<BatchJobExecutionEntity> findByStatusOrderByCreateTimeDesc(@Param("status") String status);
}
