package home.feature.kafka.consumer;

import home.kafka.payload.User;
import home.kafka.payload.user_earning.UserEarning;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;


@Component
@Slf4j
public class FailedMessagesProducer {
    @Value("${failed.topic}")
    private String failedTopic;

    @Autowired
    private KafkaTemplate template;

    public void sendToKafka(UserEarning user)  {
        ListenableFuture<SendResult> future = template.send(failedTopic, user.getUuid(), user);
        future.addCallback(new ListenableFutureCallback<SendResult>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error(ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult result) {
                log.info("sent failure to failed queue successfully");
            }
        });
    }
}
