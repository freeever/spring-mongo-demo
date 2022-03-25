package com.spring.mongo.demo;

import com.spring.mongo.demo.service.AggregationService;
import com.spring.mongo.demo.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Slf4j
@PropertySource(value = "file:${CLIENT_CONFIG_PATH}/tbsm-data-docstore.properties")
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

    // Regardless the app server and DB server time zone settings, the application
    // always use UTC as the default time zone
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone(DateUtils.DEFAULT_ZONE_ID));
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
