package home.batch;

import home.batch.user_earning.*;
import home.kafka.payload.user_earning.UserEarning;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Value("${earning.queue.capacity}")
    private Integer capacity;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

//    // tag::readerwriterprocessor[]
    @Bean
    ItemReader<UserEarning> itemReader() {
        return new UserEarningItemReader();
    }
    @Bean
    UserEarningProcessor processor() {
        return new UserEarningProcessor();
    }
    @Bean
    ItemWriter<UserEarningEntity> itemWriter(UserEarningRepository repository) {
        return new UserEarningItemWriter();
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step earningStep) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(earningStep)
                .end()
                .build();
    }

    @Bean
    public Step earningStep(ItemWriter<UserEarningEntity> writer) {
        return stepBuilderFactory.get("earningStep")
                .<UserEarning, UserEarningEntity> chunk(3)
                .reader(itemReader())
                .processor(processor())
                .writer(writer)
                .build();
    }

    @Bean
    BlockingQueue<UserEarning> queue() {
        return new LinkedBlockingQueue<UserEarning>(capacity);
    }
}