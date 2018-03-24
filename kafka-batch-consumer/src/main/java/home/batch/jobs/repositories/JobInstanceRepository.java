package home.batch.jobs.repositories;

import home.batch.jobs.entities.BatchJobInstanceEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JobInstanceRepository extends PagingAndSortingRepository<BatchJobInstanceEntity, Long> {
}
