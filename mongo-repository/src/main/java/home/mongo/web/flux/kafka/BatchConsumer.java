package home.mongo.web.flux.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import home.kafka.payload.user_earning.UserEarning;
import home.kafka.payload.user_earning.jobs.DetailedJobEarning;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class BatchConsumer {

    @Autowired
    private Sink sink;

    @KafkaListener(id = "list", topics = "userEarningTopic", containerFactory = "batchFactory")
    public void listen(List<UserEarning> earnings) {
        log.info("kafka listener entered for : " + toLogString(earnings));
        sink.output(earnings);
    }

    private ObjectMapper objectMapper = new ObjectMapper();
    private String toLogString(Object object) {
        String result = "";
        try {
            if(object.getClass().isAssignableFrom(List.class)){
                CollectionType type = this.objectMapper.getTypeFactory().constructCollectionType(List.class, DetailedJobEarning.class);
                result = this.objectMapper.writerFor(type).writeValueAsString(object);
            }
            result = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("error transforming object into pretty json", e);
        }
        return result;
    }
}
