package kz.com.java_component;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class JavaComponentApplication {
	public static void main(String[] args) {
		SpringApplication.run(JavaComponentApplication.class, args);
	}
}
