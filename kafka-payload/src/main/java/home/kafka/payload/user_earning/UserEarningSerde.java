package home.kafka.payload.user_earning;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.support.serializer.JsonSerde;

public class UserEarningSerde extends JsonSerde<UserEarning> implements Serializer<UserEarning>, Deserializer<UserEarning> {
    @Override
    public UserEarning deserialize(String topic, byte[] data) {
        return super.deserializer().deserialize(topic, data);
    }

    @Override
    public byte[] serialize(String topic, UserEarning data) {
        return super.serializer().serialize(topic, data);
    }
}
