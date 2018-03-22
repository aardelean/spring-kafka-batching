package home.kafka.client;

import home.kafka.payload.User;
import home.kafka.payload.UserSerde;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class KafkaClientApplication {

//    @Value("${spring.kafka.bootstrap_servers}")
//    private String kafkaCluster;

	public static void main(String[] args) {
		SpringApplication.run(KafkaClientApplication.class, args);
	}

//    @Bean
//    public ProducerFactory<String, User> producerFactory() {
//        return new DefaultKafkaProducerFactory<>(producerConfigs());
//    }
//
//    @Bean
//    public Map<String, Object> producerConfigs() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaCluster);
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, UserSerde.class);
//        // See https://kafka.apache.org/documentation/#producerconfigs for more properties
//        return props;
//    }
//
//    @Bean
//    public KafkaTemplate<String, User> kafkaTemplate() {
//        return new KafkaTemplate<String, User>(producerFactory());
//    }
}
