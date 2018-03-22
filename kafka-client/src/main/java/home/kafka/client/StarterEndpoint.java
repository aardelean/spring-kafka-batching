package home.kafka.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import home.kafka.payload.User;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.stream.Stream;

@RestController
@Slf4j
public class StarterEndpoint {
    @Value("${REST_PROVIDER}")
    private String restProducerUrl;
    @Autowired
    private UserKafkaProducer kafkaProducer;


    private OkHttpClient restClient = new OkHttpClient();
    private Request request;
    private ObjectMapper objectMapper = new ObjectMapper();
    private RestProducerCallback callback = new RestProducerCallback();

    @PostConstruct
    void initClient() {
        request = new Request.Builder().url(restProducerUrl).build();
        User user = new User();
        user.setId(0L);
        user.setUuid("test uuid");
        kafkaProducer.sendToKafka(user);
    }

    @PostMapping("/{importNo}")
    public void startImport(@PathVariable("importNo") Long importNo) {
        Stream.iterate(0, i -> i < importNo, i -> i + 1)
                .forEach(index -> restClient.newCall(request).enqueue(callback));
    }

    class RestProducerCallback implements Callback{
        @Override
        public void onFailure(Call call, IOException e) {
            log.error(e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected rest status code " + response);
            } else {
                String result = response.body().string();
                User user = objectMapper.readValue(result, User.class);
                kafkaProducer.sendToKafka(user);
            }
        }
    }
}
