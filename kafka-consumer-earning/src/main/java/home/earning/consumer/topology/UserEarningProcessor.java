package home.earning.consumer.topology;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import home.kafka.payload.user_earning.UserEarning;
import home.kafka.payload.user_earning.jobs.DetailedJobEarning;
import home.kafka.payload.user_earning.jobs.JobEarning;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.PunctuationType;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
public class UserEarningProcessor implements Processor<String, UserEarning> {
    private ProcessorContext context;

    private KeyValueStore<String, UserEarning> kvStore;
    @Override
    public void init(ProcessorContext context) {
        log.info("processor init");
        // keep the processor context locally because we need it to forward results downstream and for commit
        this.context = context;

        // call this processor's punctuate() method every 1000 milliseconds.
        // as a way to do something periodically
        this.context.schedule(3000, PunctuationType.WALL_CLOCK_TIME, (timestamp -> {
            log.debug("processor punctuation time!");
            KeyValueIterator<String, UserEarning> iter = this.kvStore.all();
            while (iter.hasNext()) {
                KeyValue<String, UserEarning> entry = iter.next();

                //forward the calculated values downstream
                context.forward(entry.key, entry.value);
                kvStore.delete(entry.key);
                log.info("forwared key: " + entry.key + " with userEarning: " + toLogString(entry.value));
            }

            iter.close();
            // commit the current processing progress
            context.commit();
        }));
        // retrieve the key-value store named "userEarning"
        this.kvStore = (KeyValueStore<String, UserEarning>)context.getStateStore("userEarning");
    }

    @Override
    public void process(String key, UserEarning userEarning) {
        log.info("to process key: " + key + " with userEarning: " + toLogString(userEarning));
        userEarning.setTotalEarned(new BigDecimal(userEarning.getJobEarnings()
                .stream()
                .map(JobEarning::getEarning)
                .mapToDouble(BigDecimal::doubleValue)
                .sum()));
        this.kvStore.put(key, userEarning);
    }

    @Override
    public void punctuate(long timestamp) {

    }

    @Override
    public void close() {
//        kvStore.close();
        log.info("processor close time!");
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