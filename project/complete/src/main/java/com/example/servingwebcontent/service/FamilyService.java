package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.PersonManagement.Family;
import com.example.servingwebcontent.model.PersonManagement.Person;
import com.example.servingwebcontent.repository.FamilyRepository;
import com.example.servingwebcontent.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FamilyService {

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private PersonRepository personRepository;

    // Tạo dữ liệu mẫu nếu chưa có
    public void createSampleData() {
        if (familyRepository.count() == 0) {
            // Tạo một số gia đình mẫu
            Family family1 = new Family("FAM001", "Nguyễn Văn A Family");
            familyRepository.save(family1);

            Family family2 = new Family("FAM002", "Trần Thị B Family");
            familyRepository.save(family2);

            Family family3 = new Family("FAM003", "Lê Văn C Family");
            familyRepository.save(family3);

            System.out.println("Đã tạo dữ liệu gia đình mẫu thành công!");
        }
    }

    // Lấy tất cả gia đình
    public List<Family> getAllFamilies() {
        return familyRepository.findAll();
    }

    // Lấy gia đình theo ID
    public Optional<Family> getFamilyById(Long id) {
        return familyRepository.findById(id);
    }

    // Lấy gia đình theo familyId
    public Optional<Family> getFamilyByFamilyId(String familyId) {
        return familyRepository.findByFamilyId(familyId);
    }

    // Lưu hoặc cập nhật gia đình
    public Family saveFamily(Family family) {
        family.setUpdatedAt(LocalDateTime.now());
        return familyRepository.save(family);
    }

    // Xóa gia đình
    public void deleteFamily(Long id) {
        familyRepository.deleteById(id);
    }

    // Tạo gia đình mới
    public Family createFamily(String familyId, String nameFamily) {
        if (familyRepository.existsByFamilyId(familyId)) {
            throw new IllegalArgumentException("Family ID đã tồn tại: " + familyId);
        }
        
        Family family = new Family(familyId, nameFamily);
        return familyRepository.save(family);
    }

    // Cập nhật thông tin gia đình
    public Family updateFamily(Long id, String familyId, String nameFamily) {
        Optional<Family> familyOptional = familyRepository.findById(id);
        if (familyOptional.isPresent()) {
            Family family = familyOptional.get();
            
            // Kiểm tra familyId có thay đổi không và có trùng không
            if (!family.getFamilyId().equals(familyId) && familyRepository.existsByFamilyId(familyId)) {
                throw new IllegalArgumentException("Family ID đã tồn tại: " + familyId);
            }
            
            family.setFamilyId(familyId);
            family.setNameFamily(nameFamily);
            family.setUpdatedAt(LocalDateTime.now());
            return familyRepository.save(family);
        }
        return null;
    }

    // Thêm thành viên vào gia đình
    public boolean addMemberToFamily(Long familyId, Long personId) {
        Optional<Family> familyOptional = familyRepository.findById(familyId);
        Optional<Person> personOptional = personRepository.findById(personId);
        
        if (familyOptional.isPresent() && personOptional.isPresent()) {
            Family family = familyOptional.get();
            Person person = personOptional.get();
            
            // Kiểm tra người này đã thuộc gia đình khác chưa
            if (person.getFamily() != null && !person.getFamily().getId().equals(familyId)) {
                throw new IllegalArgumentException("Người này đã thuộc gia đình khác");
            }
            
            family.addFamilyMember(person);
            familyRepository.save(family);
            
            // Cập nhật tên gia đình nếu cần
            family.updateFamilyName();
            familyRepository.save(family);
            
            return true;
        }
        return false;
    }

    // Xóa thành viên khỏi gia đình
    public boolean removeMemberFromFamily(Long familyId, Long personId) {
        Optional<Family> familyOptional = familyRepository.findById(familyId);
        Optional<Person> personOptional = personRepository.findById(personId);
        
        if (familyOptional.isPresent() && personOptional.isPresent()) {
            Family family = familyOptional.get();
            Person person = personOptional.get();
            
            family.removeFamilyMember(person);
            familyRepository.save(family);
            
            // Cập nhật tên gia đình nếu cần
            family.updateFamilyName();
            familyRepository.save(family);
            
            return true;
        }
        return false;
    }

    // Tìm kiếm gia đình theo từ khóa
    public List<Family> searchFamilies(String keyword) {
        return familyRepository.findByKeyword(keyword);
    }

    // Tìm kiếm gia đình theo tên
    public List<Family> searchByName(String nameFamily) {
        return familyRepository.findByNameFamilyContainingIgnoreCase(nameFamily);
    }

    // Lấy gia đình có thành viên cụ thể
    public Optional<Family> getFamilyByPersonId(Long personId) {
        return familyRepository.findByPersonId(personId);
    }

    // Lấy danh sách gia đình theo số thành viên
    public List<Family> getFamiliesByMemberCount(int minMembers, int maxMembers) {
        return familyRepository.findByMemberCountRange(minMembers, maxMembers);
    }

    // Lấy gia đình có nhiều thành viên nhất
    public List<Family> getFamiliesWithMostMembers() {
        return familyRepository.findAllOrderByMemberCountDesc();
    }

    // Lấy gia đình có ít thành viên nhất
    public List<Family> getFamiliesWithLeastMembers() {
        return familyRepository.findAllOrderByMemberCountAsc();
    }

    // Lấy thống kê
    public long getTotalFamilies() {
        return familyRepository.count();
    }

    public long getFamiliesWithMembersCount() {
        return familyRepository.findByMemberCountRange(1, Integer.MAX_VALUE).size();
    }

    public long getEmptyFamiliesCount() {
        return familyRepository.findByMemberCountRange(0, 0).size();
    }

    // Lấy gia đình gần đây
    public List<Family> getRecentFamilies() {
        return familyRepository.findTop10ByOrderByCreatedAtDesc();
    }

    // Tự động cập nhật tên gia đình dựa trên con trai lớn tuổi nhất
    public void updateFamilyNames() {
        List<Family> families = familyRepository.findAll();
        for (Family family : families) {
            family.updateFamilyName();
            familyRepository.save(family);
        }
    }

    // Kiểm tra gia đình có thể xóa không (không có thành viên)
    public boolean canDeleteFamily(Long familyId) {
        Optional<Family> familyOptional = familyRepository.findById(familyId);
        if (familyOptional.isPresent()) {
            Family family = familyOptional.get();
            return family.getMemberCount() == 0;
        }
        return false;
    }

    // Lấy danh sách thành viên của gia đình
    public List<Person> getFamilyMembers(Long familyId) {
        Optional<Family> familyOptional = familyRepository.findById(familyId);
        if (familyOptional.isPresent()) {
            return familyOptional.get().getFamilyMembers();
        }
        return List.of();
    }

    // Di chuyển thành viên từ gia đình này sang gia đình khác
    public boolean moveMemberToFamily(Long fromFamilyId, Long toFamilyId, Long personId) {
        // Xóa khỏi gia đình cũ
        if (fromFamilyId != null) {
            removeMemberFromFamily(fromFamilyId, personId);
        }
        
        // Thêm vào gia đình mới
        if (toFamilyId != null) {
            return addMemberToFamily(toFamilyId, personId);
        }
        
        return true;
    }
}
