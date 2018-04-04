package home.batch.backend.jobs.repositories;

import home.batch.backend.jobs.entities.BatchJobInstanceEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JobInstanceRepository extends PagingAndSortingRepository<BatchJobInstanceEntity, Long> {
}
