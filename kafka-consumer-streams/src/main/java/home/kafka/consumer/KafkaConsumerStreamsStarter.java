package home.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import home.kafka.payload.User;
import home.kafka.payload.UserSerde;
import home.kafka.payload.user_earning.UserEarning;
import home.kafka.payload.user_earning.UserEarningSerde;
import home.kafka.payload.user_earning.jobs.DetailedJobEarning;
import home.kafka.payload.user_earning.jobs.DetailedJobEarningSerde;
import home.kafka.payload.user_earning.jobs.DetailedJobsSerde;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.Serialized;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableKafkaStreams
@Slf4j
public class KafkaConsumerStreamsStarter {
    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaCluster;

    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumerStreamsStarter.class);
    }

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public StreamsConfig kStreamsConfigs() {
        Map<String, Object> props = commonConfigs();
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, UserSerde.class.getName());
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 3000);
        return new StreamsConfig(props);
    }

    @Bean(name = "commonConfigs")
    public Map<String, Object> commonConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "testStreams");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaCluster);
        return props;
    }

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaCluster);
        return new KafkaAdmin(configs);
    }

    @Bean(name = "userStream")
    public KStream<String, UserEarning> kStream(StreamsBuilder kStreamBuilder, UserEarningMapper mapper) {
        KStream<String, User> stream = kStreamBuilder.stream("test");
        return  stream
                .peek((k, v) -> log.info("entered streams processing: " + toLogString(v)))
                .filter((p, v) -> v.getId() > 0)
                .filter((p, v) -> v.getWorkingJobs() != null)
                .flatMapValues(v -> v.getWorkingJobs()
                        .stream()
                        .map(job -> new DetailedJobEarning(job, v))
                        .collect(Collectors.toList())

                )
                .selectKey((key, v) -> v.getExternalUUID())
                .peek((key, job) -> log.info(" key : " + key + " for flattened jobs: " + toLogString(job)))
                .groupByKey(Serialized.with(Serdes.String(), new DetailedJobEarningSerde()))
                .aggregate(ArrayList::new,
                        (key, value, aggregate) -> {
                            aggregate.add(value);
                            return aggregate;
                        },
                        Materialized.with(Serdes.String(), new DetailedJobsSerde())
                )
                .toStream()
                .peek((key, jobs) -> log.info(" key : " + key + " for aggregated jobs: " + toLogString(jobs)))
                .mapValues(mapper::toUserEarning)
                .peek((key, userEarning) -> log.info(" key : " + key + " for mapped userEarning jobs: " + toLogString(userEarning)))
                .through("rawUserEarningTopic", Produced.with(Serdes.String(), new UserEarningSerde()));
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

    @Bean
    public NewTopic rawUserEarningTopic() {
        return new NewTopic("rawUserEarningTopic", 1, (short) 1);
    }
}
