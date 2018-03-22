package home.batch.user_earning;

import home.kafka.payload.user_earning.UserEarning;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class UserEarningItemReader implements ItemReader<UserEarning> {
    @Value("${earning.queue.await.poll-ms}")
    private Long processorAwaitTime;

    @Autowired
    private BlockingQueue<UserEarning> queue;

    @Override
    public UserEarning read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        try {
            log.info("reading one userEarning object");
            return queue.poll(processorAwaitTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.info("finished the batch!");
            return null;
        }
    }
}
