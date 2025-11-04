package com.example.servingwebcontent.Test.TestPerson;

import com.example.servingwebcontent.model.PersonManagement.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

// Plain Java POJO (no JPA) for standalone Person testing
public class SimplePerson {
    private Long id;
    private String name;
    private Gender gender = Gender.MALE;
    private String phone;
    private String address;
    private LocalDate dob; // date of birth
    private LocalDate dod; // date of death (optional)
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public LocalDate getDod() { return dod; }
    public void setDod(LocalDate dod) { this.dod = dod; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public boolean isAlive() { return this.dod == null; }
}

