package home.kafka.payload;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkingJob {
    private Company company;
    private Date startDate;
    private Date endDate;
    private String salary;
}
