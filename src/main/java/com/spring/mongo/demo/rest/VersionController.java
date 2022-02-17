package com.spring.mongo.demo.rest;

import com.spring.mongo.demo.common.exception.DocStoreDataAccessException;
import com.spring.mongo.demo.dto.docstore.BackupCollectionRequest;
import com.spring.mongo.demo.dto.docstore.VersionDto;
import com.spring.mongo.demo.model.docstore.Version;
import com.spring.mongo.demo.service.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    @PostMapping("/backup-collections")
    @ResponseBody
    public String backupCollections(@Valid @RequestBody BackupCollectionRequest request)
        throws DocStoreDataAccessException {
        return versionService.backupCollection(request);
    }

}
