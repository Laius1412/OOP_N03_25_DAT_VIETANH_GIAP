package com.example.servingwebcontent.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.servingwebcontent.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    
    Optional<Person> findByNameIgnoreCase(String name);
    
    List<Person> findByGender(String gender);
    
    List<Person> findByAddressContainingIgnoreCase(String address);
    
    @Query("SELECT p FROM Person p WHERE p.father = :father OR p.mother = :mother")
    List<Person> findChildren(@Param("father") Person father, @Param("mother") Person mother);
    
    @Query("SELECT p FROM Person p WHERE p.spouse = :spouse")
    Optional<Person> findSpouse(@Param("spouse") Person spouse);
}

