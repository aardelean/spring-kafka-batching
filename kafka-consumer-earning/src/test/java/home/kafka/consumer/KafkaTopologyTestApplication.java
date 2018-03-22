package home.kafka.consumer;


import home.earning.consumer.topology.KafkaTopologyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Import;

//@SpringBootConfiguration
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,
//        DataSourceTransactionManagerAutoConfiguration.class,
//        JpaRepositoriesAutoConfiguration.class,
//        HibernateJpaAutoConfiguration.class})
//@Import({KafkaConsumerConfig.class, KafkaTopologyConfig.class})
public class KafkaTopologyTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaTopologyTestApplication.class, args);
    }
}
