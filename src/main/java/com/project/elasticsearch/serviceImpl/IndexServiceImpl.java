package com.project.elasticsearch.serviceImpl;

import com.project.elasticsearch.configuration.IndexNameConfig;
import com.project.elasticsearch.helper.Util;
import com.project.elasticsearch.service.IndexService;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {

    private static final Logger log = LoggerFactory.getLogger(IndexServiceImpl.class);

    @Autowired
    private RestHighLevelClient client;

    private final List<String> ListOfindex = Arrays.asList(IndexNameConfig.vehicleIndex);

    @PostConstruct
    public void tryToCreateIndex() throws IOException {
        String settings = Util.loadAsString("static/es-settings.json");

        for (String index : ListOfindex) {

            try {
                boolean indexExists = client.indices().exists(new GetIndexRequest(index), RequestOptions.DEFAULT);
                if (indexExists) {
                    continue;
                }
                String mappings = Util.loadAsString("static/mappings/" + index + ".json");
                if (settings == null || mappings == null) {
                    log.error("failed to create index with name" + index);
                    continue;
                }
                CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
                createIndexRequest.settings(settings, XContentType.JSON);
                createIndexRequest.mapping(mappings, XContentType.JSON);

                client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            } catch (Exception exp) {
                log.error(exp.getMessage(), exp);
            }
        }

    }

    @Override
    public Boolean deleteIndex(String IndexName) {

        try {
            boolean indexExists = client.indices().exists(new GetIndexRequest(IndexName), RequestOptions.DEFAULT);
            if (indexExists) {
                client.indices().delete(new DeleteIndexRequest(IndexName), RequestOptions.DEFAULT);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
