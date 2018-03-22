package home.kafka.consumer;

import home.kafka.payload.User;
import home.kafka.payload.UserGenerator;
import home.kafka.payload.UserSerde;
import home.kafka.payload.user_earning.UserEarning;
import home.kafka.payload.user_earning.UserEarningSerde;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest (classes = {KafkaConsumerStreamsStarter.class})
@DirtiesContext
@TestPropertySource("classpath:test-application.properties")
public class KafkaStreamTest {

    private static String SEND_TOPIC = "test";
    private static String RECEIVE_TOPIC = "rawUserEarningTopic";

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @ClassRule
    public static TestKafkaFactory kafkaFactory = new TestKafkaFactory(SEND_TOPIC, RECEIVE_TOPIC);

    @Test
    public void testStreams() throws Exception {
        // send the message
        User user = new UserGenerator().generateDefault();
        ConsumerRecord<String, UserEarning> received = kafkaFactory.sendAndReceive(user, kafkaListenerEndpointRegistry,
                UserSerde.class, UserEarningSerde.class);
        // Hamcrest Matchers to check the value
        assertThat(received.value().getFirstName()).isEqualTo("Alex");
    }
}