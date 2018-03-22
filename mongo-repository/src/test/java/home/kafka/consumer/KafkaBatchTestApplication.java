package home.kafka.consumer;


import home.mongo.web.flux.kafka.BatchConsumer;
import home.mongo.web.flux.kafka.KafkaConsumerConfiguration;
import home.mongo.web.flux.kafka.Sink;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

@SpringBootConfiguration
@EnableAutoConfiguration(exclude = {MongoReactiveAutoConfiguration.class,
        MongoReactiveDataAutoConfiguration.class,
        MongoDataAutoConfiguration.class,
        MongoRepositoriesAutoConfiguration.class,
        MongoAutoConfiguration.class})
@Import({KafkaConsumerConfiguration.class})
public class KafkaBatchTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaBatchTestApplication.class, args);
    }

    @Bean
    @Primary
    public Sink mockedSink() {
        return Mockito.mock(Sink.class);
    }

    @Bean
    public BatchConsumer batchConsumer() {
        return new BatchConsumer();
    }
}
