package home.mongo.web.flux.kafka;

import home.kafka.payload.user_earning.UserEarning;

import java.util.List;

public interface Sink {
    public void output(List<UserEarning> userEarnings);
}
