package com.project.elasticsearch.service;

import com.project.elasticsearch.documents.Vehicle;
import com.project.elasticsearch.search.SearchRequestDto;

import java.util.Date;
import java.util.List;

public interface VehicleService {

    Boolean saveVehicle(Vehicle vehicle);

    Vehicle getVehicleById(String vehicleId);

    List<Vehicle> search(SearchRequestDto dto);

    List<Vehicle> getAllVehiclesCreatedSince(Date date);

    List<Vehicle> getFilteredVehiclesCreatedSince(SearchRequestDto dto, Date date);

}