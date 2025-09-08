package com.btg.pactual.fundsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class FundsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FundsApiApplication.class, args);
    }

}