package home.batch.consumer;

import home.kafka.payload.user_earning.UserEarning;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class BlockingSink implements Sink {
    @Value("${earning.queue.await.add-ms}")
    private Long consumerAwaitTime;
    @Autowired
    private BlockingQueue<UserEarning> userEarningBlockingQueue;
    @Override
    public void output(List<UserEarning> userEarnings) throws Exception {
        userEarnings.stream().forEach(this::addItem);
    }

    private void addItem(UserEarning userEarning) {
        try {
            log.info("received a new entity");
            userEarningBlockingQueue.offer(userEarning, consumerAwaitTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
           throw new RuntimeException(e);
        }
    }
}
