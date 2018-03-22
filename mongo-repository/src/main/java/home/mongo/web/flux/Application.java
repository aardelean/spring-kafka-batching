package home.mongo.web.flux;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;


@SpringBootApplication
@EnableReactiveMongoRepositories
public class Application extends AbstractReactiveMongoConfiguration {
    @Value("mongodb://${spring.data.mongodb.username}:${spring.data.mongodb.password}@${spring.data.mongodb.host}:${spring.data.mongodb.port}/${spring.data.mongodb.authenticationDatabase}")
    private String mongoUrl;
    @Value("${spring.data.mongodb.database}")
    private String database;

    public static void main(String ...args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public MongoClient reactiveMongoClient() {
        System.out.println(mongoUrl);
        return MongoClients.create(mongoUrl);
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }
}
