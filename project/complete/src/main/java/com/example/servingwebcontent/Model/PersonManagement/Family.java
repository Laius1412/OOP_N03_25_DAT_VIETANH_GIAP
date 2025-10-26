package com.example.servingwebcontent.Model.PersonManagement;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Family Entity - Đại diện cho một hộ gia đình trong dòng họ
 */
@Entity
@Table(name = "families")
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "family_id", unique = true, nullable = false, length = 50)
    private String familyId;

    @Column(name = "family_name", nullable = false, length = 200)
    private String nameFamily;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Danh sách thành viên trong gia đình
    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Person> familyMembers = new ArrayList<>();

    // Constructors
    public Family() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Family(String familyId, String nameFamily) {
        this();
        this.familyId = familyId;
        this.nameFamily = nameFamily;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getNameFamily() {
        return nameFamily;
    }

    public void setNameFamily(String nameFamily) {
        this.nameFamily = nameFamily;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Person> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(List<Person> familyMembers) {
        this.familyMembers = familyMembers;
    }

    // Helper methods
    public void addFamilyMember(Person person) {
        if (person != null && !familyMembers.contains(person)) {
            familyMembers.add(person);
            person.setFamily(this);
        }
    }

    public void removeFamilyMember(Person person) {
        if (person != null && familyMembers.contains(person)) {
            familyMembers.remove(person);
            person.setFamily(null);
        }
    }

    public int getMemberCount() {
        return familyMembers.size();
    }

    public Person getOldestMaleMember() {
        return familyMembers.stream()
                .filter(person -> person.getGender() == Gender.MALE && person.isAlive())
                .min((p1, p2) -> {
                    if (p1.getDob() == null) return 1;
                    if (p2.getDob() == null) return -1;
                    return p1.getDob().compareTo(p2.getDob());
                })
                .orElse(null);
    }

    public void updateFamilyName() {
        Person oldestMale = getOldestMaleMember();
        if (oldestMale != null) {
            this.nameFamily = oldestMale.getName() + " Family";
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Family{" +
                "id=" + id +
                ", familyId='" + familyId + '\'' +
                ", nameFamily='" + nameFamily + '\'' +
                ", memberCount=" + getMemberCount() +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}