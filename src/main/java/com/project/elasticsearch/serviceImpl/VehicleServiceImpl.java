package com.project.elasticsearch.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.elasticsearch.configuration.IndexNameConfig;
import com.project.elasticsearch.documents.Vehicle;
import com.project.elasticsearch.search.SearchRequestDto;
import com.project.elasticsearch.search.searchUtil.searchUtil;
import com.project.elasticsearch.service.VehicleService;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger Log = LoggerFactory.getLogger(VehicleServiceImpl.class);

    @Autowired
    private RestHighLevelClient client;


    @Override
    public Boolean saveVehicle(Vehicle vehicle) {
        try {
            String vehicleAsString = MAPPER.writeValueAsString(vehicle);
            IndexRequest request = new IndexRequest(IndexNameConfig.vehicleIndex);
            request.id(vehicle.getId());
            request.source(vehicleAsString, XContentType.JSON);

            IndexResponse response = client.index(request, RequestOptions.DEFAULT);

            return response != null && response.status().equals(RestStatus.CREATED);

        } catch (Exception e) {
            Log.error(e.getMessage(), e);
            return false;

        }
    }

    @Override
    public Vehicle getVehicleById(String vehicleId) {
        try {

            GetResponse vehicleIndex = client.get(new GetRequest(IndexNameConfig.vehicleIndex, vehicleId), RequestOptions.DEFAULT);
            if (vehicleIndex == null) {
                return null;
            }
            return MAPPER.readValue(vehicleIndex.getSourceAsString(), Vehicle.class);
        } catch (Exception e) {
            Log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<Vehicle> search(SearchRequestDto dto) {

        SearchRequest request = searchUtil.buildSearchRequest(IndexNameConfig.vehicleIndex, dto);
        return searchInternal(request);
    }

    public List<Vehicle> getAllVehiclesCreatedSince(Date date) {

        SearchRequest request = searchUtil.buildSearchRequest(IndexNameConfig.vehicleIndex, "createdDate", date);
        return searchInternal(request);
    }

    private List<Vehicle> searchInternal(SearchRequest request) {
        if (ObjectUtils.isEmpty(request)) {
            Log.error("Failed to build search request...");
            return null;
        }

        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHit[] searchHits = response.getHits().getHits();
            List<Vehicle> vehicles = new ArrayList<>(searchHits.length);
            Arrays.stream(searchHits).forEach(hit -> {
                try {
                    vehicles.add(MAPPER.readValue(hit.getSourceAsString(), Vehicle.class));
                } catch (JsonProcessingException e) {
                    Log.error(e.getMessage(), e);
                }
            });
            return vehicles;
        } catch (Exception e) {
            Log.error(e.getMessage(), e);
            return null;
        }
    }


    public List<Vehicle> getFilteredVehiclesCreatedSince(SearchRequestDto dto, Date date) {
        SearchRequest request = searchUtil.buildSearchRequest(IndexNameConfig.vehicleIndex, dto, date);
        return searchInternal(request);

    }
}
