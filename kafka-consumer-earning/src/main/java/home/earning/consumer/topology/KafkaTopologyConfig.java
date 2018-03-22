package home.earning.consumer.topology;

import home.kafka.payload.user_earning.UserEarning;
import home.kafka.payload.user_earning.UserEarningSerde;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class KafkaTopologyConfig {
    @Autowired
    private UserEarningProcessor userEarningProcessor;

    @Bean(name = "rawUserEarningStream")
    public KafkaStreams streamsWithTopology(StreamsConfig streamsConfig, StoreBuilder<KeyValueStore<String, UserEarning>> storeBuilder) {
        Deserializer<String> keyDeserializer = Serdes.String().deserializer();
        Deserializer<UserEarning> valueDeserializer = new UserEarningSerde().deserializer();
        Serializer<String> keySerializer = Serdes.String().serializer();
        Serializer<UserEarning> valueSerializer = new UserEarningSerde().serializer();
        Topology topology = new Topology()
                .addSource(Topology.AutoOffsetReset.LATEST,
                        "SOURCE" ,
                        keyDeserializer,
                        valueDeserializer,
                        "rawUserEarningTopic")
                .addProcessor("PROCESSOR", UserEarningProcessor::new, "SOURCE")
                .addStateStore(storeBuilder, "PROCESSOR")
                .addSink("SINK",
                        "userEarningTopic",
                        keySerializer,
                        valueSerializer,
                        "PROCESSOR");
        KafkaStreams kafkaStreams = new KafkaStreams(topology, streamsConfig);
        kafkaStreams.start();
        return kafkaStreams;
    }

//    @Bean(name = "rawUserEarningStream")
//    public KStream streamsWithTopologyStreams(StreamsBuilder streamsBuilder, StoreBuilder<KeyValueStore<String, UserEarning>> storeBuilder) {
//        KStream<String, UserEarning> kafkaStreams = streamsBuilder.addStateStore(storeBuilder).stream("rawUserEarningTopic");
//        kafkaStreams
//                .filter((key, user) -> user.getJobEarnings() != null)
//                .peek((key, value) -> log.info("before processor for key : " + key))
//                .process(() -> userEarningProcessor, "userEarning");
//        kafkaStreams.through("userEarningTopic", Produced.with(new Serdes.StringSerde(), new UserEarningSerde()));
//        return kafkaStreams;
//    }

    @Bean
    public UserEarningProcessor processor() {
        return new UserEarningProcessor();
    }

    @Bean
    public StoreBuilder<KeyValueStore<String, UserEarning>> storeBuilder() {
        return Stores.keyValueStoreBuilder(
                Stores.persistentKeyValueStore("userEarning"),
                Serdes.String(),
                new UserEarningSerde());
    }

    @Bean
    public NewTopic userEarningTopic() {
        return new NewTopic("userEarningTopic", 1, (short) 1);
    }
}
