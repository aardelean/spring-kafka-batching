package home.batch.backend.jobs.repositories;

import home.batch.backend.jobs.entities.BatchStepExecutionEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StepExecutionRepository extends PagingAndSortingRepository<BatchStepExecutionEntity, Long>{
}
