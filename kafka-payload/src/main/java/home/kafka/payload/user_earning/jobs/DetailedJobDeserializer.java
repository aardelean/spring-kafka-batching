package home.kafka.payload.user_earning.jobs;

import com.fasterxml.jackson.databind.type.CollectionType;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DetailedJobDeserializer extends JsonDeserializer<List<DetailedJobEarning>> {
    @Override
    public List<DetailedJobEarning> deserialize(String topic, byte[] data) {
        try {
            List<DetailedJobEarning> result = null;
            if (data != null) {
                CollectionType type = objectMapper.getTypeFactory().constructCollectionType(List.class, DetailedJobEarning.class);
                result = this.objectMapper.readerFor(type).readValue(data);
            }
            return result;
        }
		catch (IOException e) {
            throw new SerializationException("Can't deserialize data [" + Arrays.toString(data) +
                    "] from topic [" + topic + "]", e);
        }
    }
}
