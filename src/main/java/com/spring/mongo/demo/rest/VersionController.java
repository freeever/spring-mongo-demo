package com.spring.mongo.demo.rest;

import com.spring.mongo.demo.dto.docstore.VersionDto;
import com.spring.mongo.demo.model.docstore.Version;
import com.spring.mongo.demo.service.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/versions")
@RestController
public class VersionController {

    private final VersionService versionService;

    @GetMapping
    public List<Version> findAllVersions() {
        return versionService.findAll();
    }

    @GetMapping("/{id}")
    public Version findVersionById(@PathVariable String id) {
        return versionService.findById(id);
    }

    @GetMapping("/aggregation")
    public List<VersionDto> findByAggregation() {
        return versionService.findByAggregation();
    }

    @PostMapping("/backup/{collectionName}")
    public String backupCollection(@PathVariable String collectionName) {
        return versionService.backupCollection(collectionName);
    }

}
