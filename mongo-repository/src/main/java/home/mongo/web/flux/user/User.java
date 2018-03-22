package home.mongo.web.flux.user;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "userEarning")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class User {

    @Id
    private String id;
    private Long externalId;
    private String uuid;
    private String firstName;
    private String lastName;
    private String totalEarned;
}
