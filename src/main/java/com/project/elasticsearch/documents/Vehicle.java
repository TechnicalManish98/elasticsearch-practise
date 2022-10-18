package com.project.elasticsearch.documents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
public class Vehicle {

    private String id;

    private String vehicleName;
    private String vehicleNumber;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date createdDate;
}