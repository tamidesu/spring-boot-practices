package kz.com.my_retro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.testcontainers.containers.MongoDBContainer;
import kz.com.my_retro.MyRetroApplication;

@Profile("!mongoTest")
@Configuration
public class RetroBoardTestConfiguration {

    @Bean
    @RestartScope
    @ServiceConnection
    public MongoDBContainer mongoDBContainer() {
        return new MongoDBContainer("mongo:latest");
    }

    public static void main(String[] args) {
        SpringApplication
                .from(MyRetroApplication::main)
                .with(RetroBoardTestConfiguration.class)
                .run(args);
    }
}
