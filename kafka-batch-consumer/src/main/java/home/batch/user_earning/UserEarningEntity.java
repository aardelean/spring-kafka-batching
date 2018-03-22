package home.batch.user_earning;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserEarningEntity {
    @Id @GeneratedValue
    private Long id;
    private String uuid;
    private String fullName;
    private BigDecimal salary;
}
