package home.kafka.consumer;

import home.kafka.payload.user_earning.UserEarning;
import home.kafka.payload.user_earning.UserEarningSerde;
import home.mongo.web.flux.kafka.BatchConsumer;
import home.mongo.web.flux.kafka.Sink;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = KafkaBatchTestApplication.class)
@DirtiesContext
@TestPropertySource("classpath:test-application.properties")
public class KafkaBatchTest {

    private static String SEND_TOPIC = "userEarningTopic";

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @Autowired
    private BatchConsumer consumer;

    @Autowired
    private Sink sink;

    @ClassRule
    public static TestKafkaFactory<String, UserEarning, UserEarning> kafkaFactory = new TestKafkaFactory(SEND_TOPIC, null);

    @Test
    public void testBatch() throws Exception {
        KafkaTemplate<String, UserEarning> producer = kafkaFactory.createProducer(UserEarningSerde.class, kafkaListenerEndpointRegistry);
        List.of(UserEarning.builder()
                        .age(22)
                        .uuid("UUID")
                        .firstName("Guido")
                        .lastName("Milanezo1")
                        .id(1L)
                        .totalEarned(new BigDecimal(90000))
                        .build(),
                UserEarning.builder()
                        .age(22)
                        .uuid("UUID")
                        .firstName("Guido")
                        .lastName("Milanezo2")
                        .id(1L)
                        .totalEarned(new BigDecimal(90000))
                        .build(),
                UserEarning.builder()
                        .age(22)
                        .uuid("UUID")
                        .firstName("Guido")
                        .lastName("Milanezo3")
                        .id(1L)
                        .totalEarned(new BigDecimal(90000))
                        .build())
        .stream().forEach(user -> producer.sendDefault(user));
        Thread.sleep(5_000);
        verify(sink, atLeast(1)).output(anyList());
    }
}
