package home.kafka.payload.user_earning.jobs;

import home.kafka.payload.WorkingJob;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Period;
import java.time.ZoneId;

@Getter
@Setter
@NoArgsConstructor
public class JobEarning {

    private String companyName;
    private int months;
    private BigDecimal salary;
    private BigDecimal earning;

    public JobEarning(WorkingJob job) {
        companyName = job.getCompany().getName();
        Period period = Period.between(job.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                                job.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        months = period.getYears() * 12 + period.getMonths();
        salary = new BigDecimal(job.getSalary());
        earning = salary.multiply(new BigDecimal(months))
                                        .divide(new BigDecimal(12), 2, RoundingMode.HALF_UP);
    }
}
