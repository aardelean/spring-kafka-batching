package home.kafka.payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import home.kafka.payload.user_earning.jobs.DetailedJobEarning;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class JsonLogHelper {

    private static Log log = LogFactory.getLog(JsonLogHelper.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String toLogString(Object object) {
        String result = "";
        try {
            if(object.getClass().isAssignableFrom(List.class)){
                CollectionType type = objectMapper.getTypeFactory().constructCollectionType(List.class, DetailedJobEarning.class);
                result = objectMapper.writerFor(type).writeValueAsString(object);
            }
            result = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("error transforming object into pretty json", e);
        }
        return result;
    }
}
