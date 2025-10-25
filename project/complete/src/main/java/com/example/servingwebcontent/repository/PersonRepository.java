package com.example.servingwebcontent.repository;

import com.example.servingwebcontent.Model.PersonManagement.Gender;
import com.example.servingwebcontent.Model.PersonManagement.Person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    // Tìm kiếm theo tên (case insensitive)
    List<Person> findByNameContainingIgnoreCase(String name);

    // Tìm kiếm theo giới tính
    List<Person> findByGender(Gender gender);

    // Tìm kiếm theo ngày sinh
    List<Person> findByDob(LocalDate dob);

    // Tìm kiếm theo khoảng tuổi
    @Query("SELECT p FROM Person p WHERE p.dob BETWEEN :startDate AND :endDate")
    List<Person> findByAgeRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // Tìm kiếm người còn sống
    List<Person> findByDodIsNull();

    // Tìm kiếm người đã mất
    List<Person> findByDodIsNotNull();

    // Tìm kiếm theo địa chỉ
    List<Person> findByAddressContainingIgnoreCase(String address);

    // Tìm kiếm theo số điện thoại
    Optional<Person> findByPhone(String phone);

    // Tìm kiếm con của một người (theo bố)
    List<Person> findByFather(Person father);

    // Tìm kiếm con của một người (theo mẹ)
    List<Person> findByMother(Person mother);

    // Tìm kiếm vợ/chồng
    List<Person> findBySpouse(Person spouse);

    // Tìm kiếm theo tên và giới tính
    List<Person> findByNameContainingIgnoreCaseAndGender(String name, Gender gender);

    // Tìm kiếm tổng hợp
    @Query("SELECT p FROM Person p WHERE " +
           "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:gender IS NULL OR p.gender = :gender) AND " +
           "(:isAlive IS NULL OR (:isAlive = true AND p.dod IS NULL) OR (:isAlive = false AND p.dod IS NOT NULL))")
    List<Person> findByAdvancedSearch(@Param("name") String name, 
                                    @Param("gender") Gender gender, 
                                    @Param("isAlive") Boolean isAlive);

    // Đếm số lượng thành viên
    long count();

    // Đếm theo giới tính
    long countByGender(Gender gender);

    // Đếm người còn sống
    long countByDodIsNull();

    // Đếm người đã mất
    long countByDodIsNotNull();

    // Tìm kiếm gần đây (theo ngày tạo)
    List<Person> findTop10ByOrderByCreatedAtDesc();

    // Tìm kiếm theo từ khóa tổng hợp
    @Query("SELECT p FROM Person p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.address) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.phone) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Person> findByKeyword(@Param("keyword") String keyword);
}