package com.example.servingwebcontent.model;

import java.time.LocalDate;

public class Person {
    private String name;
    private String gender;
    private LocalDate dob;
    private LocalDate dod;
    private int phone;
    private String address;
    private Person father;
    private Person mother;
    private Person spouse;

    public Person() {
    }

    public Person(String name, String gender, LocalDate dob, LocalDate dod, int phone, String address) {
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.dod = dod;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public LocalDate getDod() {
        return dod;
    }

    public void setDod(LocalDate dod) {
        this.dod = dod;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Person getFather() {
        return father;
    }

    public void setFather(Person father) {
        this.father = father;
    }

    public Person getMother() {
        return mother;
    }

    public void setMother(Person mother) {
        this.mother = mother;
    }

    public Person getSpouse() {
        return spouse;
    }

    public void setSpouse(Person spouse) {
        this.spouse = spouse;
        if (spouse != null && spouse.getSpouse() != this) {
            spouse.setSpouse(this);
        }
    }

    public void setParents(Person father, Person mother) {
        this.father = father;
        this.mother = mother;
    }
}


