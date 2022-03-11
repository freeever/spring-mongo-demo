package com.spring.mongo.demo.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class DataQueryLoader implements ApplicationRunner {

    private Map<String, String> ruleQueries = new HashMap<>();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("DataQueryLoader is running...");
        this.loadRuleQueries();
    }

    private void loadRuleQueries() {
        this.ruleQueries = this.loadDataQueries("query/ruleQueries.xml");
    }

    private Map<String, String> loadDataQueries(String fileName) {
        log.info("Loading data queries from {}...", fileName);

        Map<String, String> queries = new HashMap<>();
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            InputStream inputStream = classLoader.getResourceAsStream(fileName);
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(inputStream);

            StringBuilder sb = null;
            String id = null;
            while (reader.hasNext()) {
                XMLEvent nextEvent = reader.nextEvent();
                if (nextEvent.isStartElement()) {
                    sb = new StringBuilder();
                    StartElement startElement = nextEvent.asStartElement();
                    switch (startElement.getName().getLocalPart()) {
                        case "Query":
                            Attribute idAttr = startElement.getAttributeByName(new QName("id"));
                            if (idAttr != null) {
                                id = idAttr.getValue();
                            }
                            break;
                    }
                }
                if (nextEvent.getEventType() == XMLStreamConstants.CHARACTERS) {
                    sb.append(nextEvent.asCharacters().getData());
                }

                if (nextEvent.isEndElement()) {
                    EndElement endElement = nextEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("Query")) {
                        queries.put(id, sb.toString());
                    }
                }
            }
        } catch (Exception ex) {
            log.error("Failed to load queries from " + fileName, ex);
        }

        return queries;
    }

    public String getRuleQuery(String queryId, String... params) {
        String query = this.ruleQueries.get(queryId);
        if (StringUtils.isBlank(query)) {
            return null;
        }

        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                query = query.replace("{" + i + "}", params[i].replaceAll("'", "\'"));
            }
        }

        return query;
    }

}
