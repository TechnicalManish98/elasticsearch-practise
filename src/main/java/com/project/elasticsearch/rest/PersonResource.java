package com.project.elasticsearch.rest;

import com.project.elasticsearch.documents.Person;
import com.project.elasticsearch.service.PersonService;
import com.project.elasticsearch.serviceImpl.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
public class PersonResource {

    @Autowired
    private PersonService personService;

    @PostMapping("/save")
    Person savePerson(@RequestBody Person person) {
        return this.personService.savePerson(person);
    }

    @GetMapping("/{id}")
    Person findPersonById(@PathVariable String id) {
        return this.personService.findPersonById(id);

    }

}
