package home.kafka.payload.user_earning.jobs;

import com.fasterxml.jackson.databind.type.CollectionType;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.io.IOException;
import java.util.List;

public class DetailedJobsSerializer extends JsonSerializer<List<DetailedJobEarning>> implements Serializer<List<DetailedJobEarning>> {
    @Override
    public byte[] serialize(String topic, List<DetailedJobEarning> data) {
        try {
            byte[] result = null;
            if (data != null) {
                CollectionType type = objectMapper.getTypeFactory().constructCollectionType(List.class, DetailedJobEarning.class);
                result = this.objectMapper.writerFor(type).writeValueAsBytes(data);
            }
            return result;
        }
        catch (IOException ex) {
            throw new SerializationException("Can't serialize data [" + data + "] for topic [" + topic + "]", ex);
        }
    }
}
