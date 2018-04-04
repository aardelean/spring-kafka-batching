package home.batch.backend.jobs.repositories;

import home.batch.backend.jobs.entities.BatchJobExecutionContextEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JobExecutionContextRepository extends PagingAndSortingRepository<BatchJobExecutionContextEntity, Long> {
}
