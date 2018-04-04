package home.batch.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.BatchErrorHandler;

@Slf4j
public class GlobalBatchErrorHandler implements BatchErrorHandler {

    @Value("${failed.queue}")
    private String failedQueue;
    @Autowired
    private KafkaTemplate template;

    @Override
    public void handle(Exception e, ConsumerRecords<?, ?> data) {
        log.error("exception during the processing of the batch: ", e);
        template.send(failedQueue, data);
    }
}
