package home.batch.jobs.repositories;

import home.batch.jobs.entities.BatchJobExecutionContextEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JobExecutionContextRepository extends PagingAndSortingRepository<BatchJobExecutionContextEntity, Long> {
}
