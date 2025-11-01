package com.example.servingwebcontent.repository;

import com.example.servingwebcontent.model.PersonManagement.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {

    // Tìm kiếm gia đình theo familyId
    Optional<Family> findByFamilyId(String familyId);

    // Tìm kiếm gia đình theo tên (case insensitive)
    List<Family> findByNameFamilyContainingIgnoreCase(String nameFamily);

    // Tìm kiếm gia đình có thành viên cụ thể
    @Query("SELECT f FROM Family f JOIN f.familyMembers p WHERE p.id = :personId")
    Optional<Family> findByPersonId(@Param("personId") Long personId);

    // Lấy danh sách gia đình có số thành viên trong khoảng
    @Query("SELECT f FROM Family f WHERE SIZE(f.familyMembers) BETWEEN :minMembers AND :maxMembers")
    List<Family> findByMemberCountRange(@Param("minMembers") int minMembers, @Param("maxMembers") int maxMembers);

    // Lấy gia đình có nhiều thành viên nhất
    @Query("SELECT f FROM Family f ORDER BY SIZE(f.familyMembers) DESC")
    List<Family> findAllOrderByMemberCountDesc();

    // Lấy gia đình có ít thành viên nhất
    @Query("SELECT f FROM Family f ORDER BY SIZE(f.familyMembers) ASC")
    List<Family> findAllOrderByMemberCountAsc();

    // Tìm kiếm gia đình theo từ khóa trong tên hoặc familyId
    @Query("SELECT f FROM Family f WHERE " +
           "LOWER(f.nameFamily) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(f.familyId) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Family> findByKeyword(@Param("keyword") String keyword);

    // Kiểm tra familyId đã tồn tại chưa
    boolean existsByFamilyId(String familyId);

    // Đếm số gia đình
    long count();

    // Lấy gia đình được tạo gần đây nhất
    @Query("SELECT f FROM Family f ORDER BY f.createdAt DESC")
    List<Family> findTop10ByOrderByCreatedAtDesc();
}
