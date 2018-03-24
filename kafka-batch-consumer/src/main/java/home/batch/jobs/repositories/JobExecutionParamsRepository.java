package home.batch.jobs.repositories;

import home.batch.jobs.entities.BatchJobExecutionParamsEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JobExecutionParamsRepository extends PagingAndSortingRepository<BatchJobExecutionParamsEntity, Long> {
}
