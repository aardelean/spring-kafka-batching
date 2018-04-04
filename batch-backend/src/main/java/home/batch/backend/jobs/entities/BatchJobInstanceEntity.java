package home.batch.backend.jobs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "batch_job_instance", schema = "public", catalog = "batch")
public class BatchJobInstanceEntity {
    private Long jobInstanceId;
    private Long version;
    private String jobName;
    private String jobKey;
    @JsonIgnore
    private Collection<BatchJobExecutionEntity> batchJobExecutionsByJobInstanceId;

    @Id
    @Column(name = "job_instance_id", nullable = false)
    public Long getJobInstanceId() {
        return jobInstanceId;
    }

    public void setJobInstanceId(Long jobInstanceId) {
        this.jobInstanceId = jobInstanceId;
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
    @Column(name = "job_name", nullable = false, length = 100)
    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    @Basic
    @Column(name = "job_key", nullable = false, length = 32)
    public String getJobKey() {
        return jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatchJobInstanceEntity that = (BatchJobInstanceEntity) o;
        return jobInstanceId == that.jobInstanceId &&
                Objects.equals(version, that.version) &&
                Objects.equals(jobName, that.jobName) &&
                Objects.equals(jobKey, that.jobKey);
    }

    @Override
    public int hashCode() {

        return Objects.hash(jobInstanceId, version, jobName, jobKey);
    }

    @OneToMany(mappedBy = "batchJobInstanceByJobInstanceId")
    public Collection<BatchJobExecutionEntity> getBatchJobExecutionsByJobInstanceId() {
        return batchJobExecutionsByJobInstanceId;
    }

    public void setBatchJobExecutionsByJobInstanceId(Collection<BatchJobExecutionEntity> batchJobExecutionsByJobInstanceId) {
        this.batchJobExecutionsByJobInstanceId = batchJobExecutionsByJobInstanceId;
    }
}
