package home.kafka.payload.user_earning;

import home.kafka.payload.user_earning.jobs.JobEarning;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class UserEarning {
    private Long id;
    private String uuid;
    private String firstName;
    private String lastName;
    private int age;
    private BigDecimal totalEarned;
    private List<? extends JobEarning> jobEarnings;

}
