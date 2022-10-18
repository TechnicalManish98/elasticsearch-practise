package com.project.elasticsearch.helper;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.core.io.ClassPathResource;
import java.io.File;
import java.nio.file.Files;

public class Util {

    private static final Logger log = LoggerFactory.getLogger(Util.class);
    public static String loadAsString(String path){
        try {
            File resource = new ClassPathResource(path).getFile();
            return new String(Files.readAllBytes(resource.toPath()));
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return null;
        }
    }

}
