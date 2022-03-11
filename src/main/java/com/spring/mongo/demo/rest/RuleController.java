package com.spring.mongo.demo.rest;

import com.spring.mongo.demo.dto.docstore.ResponseData;
import com.spring.mongo.demo.dto.docstore.RuleSearchRequest;
import com.spring.mongo.demo.service.RuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/rules")
@RestController
public class RuleController {

    private final RuleService ruleService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<ResponseData<List<Document>>> findRules(@RequestBody RuleSearchRequest request) {
        log.info("RuleController.findRules :: \"{}\"", request.getTerm());
        List<Document> rules = ruleService.findRules(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseData<>(true, rules));
    }
}
