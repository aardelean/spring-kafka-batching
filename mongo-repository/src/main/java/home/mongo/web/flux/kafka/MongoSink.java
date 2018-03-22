package home.mongo.web.flux.kafka;

import home.kafka.payload.user_earning.UserEarning;
import home.mongo.web.flux.user.User;
import home.mongo.web.flux.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MongoSink implements Sink {

    @Autowired
    private UserRepository repository;

    @Override
    public void output(List<UserEarning> userEarnings) {
        log.info("saving total received messages: " + userEarnings.size());
        userEarnings.stream()
                .map(v -> User.builder()
                            .externalId(v.getId())
                            .firstName(v.getFirstName())
                            .lastName(v.getLastName())
                            .uuid(v.getUuid())
                            .totalEarned(v.getTotalEarned().toString())
                            .build()
                )
                .forEach(this::saveUser);
    }

    void saveUser(User user) {
        repository.insert(user).subscribe();
    }
}
