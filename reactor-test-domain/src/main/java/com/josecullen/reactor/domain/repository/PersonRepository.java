package com.josecullen.reactor.domain.repository;

import com.josecullen.reactor.domain.model.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by josecullen on 23/01/18.
 */
@Repository
public interface PersonRepository extends ReactiveMongoRepository<Person, String>{
}
