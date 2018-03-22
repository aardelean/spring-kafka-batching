package home.kafka.payload.user_earning.jobs;

import org.apache.kafka.common.serialization.Serde;
import org.springframework.kafka.support.serializer.JsonSerde;

public class DetailedJobEarningSerde extends JsonSerde<DetailedJobEarning> implements Serde<DetailedJobEarning> {
}
