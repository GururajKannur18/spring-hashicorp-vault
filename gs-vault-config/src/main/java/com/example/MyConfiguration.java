package com.example;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("example")
public class MyConfiguration {
    private String username;
    private String password;
}
