package home.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TestKafkaFactory<K, S, R> implements TestRule{
    private static final Logger LOGGER = LoggerFactory.getLogger(TestKafkaFactory.class);
    public KafkaEmbedded embeddedKafka;
    private String sendTopic;
    private String receiveTopic;

    public TestKafkaFactory(String sendTopic, String receiveTopic) {
        this.sendTopic = sendTopic;
        this.receiveTopic = receiveTopic;

    }

    public KafkaTemplate<K, S> createProducer(Class producerSerializer, KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry) throws Exception {
        // set up the Kafka producer properties
        Map<String, Object> senderProperties = KafkaTestUtils.senderProps(embeddedKafka.getBrokersAsString());
        senderProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producerSerializer);
        // create a Kafka producer factory
        ProducerFactory<K, S> producerFactory = new DefaultKafkaProducerFactory<>(senderProperties);

        // create a Kafka template
        KafkaTemplate<K, S> template = new KafkaTemplate<>(producerFactory);
        // set the default topic to send to
        template.setDefaultTopic(sendTopic);

        // wait until the partitions are assigned
        for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry.getListenerContainers()) {
            ContainerTestUtils.waitForAssignment(messageListenerContainer, embeddedKafka.getPartitionsPerTopic());
        }
        return template;
    }

    public BlockingQueue<ConsumerRecord<K, R>> createConsumer(Class consumerDeserializer) throws Exception {
        // set up the Kafka consumer properties
        Map<String, Object> consumerProperties = KafkaTestUtils.consumerProps("consumer", "false", embeddedKafka);
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumerDeserializer.getName());
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // create a Kafka consumer factory
        DefaultKafkaConsumerFactory<K, R> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProperties);
        BlockingQueue<ConsumerRecord<K, R>> records = new LinkedBlockingQueue<>();
        // set the topic that needs to be consumed
        ContainerProperties containerProperties = new ContainerProperties(receiveTopic);

        // create a Kafka MessageListenerContainer
        KafkaMessageListenerContainer<K, R> container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        // setup a Kafka message listener
        container.setupMessageListener(new MessageListener<K, R>() {
            @Override
            public void onMessage(ConsumerRecord<K, R> record) {
                LOGGER.debug("test-listener received message='{}'", record.toString());
                records.add(record);
            }
        });

        // start the container and underlying message listener
        container.start();

        // wait until the container has the required number of assigned partitions
        ContainerTestUtils.waitForAssignment(container, embeddedKafka.getPartitionsPerTopic());
        return records;
    }

    public ConsumerRecord<K, R> sendAndReceive(S dataToSend, KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry,
                                               Class producerSerializer,
                                               Class consumerDeserializer) throws Exception {
        createProducer(producerSerializer, kafkaListenerEndpointRegistry).sendDefault(dataToSend);
        LOGGER.debug("test-sender sent message='{}'", dataToSend);

        // check that the message was received
        ConsumerRecord<K, R> received = createConsumer(consumerDeserializer).poll(30, TimeUnit.SECONDS);
        return received;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        embeddedKafka = new KafkaEmbedded(1, true, sendTopic, receiveTopic);
        return embeddedKafka.apply(base, description);
    }
}
