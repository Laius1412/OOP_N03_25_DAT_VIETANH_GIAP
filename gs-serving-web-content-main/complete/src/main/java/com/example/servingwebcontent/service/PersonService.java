package com.example.servingwebcontent.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.servingwebcontent.model.Person;
import com.example.servingwebcontent.repository.PersonRepository;

@Service
@Transactional
public class PersonService {
    
    @Autowired
    private PersonRepository personRepository;

    public Person create(Person entity) {
        if (entity != null) {
            return personRepository.save(entity);
        }
        return null;
    }

    public boolean update(Long id, Person newData) {
        Optional<Person> personOpt = personRepository.findById(id);
        if (personOpt.isEmpty()) return false;
        
        Person person = personOpt.get();
        if (newData.getAddress() != null) person.setAddress(newData.getAddress());
        if (newData.getPhone() != null) person.setPhone(newData.getPhone());
        if (newData.getName() != null) person.setName(newData.getName());
        if (newData.getGender() != null) person.setGender(newData.getGender());
        if (newData.getDob() != null) person.setDob(newData.getDob());
        if (newData.getDod() != null) person.setDod(newData.getDod());
        
        personRepository.save(person);
        return true;
    }

    public boolean delete(Long id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Person findById(Long id) {
        return personRepository.findById(id).orElse(null);
    }
    
    public Person findByName(String name) {
        return personRepository.findByNameIgnoreCase(name).orElse(null);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }
    
    public List<Person> findByGender(String gender) {
        return personRepository.findByGender(gender);
    }
    
    public List<Person> findByAddress(String address) {
        return personRepository.findByAddressContainingIgnoreCase(address);
    }
}


