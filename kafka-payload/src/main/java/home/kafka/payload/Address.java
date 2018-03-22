package home.kafka.payload;

import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String street;
    private int plz;
    private String city;
    private String country;
}
