package home.batch.jobs;

import home.kafka.payload.JsonLogHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobParametersNotFoundException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;

@RestController
@Slf4j
public class JobController {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;
    @Autowired
    @Qualifier("failedMessageListener")
    private KafkaMessageListenerContainer failedMessageListener;
    @Autowired
    @RequestMapping(value = "/jobs", method = RequestMethod.POST)
    public void start() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, NoSuchJobException, JobParametersNotFoundException {
        jobLauncher.run(job, new JobParametersBuilder()
                                    .addLong("random", new Random().nextLong())
                                    .toJobParameters());
    }

    @RequestMapping(value = "/failed/start", method = RequestMethod.POST)
    public void resumeFailedQueue() {
        if (!failedMessageListener.isRunning()) {
            Map<String, Map<MetricName, ? extends Metric>> metrics = failedMessageListener.metrics();
            log.info("metrics: " + JsonLogHelper.toLogString(metrics));
            failedMessageListener.resume();
        }
    }

    @RequestMapping(value = "/failed/stop", method = RequestMethod.POST)
    public void stopFailedQueue() {
        if (failedMessageListener.isRunning()) {

            failedMessageListener.stop();
        }
    }
}
