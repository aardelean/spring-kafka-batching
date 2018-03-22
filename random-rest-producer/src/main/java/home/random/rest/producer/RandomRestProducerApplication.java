package home.random.rest.producer;

import home.kafka.payload.UserGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RandomRestProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RandomRestProducerApplication.class, args);
	}

	@Bean
	public UserGenerator userGenerator() {
		return new UserGenerator();
	}
}
