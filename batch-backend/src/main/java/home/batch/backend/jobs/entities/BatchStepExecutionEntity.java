package home.batch.backend.jobs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "batch_step_execution", schema = "public", catalog = "batch")
public class BatchStepExecutionEntity {
    private Long stepExecutionId;
    private Long version;
    private String stepName;
    private Timestamp startTime;
    private Timestamp endTime;
    private String status;
    private Long commitCount;
    private Long readCount;
    private Long filterCount;
    private Long writeCount;
    private Long readSkipCount;
    private Long writeSkipCount;
    private Long processSkipCount;
    private Long rollbackCount;
    private String exitCode;
    private String exitMessage;
    private Timestamp lastUpdated;
    @JsonIgnore
    private BatchJobExecutionEntity batchJobExecutionByJobExecutionId;
    @JsonIgnore
    private BatchStepExecutionContextEntity batchStepExecutionContextByStepExecutionId;

    @Id
    @Column(name = "step_execution_id", nullable = false)
    public Long getStepExecutionId() {
        return stepExecutionId;
    }

    public void setStepExecutionId(Long stepExecutionId) {
        this.stepExecutionId = stepExecutionId;
    }

    @Basic
    @Column(name = "version", nullable = false)
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Basic
    @Column(name = "step_name", nullable = false, length = 100)
    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    @Basic
    @Column(name = "start_time", nullable = false)
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time", nullable = true)
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "status", nullable = true, length = 10)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "commit_count", nullable = true)
    public Long getCommitCount() {
        return commitCount;
    }

    public void setCommitCount(Long commitCount) {
        this.commitCount = commitCount;
    }

    @Basic
    @Column(name = "read_count", nullable = true)
    public Long getReadCount() {
        return readCount;
    }

    public void setReadCount(Long readCount) {
        this.readCount = readCount;
    }

    @Basic
    @Column(name = "filter_count", nullable = true)
    public Long getFilterCount() {
        return filterCount;
    }

    public void setFilterCount(Long filterCount) {
        this.filterCount = filterCount;
    }

    @Basic
    @Column(name = "write_count", nullable = true)
    public Long getWriteCount() {
        return writeCount;
    }

    public void setWriteCount(Long writeCount) {
        this.writeCount = writeCount;
    }

    @Basic
    @Column(name = "read_skip_count", nullable = true)
    public Long getReadSkipCount() {
        return readSkipCount;
    }

    public void setReadSkipCount(Long readSkipCount) {
        this.readSkipCount = readSkipCount;
    }

    @Basic
    @Column(name = "write_skip_count", nullable = true)
    public Long getWriteSkipCount() {
        return writeSkipCount;
    }

    public void setWriteSkipCount(Long writeSkipCount) {
        this.writeSkipCount = writeSkipCount;
    }

    @Basic
    @Column(name = "process_skip_count", nullable = true)
    public Long getProcessSkipCount() {
        return processSkipCount;
    }

    public void setProcessSkipCount(Long processSkipCount) {
        this.processSkipCount = processSkipCount;
    }

    @Basic
    @Column(name = "rollback_count", nullable = true)
    public Long getRollbackCount() {
        return rollbackCount;
    }

    public void setRollbackCount(Long rollbackCount) {
        this.rollbackCount = rollbackCount;
    }

    @Basic
    @Column(name = "exit_code", nullable = true, length = 2500)
    public String getExitCode() {
        return exitCode;
    }

    public void setExitCode(String exitCode) {
        this.exitCode = exitCode;
    }

    @Basic
    @Column(name = "exit_message", nullable = true, length = 2500)
    public String getExitMessage() {
        return exitMessage;
    }

    public void setExitMessage(String exitMessage) {
        this.exitMessage = exitMessage;
    }

    @Basic
    @Column(name = "last_updated", nullable = true)
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatchStepExecutionEntity that = (BatchStepExecutionEntity) o;
        return stepExecutionId == that.stepExecutionId &&
                version == that.version &&
                Objects.equals(stepName, that.stepName) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(status, that.status) &&
                Objects.equals(commitCount, that.commitCount) &&
                Objects.equals(readCount, that.readCount) &&
                Objects.equals(filterCount, that.filterCount) &&
                Objects.equals(writeCount, that.writeCount) &&
                Objects.equals(readSkipCount, that.readSkipCount) &&
                Objects.equals(writeSkipCount, that.writeSkipCount) &&
                Objects.equals(processSkipCount, that.processSkipCount) &&
                Objects.equals(rollbackCount, that.rollbackCount) &&
                Objects.equals(exitCode, that.exitCode) &&
                Objects.equals(exitMessage, that.exitMessage) &&
                Objects.equals(lastUpdated, that.lastUpdated);
    }

    @Override
    public int hashCode() {

        return Objects.hash(stepExecutionId, version, stepName, startTime, endTime, status, commitCount, readCount, filterCount, writeCount, readSkipCount, writeSkipCount, processSkipCount, rollbackCount, exitCode, exitMessage, lastUpdated);
    }

    @ManyToOne
    @JoinColumn(name = "job_execution_id", referencedColumnName = "job_execution_id", nullable = false)
    public BatchJobExecutionEntity getBatchJobExecutionByJobExecutionId() {
        return batchJobExecutionByJobExecutionId;
    }

    public void setBatchJobExecutionByJobExecutionId(BatchJobExecutionEntity batchJobExecutionByJobExecutionId) {
        this.batchJobExecutionByJobExecutionId = batchJobExecutionByJobExecutionId;
    }

    @OneToOne(mappedBy = "batchStepExecutionByStepExecutionId")
    public BatchStepExecutionContextEntity getBatchStepExecutionContextByStepExecutionId() {
        return batchStepExecutionContextByStepExecutionId;
    }

    public void setBatchStepExecutionContextByStepExecutionId(BatchStepExecutionContextEntity batchStepExecutionContextByStepExecutionId) {
        this.batchStepExecutionContextByStepExecutionId = batchStepExecutionContextByStepExecutionId;
    }
}
