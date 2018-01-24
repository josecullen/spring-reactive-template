package com.josecullen.reactor.domain.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by josecullen on 23/01/18.
 */
@org.springframework.data.elasticsearch.annotations.Document(
    indexName = "person",
    type = "person"
)
public class ElasticPerson {
    private String id;
    private String name;
    private String lastName;
    private int age;

    public ElasticPerson() {
    }

    public ElasticPerson(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getId() {
        return id;
    }
}
