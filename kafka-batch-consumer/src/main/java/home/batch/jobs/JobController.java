package home.batch.jobs;

import home.kafka.payload.user_earning.UserEarning;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobParametersNotFoundException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

@RestController
public class JobController {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobExplorer jobExplorer;
    @Autowired
    private Job job;
    @Autowired
    private BlockingQueue<UserEarning> queue;
    @RequestMapping(value = "/jobs", method = RequestMethod.POST)
    public String start() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, NoSuchJobException, JobParametersNotFoundException {
        int size = queue.size();
        String result = "job Id : " + jobLauncher.run(job, new JobParametersBuilder()
                                                                    .addLong("random", new Random().nextLong())
                                                                    .toJobParameters())
                                                    .getId();
        result += " and queue size : " + size;
        return result;
    }

    @RequestMapping(value = "/jobs", method = RequestMethod.GET)
    private List<JobInstance> getJobInstance() {
        return jobExplorer.getJobInstances(job.getName(), 0, 10);
    }

    @RequestMapping(value = "/jobs/executions/{instanceId}", method = RequestMethod.GET)
    private List<JobExecutionDTO> getJobExecutions(@PathVariable("instanceId") Long jobInstanceId) {
        List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(jobExplorer.getJobInstance(jobInstanceId));
        return jobExecutions.stream()
                .map(JobExecutionDTO::fromJobExecution)
                .collect(Collectors.toList());
    }

}
