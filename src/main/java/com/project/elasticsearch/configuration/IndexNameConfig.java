package com.project.elasticsearch.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Getter
@Setter
@Configuration
public class IndexNameConfig {

    public static final String personIndex = "person";

    public static final String vehicleIndex = "vehicle";

}
