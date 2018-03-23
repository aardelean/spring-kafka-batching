package home.feature.kafka.consumer;

import home.kafka.payload.user_earning.UserEarning;
import home.kafka.payload.user_earning.UserEarningSerde;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.listener.adapter.RetryingMessageListenerAdapter;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.AlwaysRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@EnableKafka
@Slf4j
public class KafkaConfiguration {
    private static final String FAILED_OBJECT_KEY = "record";
    @Value("${retry.initial.backoff.ms}")
    private Long retryInitialBackoffMs;
    @Value("${retry.maximum.backoff.ms}")
    private Long retryMaximumBackoffMs;
    @Value("${retry.maximum.attempts}")
    private Integer maximumAttemps;
    @Value("${retry.multiplier}")
    private Double retryMultiplier;
    @Value("${input.topic}")
    private String inputTopic;
    @Value("${consumer.concurrency}")
    private Integer consumerConcurrency;

    @Bean
    RetryingMessageListenerAdapter<String, UserEarning> retryAdapter(RetryTemplate retryTemplate,
                                                                     RecoveryCallback recoveryCallback) {
        RetryingMessageListenerAdapter adapter = new RetryingMessageListenerAdapter(listener(), retryTemplate, recoveryCallback);
        return adapter;
    }

    @Bean
    RecoveryCallback recoveryCallback(FailedMessagesProducer failedMessagesProducer) {
        return context -> {
            ConsumerRecord<String, UserEarning> attribute = (ConsumerRecord<String, UserEarning>) context.getAttribute(FAILED_OBJECT_KEY);
            failedMessagesProducer.sendToKafka(attribute.value());
            log.error("error occured and failed processing: ", context.getLastThrowable());
            return null;
        };
    }

    @Bean
    MessageListenerContainer listenerContainer(ConsumerFactory consumerFactory,
                                               @Qualifier("retryAdapter") MessageListener messageListener) {
        ConcurrentMessageListenerContainer messageListenerContainer =  new ConcurrentMessageListenerContainer<>(consumerFactory,
                                                                            new ContainerProperties(inputTopic));
        messageListenerContainer.setConcurrency(consumerConcurrency);
        messageListenerContainer.setupMessageListener(messageListener);
        return messageListenerContainer;
    }

    @Bean
    RetryTemplate retryTemplate(BackOffPolicy backOffPolicy, RetryPolicy retryPolicy) {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        return retryTemplate;
    }

    @Bean
    RetryPolicy retryPolicy() {
        SimpleRetryPolicy simpleRetryPolicy =  new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(maximumAttemps);
        return simpleRetryPolicy;
    }

    @Bean
    BackOffPolicy backOffPolicy() {
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(retryInitialBackoffMs);
        backOffPolicy.setMaxInterval(retryMaximumBackoffMs);
        backOffPolicy.setMultiplier(retryMultiplier);
        return backOffPolicy;
    }

    MessageListener<String, UserEarning> listener() {
        MessageListener messageListener= new MessageListener<String, UserEarning>() {
            @Override
            public void onMessage(ConsumerRecord<String, UserEarning> data) {
                processData(data);
            }
        };
        return messageListener;
    }

    private void processData(ConsumerRecord<String, UserEarning> data) {
        log.info(System.currentTimeMillis() + " received the key : " + data.key());
        throw new RuntimeException("whatever");
    }
}
