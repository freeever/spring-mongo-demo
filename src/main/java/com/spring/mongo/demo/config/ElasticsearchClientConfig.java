package com.spring.mongo.demo.config;

import com.spring.mongo.demo.dto.docstore.DocStoreConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Slf4j
@Configuration
@EnableElasticsearchRepositories
public class ElasticsearchClientConfig extends AbstractElasticsearchConfiguration {

    @Autowired
    private Environment env;

    @Bean
    public DocStoreConfiguration docStoreConfiguration(@Value("${elk.host}") String elkHost,
                                                       @Value("${elk.port}") String elkPort,
                                                       @Value("${elk.ssl}") boolean elkSsl,
                                                       @Value("${elk.index.rule}") String elkIndexRule) {
        return DocStoreConfiguration.builder()
                .elkHost(elkHost)
                .elkPort(elkPort)
                .elkSsl(elkSsl)
                .elkRuleIndex(elkIndexRule)
                .build();
    }

    @Qualifier("elasticsearchClient")
    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {

        String elkHost = env.getProperty("elk.host");
        String elkPort = env.getProperty("elk.port");
        String elkSsl = env.getProperty("elk.ssl");
        String userInfo = env.getProperty("elk.user.info");

        final ClientConfiguration.MaybeSecureClientConfigurationBuilder builder =
                ClientConfiguration.builder().connectedTo(elkHost + ":" + elkPort);

        boolean isSsl = elkSsl != null && Boolean.valueOf(elkSsl);
        if (isSsl) {
            builder.usingSsl();
        }
        if (userInfo != null) {
            final String[] userPass = userInfo.split(":", 2);
            builder.withBasicAuth(userPass[0], userPass[1]);
        }
        log.info("Elasticsearch server [{}:{}] ssl[{}] auth[{}]", elkHost, elkPort, isSsl, userInfo != null);

        return RestClients.create(builder.build()).rest();
    }
}
