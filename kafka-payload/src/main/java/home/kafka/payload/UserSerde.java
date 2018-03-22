package home.kafka.payload;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.support.serializer.JsonSerde;

public class UserSerde extends JsonSerde<User> implements Serializer<User>, Deserializer<User>{
    @Override
    public User deserialize(String topic, byte[] data) {
        return super.deserializer().deserialize(topic, data);
    }

    @Override
    public byte[] serialize(String topic, User data) {
        return super.serializer().serialize(topic, data);
    }
}
