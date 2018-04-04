package home.batch.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.BatchErrorHandler;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;

@Configuration
@Slf4j
public class KafkaConfiguration {
    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaCluster;
    @Value("${failed.queue}")
    private String failedQueue;

    @Bean
    @Primary
    public KafkaListenerContainerFactory<?> batchFactory(ConsumerFactory consumerFactory, BatchErrorHandler batchErrorHandler) {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(true);
        factory.getContainerProperties().setBatchErrorHandler(batchErrorHandler);
        return factory;
    }

    @Bean(name = "failedMessageListener")
    public KafkaMessageListenerContainer listenerContainer(FailedBatchConsumer batchConsumer,
                                                           BatchErrorHandler batchErrorHandler,
                                                           ConsumerFactory consumerFactory) {

        ContainerProperties containerProperties = new ContainerProperties(failedQueue);
        containerProperties.setBatchErrorHandler(batchErrorHandler);
        containerProperties.setGroupId("failedGroupId");
        KafkaMessageListenerContainer messageListenerContainer = new KafkaMessageListenerContainer(consumerFactory,
                containerProperties);
        messageListenerContainer.setupMessageListener(batchConsumer);
        messageListenerContainer.pause();
        return messageListenerContainer;
    }

    @Bean
    @Primary
    BatchErrorHandler batchErrorHandler() {
        return new GlobalBatchErrorHandler();
    }

    @Bean
    FailedBatchConsumer failedBatchConsumer() {
        return new FailedBatchConsumer();
    }
}
