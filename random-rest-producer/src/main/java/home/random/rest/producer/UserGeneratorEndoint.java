package home.random.rest.producer;

import home.kafka.payload.User;
import home.kafka.payload.UserGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserGeneratorEndoint {

    @Autowired
    private UserGenerator generator;
    @GetMapping(path = "")
    public User getUser() {
        return generator.generateDefault();
    }

    @GetMapping(path = "/hardcode")
    public String getUserDefault() {
        return "{\n" +
                "    \"id\": -1,\n" +
                "    \"uuid\": \"2978a0a6-9cd3-4cae-8b42-137f15d5712c\"\n" +
                "}";
    }
}
