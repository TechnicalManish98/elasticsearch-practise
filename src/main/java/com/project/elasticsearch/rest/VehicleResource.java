package com.project.elasticsearch.rest;

import com.project.elasticsearch.documents.Vehicle;
import com.project.elasticsearch.search.SearchRequestDto;
import com.project.elasticsearch.serviceImpl.VehicleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleResource {
    @Autowired
    private VehicleServiceImpl vehicleService;

    @PostMapping("/saveVehicle")
    public Boolean saveVehicle(@RequestBody Vehicle vehicle) {
        return vehicleService.saveVehicle(vehicle);
    }

    @GetMapping("/{vehicleId}")
    public Vehicle getVehicleById(@PathVariable String vehicleId) {
        return vehicleService.getVehicleById(vehicleId);
    }

    @PostMapping("/search")
    List<Vehicle> search(@RequestBody SearchRequestDto dto) {
        return vehicleService.search(dto);
    }

    @GetMapping("/search/{date}")
    List<Vehicle> getAllVehiclesCreatedSince(@PathVariable @DateTimeFormat(pattern = "yyyy-mm-dd") Date date) {
        return vehicleService.getAllVehiclesCreatedSince(date);
    }

    @PostMapping("/search/{date}")
    List<Vehicle> getFilteredVehiclesCreatedSince(@RequestBody SearchRequestDto dto, @PathVariable @DateTimeFormat(pattern = "yyyy-mm-dd") Date date) {
        return vehicleService.getFilteredVehiclesCreatedSince(dto, date);
    }


}
