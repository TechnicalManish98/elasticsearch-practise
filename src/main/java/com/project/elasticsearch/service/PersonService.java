package com.project.elasticsearch.service;

import com.project.elasticsearch.documents.Person;
import org.springframework.stereotype.Service;


public interface PersonService {

    Person savePerson(Person person);
    Person findPersonById(String id);
}
