package home.kafka.consumer;

import home.earning.consumer.KafkaConsumerEarningStarter;
import home.kafka.payload.Address;
import home.kafka.payload.Company;
import home.kafka.payload.WorkingJob;
import home.kafka.payload.user_earning.UserEarning;
import home.kafka.payload.user_earning.UserEarningSerde;
import home.kafka.payload.user_earning.jobs.JobEarning;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = KafkaConsumerEarningStarter.class)
@DirtiesContext
@TestPropertySource("classpath:test-application.properties")
public class KafkaTopologyTest {

    private static String SEND_TOPIC = "rawUserEarningTopic";
    private static String RECEIVE_TOPIC = "userEarningTopic";

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @ClassRule
    public static TestKafkaFactory<String, UserEarning, UserEarning> kafkaFactory = new TestKafkaFactory(SEND_TOPIC, RECEIVE_TOPIC);

    @Test
    public void testTopology() throws Exception {
        Date startDate = Date.from(LocalDate.now().minus(1L, ChronoUnit.YEARS).atStartOfDay(ZoneId.systemDefault()).toInstant());
        UserEarning user = UserEarning.builder()
                .age(22)
                .uuid("UUID")
                .firstName("Guido")
                .lastName("Milanezo")
                .id(1L)
                .jobEarnings(List.of(new JobEarning(WorkingJob.builder()
                                    .company(Company.builder()
                                            .registrationDate(new Date())
                                            .name("linkedin")
                                            .address(Address.builder()
                                                    .street("Californiastreet")
                                                    .city("San Franciso")
                                                    .country("USA")
                                                    .plz(898765)
                                                    .build())
                                            .build())
                                    .endDate(new Date())
                                    .startDate(startDate)
                                    .salary("90000")
                                    .build()
                )))
                .totalEarned(new BigDecimal(0))
                .build();
        ConsumerRecord<String, UserEarning> received = kafkaFactory
                .sendAndReceive(user.getUuid(), user, kafkaListenerEndpointRegistry, UserEarningSerde.class, UserEarningSerde.class);
        // Hamcrest Matchers to check the value
        assertThat(received.value().getTotalEarned()).isEqualTo(new BigDecimal(90000));
    }
}
