package home.batch.jobs;

import lombok.*;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobExecutionDTO {
    private BatchStatus status = BatchStatus.STARTING;
    private Date startTime = null;
    private Date createTime = new Date(System.currentTimeMillis());
    private Date endTime = null;
    private Date lastUpdated = null;
    private String exitCode ;
    private List<String> failureExceptions;
    private String jobConfigurationName;

    public static JobExecutionDTO fromJobExecution(JobExecution jobExecution) {
        return JobExecutionDTO.builder()
                .createTime(jobExecution.getCreateTime())
                .endTime(jobExecution.getEndTime())
                .lastUpdated(jobExecution.getLastUpdated())
                .status(jobExecution.getStatus())
                .exitCode(jobExecution.getExitStatus().getExitCode())
                .failureExceptions(jobExecution.getFailureExceptions().stream()
                                        .map(e -> e.getMessage())
                                        .collect(Collectors.toList()))
                .jobConfigurationName(jobExecution.getJobConfigurationName())
                .build();
    }
}
