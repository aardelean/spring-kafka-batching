package home.batch.consumer;

import home.kafka.payload.user_earning.UserEarning;

import java.util.List;

public interface Sink {
    void output(List<UserEarning> userEarnings) throws Exception;
}
