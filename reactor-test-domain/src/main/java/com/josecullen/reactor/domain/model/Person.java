package com.josecullen.reactor.domain.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by josecullen on 23/01/18.
 */
@Document
public class Person {
    private String id;
    private String name;
    private String lastName;
    private int age;

    public Person() {
    }

    public Person(String name) {
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
