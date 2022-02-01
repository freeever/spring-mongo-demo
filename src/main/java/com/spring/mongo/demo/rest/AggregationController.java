package com.spring.mongo.demo.rest;

import com.spring.mongo.demo.model.Course;
import com.spring.mongo.demo.service.AggregationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/aggregation")
@RestController
public class AggregationController {

    private final AggregationService aggregationService;

    @GetMapping
    public List<Course> findCoursesWithStudents() {
        log.info("Retrieving courses together students by aggregation lookup");
        return aggregationService.findCoursesWithStudents();
    }

}
