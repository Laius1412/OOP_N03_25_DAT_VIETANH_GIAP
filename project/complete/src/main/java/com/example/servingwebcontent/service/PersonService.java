package com.example.servingwebcontent.service;

import com.example.servingwebcontent.Model.PersonManagement.Gender;
import com.example.servingwebcontent.Model.PersonManagement.Person;
import com.example.servingwebcontent.Model.PersonManagement.Family;
import com.example.servingwebcontent.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    // Tạo dữ liệu mẫu nếu chưa có
    public void createSampleData() {
        if (personRepository.count() == 0) {
            // Tạo một số thành viên mẫu
            Person person1 = new Person("Nguyễn Văn A", Gender.MALE, LocalDate.of(1980, 1, 1), "Hà Nội", "0123456789");
            personRepository.save(person1);

            Person person2 = new Person("Trần Thị B", Gender.FEMALE, LocalDate.of(1985, 5, 15), "TP.HCM", "0987654321");
            personRepository.save(person2);

            Person person3 = new Person("Lê Văn C", Gender.MALE, LocalDate.of(1990, 8, 20), "Đà Nẵng", "0369258147");
            personRepository.save(person3);

            System.out.println("Đã tạo dữ liệu mẫu thành công!");
        }
    }

    // Lấy tất cả thành viên
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    // Lấy thành viên theo ID
    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    // Lưu hoặc cập nhật thành viên
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    // Xóa thành viên
    public void deletePerson(Long id) {
        Optional<Person> personOptional = personRepository.findById(id);
        if (!personOptional.isPresent()) {
            throw new IllegalArgumentException("Không tìm thấy thành viên với ID: " + id);
        }
        
        Person person = personOptional.get();
        
        // Kiểm tra xem có giao dịch nào tham chiếu đến thành viên này không
        long transactionCount = personRepository.countTransactionsByContributorId(id);
        if (transactionCount > 0) {
            throw new IllegalStateException("Thành viên này đang được tham chiếu trong " + transactionCount + " giao dịch tài chính. Vui lòng xóa các giao dịch liên quan trước khi xóa thành viên.");
        }
        
        // Xóa quan hệ gia đình trước khi xóa person
        // Nullify spouse relationship
        if (person.getSpouse() != null) {
            Person spouse = person.getSpouse();
            spouse.setSpouse(null);
            personRepository.save(spouse);
        }
        
        // Nullify children's relationships
        List<Person> children = getChildren(person);
        for (Person child : children) {
            if (child.getFather() != null && child.getFather().getId().equals(id)) {
                child.setFather(null);
            }
            if (child.getMother() != null && child.getMother().getId().equals(id)) {
                child.setMother(null);
            }
            personRepository.save(child);
        }
        
        personRepository.deleteById(id);
    }
    
    // Kiểm tra xem thành viên có thể xóa được không
    public boolean canDeletePerson(Long id) {
        long transactionCount = personRepository.countTransactionsByContributorId(id);
        return transactionCount == 0;
    }

    // Tìm kiếm theo tên
    public List<Person> searchByName(String name) {
        return personRepository.findByNameContainingIgnoreCase(name);
    }

    // Tìm kiếm theo giới tính
    public List<Person> searchByGender(Gender gender) {
        return personRepository.findByGender(gender);
    }

    // Tìm kiếm người còn sống
    public List<Person> getAlivePersons() {
        return personRepository.findByDodIsNull();
    }

    // Tìm kiếm người đã mất
    public List<Person> getDeceasedPersons() {
        return personRepository.findByDodIsNotNull();
    }

    // Tìm kiếm theo từ khóa
    public List<Person> searchByKeyword(String keyword) {
        return personRepository.findByKeyword(keyword);
    }

    // Tìm kiếm nâng cao
    public List<Person> advancedSearch(String name, Gender gender, Boolean isAlive) {
        return personRepository.findByAdvancedSearch(name, gender, isAlive);
    }

    // Lấy con của một người
    public List<Person> getChildren(Person parent) {
        List<Person> children = personRepository.findByFather(parent);
        children.addAll(personRepository.findByMother(parent));
        return children;
    }

    // Lấy vợ/chồng
    public List<Person> getSpouses(Person person) {
        return personRepository.findBySpouse(person);
    }

    // Lấy thống kê
    public long getTotalPersons() {
        return personRepository.count();
    }

    public long getPersonsByGender(Gender gender) {
        return personRepository.countByGender(gender);
    }

    public long getAlivePersonsCount() {
        return personRepository.countByDodIsNull();
    }

    public long getDeceasedPersonsCount() {
        return personRepository.countByDodIsNotNull();
    }

    // Tạo thành viên mới với validation
    public Person createPerson(String name, Gender gender, LocalDate dob, String address, String phone) {
        Person person = new Person(name, gender, dob, address, phone);
        return personRepository.save(person);
    }

    // Cập nhật thông tin thành viên
    public Person updatePerson(Long id, String name, Gender gender, LocalDate dob, 
                              LocalDate dod, String address, String phone, 
                              Person father, Person mother, Person spouse) {
        Optional<Person> personOptional = personRepository.findById(id);
        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            person.setName(name);
            person.setGender(gender);
            person.setDob(dob);
            person.setDod(dod);
            person.setAddress(address);
            person.setPhone(phone);
            person.setFather(father);
            person.setMother(mother);
            person.setSpouse(spouse);
            return personRepository.save(person);
        }
        return null;
    }

    // Kiểm tra quan hệ gia đình
    public boolean isValidFamilyRelation(Person person, Person parent) {
        if (person == null || parent == null) return false;
        if (person.getId().equals(parent.getId())) return false;
        
        // Kiểm tra không được là bố/mẹ của chính mình
        if (person.getFather() != null && person.getFather().getId().equals(parent.getId())) return false;
        if (person.getMother() != null && person.getMother().getId().equals(parent.getId())) return false;
        
        return true;
    }

    // Lấy thành viên gần đây
    public List<Person> getRecentPersons() {
        return personRepository.findTop10ByOrderByCreatedAtDesc();
    }

    // Tìm kiếm theo khoảng tuổi
    public List<Person> getPersonsByAgeRange(int minAge, int maxAge) {
        LocalDate endDate = LocalDate.now().minusYears(minAge);
        LocalDate startDate = LocalDate.now().minusYears(maxAge);
        return personRepository.findByAgeRange(startDate, endDate);
    }

    // Các method hỗ trợ Family
    // Lấy thành viên theo gia đình
    public List<Person> getPersonsByFamily(Family family) {
        return personRepository.findByFamily(family);
    }

    // Lấy thành viên không thuộc gia đình nào
    public List<Person> getPersonsWithoutFamily() {
        return personRepository.findByFamilyIsNull();
    }

    // Lấy thành viên theo ID gia đình
    public List<Person> getPersonsByFamilyId(Long familyId) {
        return personRepository.findByFamilyId(familyId);
    }

    // Đếm số thành viên trong gia đình
    public long getPersonCountByFamily(Family family) {
        return personRepository.countByFamily(family);
    }

    // Lấy con trai lớn tuổi nhất trong gia đình
    public Person getOldestMaleInFamily(Family family) {
        List<Person> oldestMales = personRepository.findOldestMaleInFamily(family);
        return oldestMales.isEmpty() ? null : oldestMales.get(0);
    }

    // Cập nhật gia đình cho thành viên
    public Person updatePersonFamily(Long personId, Family family) {
        Optional<Person> personOptional = personRepository.findById(personId);
        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            person.setFamily(family);
            return personRepository.save(person);
        }
        return null;
    }

    // Xóa gia đình khỏi thành viên
    public Person removePersonFromFamily(Long personId) {
        Optional<Person> personOptional = personRepository.findById(personId);
        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            person.setFamily(null);
            return personRepository.save(person);
        }
        return null;
    }
}