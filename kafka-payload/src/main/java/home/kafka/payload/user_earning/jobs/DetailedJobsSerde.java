package home.kafka.payload.user_earning.jobs;

import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.List;

public class DetailedJobsSerde extends JsonSerde<List<DetailedJobEarning>> {
    public DetailedJobsSerde() {
        super(new DetailedJobsSerializer(), new DetailedJobDeserializer());
    }
}
