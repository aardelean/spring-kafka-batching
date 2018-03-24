package home.batch.jobs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "batch_job_execution_context", schema = "public", catalog = "batch")
public class BatchJobExecutionContextEntity {
    private Long jobExecutionId;
    private String shortContext;
    private String serializedContext;
    @JsonIgnore
    private BatchJobExecutionEntity batchJobExecutionByJobExecutionId;

    @Id
    @Column(name = "job_execution_id", nullable = false)
    public Long getJobExecutionId() {
        return jobExecutionId;
    }

    public void setJobExecutionId(Long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
    }

    @Basic
    @Column(name = "short_context", nullable = false, length = 2500)
    public String getShortContext() {
        return shortContext;
    }

    public void setShortContext(String shortContext) {
        this.shortContext = shortContext;
    }

    @Basic
    @Column(name = "serialized_context", nullable = true, length = -1)
    public String getSerializedContext() {
        return serializedContext;
    }

    public void setSerializedContext(String serializedContext) {
        this.serializedContext = serializedContext;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatchJobExecutionContextEntity that = (BatchJobExecutionContextEntity) o;
        return jobExecutionId == that.jobExecutionId &&
                Objects.equals(shortContext, that.shortContext) &&
                Objects.equals(serializedContext, that.serializedContext);
    }

    @Override
    public int hashCode() {

        return Objects.hash(jobExecutionId, shortContext, serializedContext);
    }

    @OneToOne
    @JoinColumn(name = "job_execution_id", referencedColumnName = "job_execution_id", nullable = false)
    public BatchJobExecutionEntity getBatchJobExecutionByJobExecutionId() {
        return batchJobExecutionByJobExecutionId;
    }

    public void setBatchJobExecutionByJobExecutionId(BatchJobExecutionEntity batchJobExecutionByJobExecutionId) {
        this.batchJobExecutionByJobExecutionId = batchJobExecutionByJobExecutionId;
    }
}
