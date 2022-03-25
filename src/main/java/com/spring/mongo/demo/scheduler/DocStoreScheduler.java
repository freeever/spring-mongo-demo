package com.spring.mongo.demo.scheduler;

import com.spring.mongo.demo.service.RuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduled jobs for Doc Store
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class DocStoreScheduler {

    private final RuleService ruleService;

    @Scheduled(
            cron = "${elk.index.rule.cron:0 55 16 * * *}",
            zone = "${elk.index.rule.cron.zone:EST}"
    )
    public void exportRulesToElasticsearch() {
        log.info("[Schedule Task] Exporting Doc Store Rules to Elasticsearch...");
        try {
            ruleService.exportRuleToElasticsearch();
        } catch (Exception ex) {
            log.error("Failed to export rules to Elasticsearch!", ex);
        }

        log.info("[Schedule Task] Completed Exporting Doc Store Rules to Elasticsearch!");
    }
}
