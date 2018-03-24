package home.batch.jobs.repositories;

import home.batch.jobs.entities.BatchStepExecutionEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StepExecutionRepository extends PagingAndSortingRepository<BatchStepExecutionEntity, Long>{
}
