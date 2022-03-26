package com.spring.mongo.demo.test.scheduler;

import com.spring.mongo.demo.dto.docstore.RuleDto;
import com.spring.mongo.demo.dto.docstore.RuleSearchRequest;
import com.spring.mongo.demo.service.RuleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DocStoreSchedulerTest {

    @Autowired
    private RuleService ruleService;

    @Before
    public void before() {
        log.debug("Unit test starting: DocStoreSchedulerTest");
    }

    @After
    public void after() {
        log.debug("Unit test ending: DocStoreSchedulerTest");
    }

    @Test
    public void testExportRuleToElasticsearch() throws IOException {
        log.info("DocStoreSchedulerTest :: testExportRuleToElasticsearch");

        this.ruleService.exportRuleToElasticsearch();
        RuleSearchRequest request = new RuleSearchRequest();
        request.setTerm("ApplicationCode");
        List<RuleDto> rules = ruleService.findByText(request);
        log.info("Found {} rules!", rules.size());

        log.info("Completed :: DocStoreSchedulerTest :: testExportRuleToElasticsearch");
    }

}
