package home.kafka.payload;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    private String name;
    private Address address;
    private Date registrationDate;
}
