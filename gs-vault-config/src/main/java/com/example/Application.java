package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(MyConfiguration.class)
public class Application implements CommandLineRunner {
	Logger LOGGER = LoggerFactory.getLogger(Application.class);

    private final MyConfiguration configuration;

    public Application(MyConfiguration configuration) {
        this.configuration = configuration;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        LOGGER.info("----------------------------------------");
        LOGGER.info("Configuration properties");
        LOGGER.info("        example.username is {}", configuration.getUsername());
        LOGGER.info("        example.password is {}", configuration.getPassword());
        LOGGER.info("----------------------------------------");
    }
}
