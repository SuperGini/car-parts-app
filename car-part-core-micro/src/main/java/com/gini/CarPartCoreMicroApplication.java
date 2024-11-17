package com.gini;


import com.blazebit.persistence.spring.data.repository.config.EnableBlazeRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBlazeRepositories
public class CarPartCoreMicroApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarPartCoreMicroApplication.class, args);
    }

}
