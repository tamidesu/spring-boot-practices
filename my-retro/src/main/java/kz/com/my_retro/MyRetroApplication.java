package kz.com.my_retro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MyRetroApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyRetroApplication.class, args);
	}
}
