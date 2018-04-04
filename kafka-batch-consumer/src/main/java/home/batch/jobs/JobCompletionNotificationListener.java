package home.batch.jobs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import home.kafka.payload.user_earning.jobs.DetailedJobEarning;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    @Override
    public void afterJob(JobExecution jobExecution) {
       log.info("job finished with: " + toLogString(jobExecution.getJobId()));
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
}