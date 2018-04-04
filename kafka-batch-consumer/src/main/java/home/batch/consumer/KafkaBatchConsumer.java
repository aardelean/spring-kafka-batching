package home.batch.consumer;

import home.kafka.payload.JsonLogHelper;
import home.kafka.payload.user_earning.UserEarning;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class KafkaBatchConsumer {

    @Autowired
    private Sink sink;

    @KafkaListener(id = "list", topics = "userEarningTopic", containerFactory = "batchFactory")
    public void listen(List<UserEarning> earnings) throws Exception {
        log.info("kafka listener entered for : " + JsonLogHelper.toLogString(earnings));
        sink.output(earnings);
    }
}
