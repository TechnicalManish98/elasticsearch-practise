package com.project.elasticsearch.repository;


import com.project.elasticsearch.documents.Person;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends ElasticsearchRepository<Person, String> {

}
