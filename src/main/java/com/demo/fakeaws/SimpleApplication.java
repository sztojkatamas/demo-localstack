package com.demo.fakeaws;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SimpleApplication {

    public static void main(String[] args) {

        new SpringApplicationBuilder(SimpleApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
