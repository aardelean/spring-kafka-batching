package home.batch.jobs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "batch_step_execution_context", schema = "public", catalog = "batch")
public class BatchStepExecutionContextEntity {
    private Long stepExecutionId;
    private String shortContext;
    private String serializedContext;
    @JsonIgnore
    private BatchStepExecutionEntity batchStepExecutionByStepExecutionId;

    @Id
    @Column(name = "step_execution_id", nullable = false)
    public Long getStepExecutionId() {
        return stepExecutionId;
    }

    public void setStepExecutionId(Long stepExecutionId) {
        this.stepExecutionId = stepExecutionId;
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
        BatchStepExecutionContextEntity that = (BatchStepExecutionContextEntity) o;
        return stepExecutionId == that.stepExecutionId &&
                Objects.equals(shortContext, that.shortContext) &&
                Objects.equals(serializedContext, that.serializedContext);
    }

    @Override
    public int hashCode() {

        return Objects.hash(stepExecutionId, shortContext, serializedContext);
    }

    @OneToOne
    @JoinColumn(name = "step_execution_id", referencedColumnName = "step_execution_id", nullable = false)
    public BatchStepExecutionEntity getBatchStepExecutionByStepExecutionId() {
        return batchStepExecutionByStepExecutionId;
    }

    public void setBatchStepExecutionByStepExecutionId(BatchStepExecutionEntity batchStepExecutionByStepExecutionId) {
        this.batchStepExecutionByStepExecutionId = batchStepExecutionByStepExecutionId;
    }
}
