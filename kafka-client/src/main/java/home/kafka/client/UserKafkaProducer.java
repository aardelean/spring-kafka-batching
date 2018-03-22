package home.kafka.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import home.kafka.payload.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class UserKafkaProducer {

    @Autowired
    private KafkaTemplate template;

    public void sendToKafka(User user)  {
        ListenableFuture<SendResult<Integer, String>> future = template.send("test", user.getUuid(), user);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                log.info("sent successfully");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error(ex.getMessage());
            }

        });
    }
}
