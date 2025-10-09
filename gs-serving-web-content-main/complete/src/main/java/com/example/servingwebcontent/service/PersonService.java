package com.example.servingwebcontent.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.servingwebcontent.model.Person;

@Service
public class PersonService {
    private final List<Person> people = new ArrayList<>();

    public void create(Person entity) {
        if (entity != null) {
            people.add(entity);
        }
    }

    public boolean update(String name, Person newData) {
        Person person = findById(name);
        if (person == null) return false;
        if (newData.getAddress() != null) person.setAddress(newData.getAddress());
        if (newData.getPhone() != 0) person.setPhone(newData.getPhone());
        if (newData.getName() != null) person.setName(newData.getName());
        if (newData.getGender() != null) person.setGender(newData.getGender());
        if (newData.getDob() != null) person.setDob(newData.getDob());
        if (newData.getDod() != null) person.setDod(newData.getDod());
        return true;
    }

    public boolean delete(String name) {
        Person person = findById(name);
        if (person == null) return false;
        return people.remove(person);
    }

    public Person findById(String name) {
        for (Person p : people) {
            if (p.getName() != null && p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    public List<Person> findAll() {
        return people;
    }
}


