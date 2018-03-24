package home.batch.consumer;

import home.kafka.payload.user_earning.UserEarning;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class BlockingSink implements Sink {
    @Value("${earning.queue.await.add-ms}")
    private Long consumerAwaitTime;
    @Autowired
    private BlockingQueue<UserEarning> userEarningBlockingQueue;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;
    @Override
    public void output(List<UserEarning> userEarnings) throws Exception {
        userEarnings.stream().forEach(this::addItem);
    }

    private void addItem(UserEarning userEarning) {
        try {
            log.info("received a new entity: " + userEarning.getId() + " , but queue remaining size is already at "
                    + userEarningBlockingQueue.remainingCapacity());
            Long nanoTimeBefore = System.nanoTime();
            userEarningBlockingQueue.offer(userEarning, consumerAwaitTime, TimeUnit.MILLISECONDS);
            Long nanoTimeAfter = System.nanoTime();
            log.info("Offer queue took: " + Long.toString(nanoTimeAfter-nanoTimeBefore));
            if (userEarningBlockingQueue.remainingCapacity() == 0) {
                jobLauncher.run(job, new JobParametersBuilder()
                        .addLong("random", new Random().nextLong())
                        .toJobParameters())
                        .getId();
            }
        } catch (Exception e) {
            log.error("error adding to queue, " ,e);
           throw new RuntimeException(e);
        }
    }
}
