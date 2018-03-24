package home.batch.jobs.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "batch_job_execution_params", schema = "public", catalog = "batch")
public class BatchJobExecutionParamsEntity {
    private Long jobExecutionId;
    private String typeCd;
    private String keyName;
    private String stringVal;
    private Timestamp dateVal;
    private Long longVal;
    private Double doubleVal;
    private String identifying;

    @Basic
    @Column(name = "job_execution_id", nullable = false)
    @Id
    public Long getJobExecutionId() {
        return jobExecutionId;
    }

    public void setJobExecutionId(Long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
    }

    @Basic
    @Column(name = "type_cd", nullable = false, length = 6)
    public String getTypeCd() {
        return typeCd;
    }

    public void setTypeCd(String typeCd) {
        this.typeCd = typeCd;
    }

    @Basic
    @Column(name = "key_name", nullable = false, length = 100)
    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    @Basic
    @Column(name = "string_val", nullable = true, length = 250)
    public String getStringVal() {
        return stringVal;
    }

    public void setStringVal(String stringVal) {
        this.stringVal = stringVal;
    }

    @Basic
    @Column(name = "date_val", nullable = true)
    public Timestamp getDateVal() {
        return dateVal;
    }

    public void setDateVal(Timestamp dateVal) {
        this.dateVal = dateVal;
    }

    @Basic
    @Column(name = "long_val", nullable = true)
    public Long getLongVal() {
        return longVal;
    }

    public void setLongVal(Long longVal) {
        this.longVal = longVal;
    }

    @Basic
    @Column(name = "double_val", nullable = true, precision = 0)
    public Double getDoubleVal() {
        return doubleVal;
    }

    public void setDoubleVal(Double doubleVal) {
        this.doubleVal = doubleVal;
    }

    @Basic
    @Column(name = "identifying", nullable = false, length = -1)
    public String getIdentifying() {
        return identifying;
    }

    public void setIdentifying(String identifying) {
        this.identifying = identifying;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatchJobExecutionParamsEntity that = (BatchJobExecutionParamsEntity) o;
        return jobExecutionId == that.jobExecutionId &&
                Objects.equals(typeCd, that.typeCd) &&
                Objects.equals(keyName, that.keyName) &&
                Objects.equals(stringVal, that.stringVal) &&
                Objects.equals(dateVal, that.dateVal) &&
                Objects.equals(longVal, that.longVal) &&
                Objects.equals(doubleVal, that.doubleVal) &&
                Objects.equals(identifying, that.identifying);
    }

    @Override
    public int hashCode() {

        return Objects.hash(jobExecutionId, typeCd, keyName, stringVal, dateVal, longVal, doubleVal, identifying);
    }

}
