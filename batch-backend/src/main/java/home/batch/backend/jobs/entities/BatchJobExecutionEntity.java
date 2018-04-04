package home.batch.backend.jobs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "batch_job_execution", schema = "public", catalog = "batch")
public class BatchJobExecutionEntity {
    private Long jobExecutionId;
    private Long version;
    private Timestamp createTime;
    private Timestamp startTime;
    private Timestamp endTime;
    private String status;
    private String exitCode;
    private String exitMessage;
    private Timestamp lastUpdated;
    private String jobConfigurationLocation;
    @JsonIgnore
    private BatchJobInstanceEntity batchJobInstanceByJobInstanceId;
    @JsonIgnore
    private BatchJobExecutionContextEntity batchJobExecutionContextByJobExecutionId;
    @JsonIgnore
    private Collection<BatchJobExecutionParamsEntity> batchJobExecutionParamsByJobExecutionId;
    @JsonIgnore
    private Collection<BatchStepExecutionEntity> batchStepExecutionsByJobExecutionId;

    @Id
    @Column(name = "job_execution_id", nullable = false)
    public Long getJobExecutionId() {
        return jobExecutionId;
    }

    public void setJobExecutionId(Long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
    }

    @Basic
    @Column(name = "version", nullable = true)
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Basic
    @Column(name = "create_time", nullable = false)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "start_time", nullable = true)
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

    @Basic
    @Column(name = "job_configuration_location", nullable = true, length = 2500)
    public String getJobConfigurationLocation() {
        return jobConfigurationLocation;
    }

    public void setJobConfigurationLocation(String jobConfigurationLocation) {
        this.jobConfigurationLocation = jobConfigurationLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatchJobExecutionEntity that = (BatchJobExecutionEntity) o;
        return jobExecutionId == that.jobExecutionId &&
                Objects.equals(version, that.version) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(status, that.status) &&
                Objects.equals(exitCode, that.exitCode) &&
                Objects.equals(exitMessage, that.exitMessage) &&
                Objects.equals(lastUpdated, that.lastUpdated) &&
                Objects.equals(jobConfigurationLocation, that.jobConfigurationLocation);
    }

    @Override
    public int hashCode() {

        return Objects.hash(jobExecutionId, version, createTime, startTime, endTime, status, exitCode, exitMessage, lastUpdated, jobConfigurationLocation);
    }

    @ManyToOne
    @JoinColumn(name = "job_instance_id", referencedColumnName = "job_instance_id", nullable = false)
    public BatchJobInstanceEntity getBatchJobInstanceByJobInstanceId() {
        return batchJobInstanceByJobInstanceId;
    }

    public void setBatchJobInstanceByJobInstanceId(BatchJobInstanceEntity batchJobInstanceByJobInstanceId) {
        this.batchJobInstanceByJobInstanceId = batchJobInstanceByJobInstanceId;
    }

    @OneToOne(mappedBy = "batchJobExecutionByJobExecutionId")
    public BatchJobExecutionContextEntity getBatchJobExecutionContextByJobExecutionId() {
        return batchJobExecutionContextByJobExecutionId;
    }

    public void setBatchJobExecutionContextByJobExecutionId(BatchJobExecutionContextEntity batchJobExecutionContextByJobExecutionId) {
        this.batchJobExecutionContextByJobExecutionId = batchJobExecutionContextByJobExecutionId;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Collection<BatchJobExecutionParamsEntity> getBatchJobExecutionParamsByJobExecutionId() {
        return batchJobExecutionParamsByJobExecutionId;
    }

    public void setBatchJobExecutionParamsByJobExecutionId(Collection<BatchJobExecutionParamsEntity> batchJobExecutionParamsByJobExecutionId) {
        this.batchJobExecutionParamsByJobExecutionId = batchJobExecutionParamsByJobExecutionId;
    }

    @OneToMany(mappedBy = "batchJobExecutionByJobExecutionId")
    public Collection<BatchStepExecutionEntity> getBatchStepExecutionsByJobExecutionId() {
        return batchStepExecutionsByJobExecutionId;
    }

    public void setBatchStepExecutionsByJobExecutionId(Collection<BatchStepExecutionEntity> batchStepExecutionsByJobExecutionId) {
        this.batchStepExecutionsByJobExecutionId = batchStepExecutionsByJobExecutionId;
    }
}
