package com.spring.mongo.demo;

import com.spring.mongo.demo.service.AggregationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Slf4j
@SpringBootApplication
@EnableMongoRepositories
public class SpringMongoDemoApplication {

    @Autowired
    private MongoOperations mongoOperation;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AggregationService aggregationService;

    public static void main(String[] args) {
        SpringApplication.run(SpringMongoDemoApplication.class, args);
    }

    @Bean
    CommandLineRunner init() {

        return new CommandLineRunner() {
            public void run(String... args) throws Exception {
                log.info("=============== Get Courses/Students By Lookup===============");
//                aggregationService.findCoursesWithStudents();
            }
        };
    }
}
