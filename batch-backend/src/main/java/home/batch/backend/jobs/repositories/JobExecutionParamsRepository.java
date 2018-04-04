package home.batch.backend.jobs.repositories;

import home.batch.backend.jobs.entities.BatchJobExecutionParamsEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface JobExecutionParamsRepository extends PagingAndSortingRepository<BatchJobExecutionParamsEntity, Long> {

    @RestResource(path = "executionId", rel = "executionId")
    Iterable<BatchJobExecutionParamsEntity> findByJobExecutionId(@Param("executionId") Long jobExecutionId);
}
