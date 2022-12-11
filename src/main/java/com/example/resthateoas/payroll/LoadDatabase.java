package com.example.resthateoas.payroll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Date;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository){
        return args -> {
            log.info("Preloading " + repository.save(new Employee("Bilbo Baggins", "burglar", true, Date.valueOf("2010-01-01"), null)));
            log.info("Preloading " + repository.save(new Employee("Frodo Baggins", "thief", false, Date.valueOf("2000-01-01"), Date.valueOf("2020-01-01"))));
        };
    }
}
