package home.batch.jobs.repositories;

import home.batch.jobs.entities.BatchStepExecutionContextEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StepExecutionContextRepository extends PagingAndSortingRepository<BatchStepExecutionContextEntity, Long> {
}
