package home.batch.consumer;

import home.kafka.payload.JsonLogHelper;
import home.kafka.payload.user_earning.UserEarning;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.BatchMessageListener;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FailedBatchConsumer implements BatchMessageListener<String, UserEarning> {
    @Autowired
    private Sink sink;
    @Override
    public void onMessage(List<ConsumerRecord<String, UserEarning>> data) {
        List<UserEarning> earnings = data.stream().map(record -> record.value()).collect(Collectors.toList());
        log.info("kafka listener entered for : " + JsonLogHelper.toLogString(earnings));
        try {
            sink.output(earnings);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
