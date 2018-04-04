package home.batch.backend.jobs.repositories;

import home.batch.backend.jobs.entities.BatchStepExecutionContextEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StepExecutionContextRepository extends PagingAndSortingRepository<BatchStepExecutionContextEntity, Long> {
}
