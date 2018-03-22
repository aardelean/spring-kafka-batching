package home.kafka.payload;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String uuid;
    private String firstName;
    private String lastName;
    private int age;
    private int weight;
    private Address address;
    private List<WorkingJob> workingJobs;
}
