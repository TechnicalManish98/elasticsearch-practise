package com.project.elasticsearch.serviceImpl;

import com.project.elasticsearch.documents.Person;
import com.project.elasticsearch.repository.PersonRepository;
import com.project.elasticsearch.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person savePerson(Person person) {
        System.out.println(person.toString()+"++++++++");
        this.personRepository.save(person);
        return person;
    }

    @Override
    public Person findPersonById(String id) throws RuntimeException{
        return personRepository.findById(id).orElse(null);
    }
}
