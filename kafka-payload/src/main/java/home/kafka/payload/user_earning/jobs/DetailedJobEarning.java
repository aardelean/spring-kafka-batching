package home.kafka.payload.user_earning.jobs;

import home.kafka.payload.User;
import home.kafka.payload.WorkingJob;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DetailedJobEarning extends JobEarning {
    private String firstName;
    private String lastName;
    private Long externalId;
    private String externalUUID;
    private int age;
    public DetailedJobEarning(WorkingJob job, User user) {
        super(job);
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.age = user.getAge();
        this.externalId = user.getId();
        this.externalUUID = user.getUuid();
    }
}
