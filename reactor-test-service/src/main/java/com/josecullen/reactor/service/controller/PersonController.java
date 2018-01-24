package com.josecullen.reactor.service.controller;

import com.josecullen.reactor.domain.model.ElasticPerson;
import com.josecullen.reactor.domain.model.Person;
import com.josecullen.reactor.domain.repository.ElasticPersonRepository;
import com.josecullen.reactor.domain.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by josecullen on 23/01/18.
 */
@RestController
public class PersonController {

    private PersonRepository personRepository;
    private ElasticPersonRepository elasticPersonRepository;

    @Autowired
    public PersonController(PersonRepository personRepository, ElasticPersonRepository elasticPersonRepository) {
        this.personRepository = personRepository;
        this.elasticPersonRepository = elasticPersonRepository;
    }

    @PostMapping("/person")
    public Mono<Person> create(@RequestBody Person person){
        elasticPersonRepository.save(new ElasticPerson(person.getName()));
        return personRepository.save(person);
    }

    @GetMapping("/person")
    public Flux<Person> retrieveAll(){
        return null;//personRepository.findAll();
    }

    @GetMapping("/person/{id}")
    public Mono<Person> retrieve(@PathVariable String id){
        return personRepository.findById(id);
    }

    @DeleteMapping("/person/{id}")
    public Mono<Void> delete(@PathVariable String id){
        return personRepository.deleteById(id);
    }

}
