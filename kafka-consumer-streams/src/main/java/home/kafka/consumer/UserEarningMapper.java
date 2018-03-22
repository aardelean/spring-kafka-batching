package home.kafka.consumer;

import home.kafka.payload.user_earning.UserEarning;
import home.kafka.payload.user_earning.jobs.DetailedJobEarning;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserEarningMapper {
    public UserEarning toUserEarning(List<DetailedJobEarning> jobs) {
        DetailedJobEarning first = jobs.iterator().next();
        return UserEarning.builder()
                .age(first.getAge())
                .firstName(first.getFirstName())
                .lastName(first.getLastName())
                .uuid(first.getExternalUUID())
                .id(first.getExternalId())
                .jobEarnings(jobs)
                .build();
    }
}
