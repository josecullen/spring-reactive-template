package com.josecullen.reactor.domain.repository;

import com.josecullen.reactor.domain.model.ElasticPerson;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


/**
 * Created by josecullen on 23/01/18.
 */
@Repository
public interface ElasticPersonRepository extends ElasticsearchRepository<ElasticPerson, String>{

}
