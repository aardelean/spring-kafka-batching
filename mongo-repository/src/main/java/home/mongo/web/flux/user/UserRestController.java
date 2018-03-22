package home.mongo.web.flux.user;

import home.kafka.payload.user_earning.UserEarning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/users")
public class UserRestController {
    @Autowired
    private UserRepository repository;
    @GetMapping("")
    public Flux<UserEarning> findAll() {
        return repository.findAll()
                .filter(v -> v.getTotalEarned() != null )
                .map(v -> UserEarning
                        .builder()
                        .uuid(v.getUuid())
                        .id(v.getExternalId())
                        .firstName(v.getFirstName())
                        .lastName(v.getLastName())
                        .totalEarned(new BigDecimal(v.getTotalEarned()))
                        .build());
    }
    @PostMapping("/random")
    private Mono<User> createRandomUser() {
        User user = new User();
        user.setFirstName("Alex");
        user.setLastName("Ardelean");
        return repository.insert(user);
    }
}
