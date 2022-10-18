package com.project.elasticsearch.rest;

import com.project.elasticsearch.documents.Person;
import com.project.elasticsearch.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/index")
public class IndexController {

    @Autowired
    private IndexService indexService;

    @DeleteMapping("delete/{indexName}")
    Boolean savePerson(@PathVariable String indexName) {
        return this.indexService.deleteIndex(indexName);
    }

}
