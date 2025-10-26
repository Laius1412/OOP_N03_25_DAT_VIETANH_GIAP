package com.example.servingwebcontent.controller.PersonControllers;

import com.example.servingwebcontent.Model.Role;
import com.example.servingwebcontent.Model.User;
import com.example.servingwebcontent.Model.PersonManagement.Gender;
import com.example.servingwebcontent.Model.PersonManagement.Person;
import com.example.servingwebcontent.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class PersonController {

    @Autowired
    private PersonService personService;

    // Trang danh sách thành viên
    @GetMapping("/members")
    public String membersList(Model model, HttpSession session,
                             @RequestParam(required = false) String search,
                             @RequestParam(required = false) String gender,
                             @RequestParam(required = false) String status) {
        
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        List<Person> persons;
        
        // Tìm kiếm
        if (search != null && !search.trim().isEmpty()) {
            persons = personService.searchByKeyword(search.trim());
        } else if (gender != null && !gender.isEmpty()) {
            Gender genderEnum = Gender.fromCode(gender);
            persons = personService.searchByGender(genderEnum);
        } else if ("alive".equals(status)) {
            persons = personService.getAlivePersons();
        } else if ("deceased".equals(status)) {
            persons = personService.getDeceasedPersons();
        } else {
            persons = personService.getAllPersons();
        }

        // Thống kê
        long totalPersons = personService.getTotalPersons();
        long maleCount = personService.getPersonsByGender(Gender.MALE);
        long femaleCount = personService.getPersonsByGender(Gender.FEMALE);
        long aliveCount = personService.getAlivePersonsCount();
        long deceasedCount = personService.getDeceasedPersonsCount();

        model.addAttribute("persons", persons);
        model.addAttribute("totalPersons", totalPersons);
        model.addAttribute("maleCount", maleCount);
        model.addAttribute("femaleCount", femaleCount);
        model.addAttribute("aliveCount", aliveCount);
        model.addAttribute("deceasedCount", deceasedCount);
        model.addAttribute("user", currentUser);
        model.addAttribute("search", search);
        model.addAttribute("gender", gender);
        model.addAttribute("status", status);

        return "persons/list";
    }

    // Trang thêm thành viên (chỉ Admin và Member Manager)
    @GetMapping("/members/add")
    public String addMemberForm(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        // Kiểm tra quyền
        if (currentUser.getRole() != Role.ADMIN && currentUser.getRole() != Role.MEMBER_MANAGER) {
            return "redirect:/members";
        }

        List<Person> allPersons = personService.getAllPersons();
        model.addAttribute("person", new Person());
        model.addAttribute("allPersons", allPersons);
        model.addAttribute("genders", Gender.values());
        model.addAttribute("user", currentUser);

        return "persons/add";
    }

    // Xử lý thêm thành viên
    @PostMapping("/members/add")
    public String addMember(@RequestParam String name,
                           @RequestParam String gender,
                           @RequestParam(required = false) String dob,
                           @RequestParam(required = false) String dod,
                           @RequestParam(required = false) String address,
                           @RequestParam(required = false) String phone,
                           @RequestParam(required = false) Long fatherId,
                           @RequestParam(required = false) Long motherId,
                           @RequestParam(required = false) Long spouseId,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        // Kiểm tra quyền
        if (currentUser.getRole() != Role.ADMIN && currentUser.getRole() != Role.MEMBER_MANAGER) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền thêm thành viên!");
            return "redirect:/members";
        }

        try {
            Person person = new Person();
            person.setName(name);
            person.setGender(Gender.fromCode(gender));
            
            if (dob != null && !dob.isEmpty()) {
                person.setDob(LocalDate.parse(dob));
            }
            
            if (dod != null && !dod.isEmpty()) {
                person.setDod(LocalDate.parse(dod));
            }
            
            person.setAddress(address);
            person.setPhone(phone);

            // Thiết lập quan hệ gia đình
            if (fatherId != null) {
                Optional<Person> father = personService.getPersonById(fatherId);
                father.ifPresent(person::setFather);
            }
            
            if (motherId != null) {
                Optional<Person> mother = personService.getPersonById(motherId);
                mother.ifPresent(person::setMother);
            }
            
            if (spouseId != null) {
                Optional<Person> spouse = personService.getPersonById(spouseId);
                spouse.ifPresent(person::setSpouse);
            }

            personService.savePerson(person);
            redirectAttributes.addFlashAttribute("success", "Thêm thành viên thành công!");
            return "redirect:/members";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm thành viên: " + e.getMessage());
            return "redirect:/members/add";
        }
    }

    // Trang chi tiết thành viên
    @GetMapping("/members/{id}")
    public String memberDetail(@PathVariable Long id, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        Optional<Person> personOptional = personService.getPersonById(id);
        if (!personOptional.isPresent()) {
            return "redirect:/members";
        }

        Person person = personOptional.get();
        List<Person> children = personService.getChildren(person);
        List<Person> spouses = personService.getSpouses(person);

        model.addAttribute("person", person);
        model.addAttribute("children", children);
        model.addAttribute("spouses", spouses);
        model.addAttribute("user", currentUser);

        return "persons/detail";
    }

    // Trang chỉnh sửa thành viên (chỉ Admin và Member Manager)
    @GetMapping("/members/edit/{id}")
    public String editMemberForm(@PathVariable Long id, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        // Kiểm tra quyền
        if (currentUser.getRole() != Role.ADMIN && currentUser.getRole() != Role.MEMBER_MANAGER) {
            return "redirect:/members";
        }

        Optional<Person> personOptional = personService.getPersonById(id);
        if (!personOptional.isPresent()) {
            return "redirect:/members";
        }

        List<Person> allPersons = personService.getAllPersons();
        model.addAttribute("person", personOptional.get());
        model.addAttribute("allPersons", allPersons);
        model.addAttribute("genders", Gender.values());
        model.addAttribute("user", currentUser);

        return "persons/edit";
    }

    // Xử lý cập nhật thành viên
    @PostMapping("/members/edit/{id}")
    public String updateMember(@PathVariable Long id,
                              @RequestParam String name,
                              @RequestParam String gender,
                              @RequestParam(required = false) String dob,
                              @RequestParam(required = false) String dod,
                              @RequestParam(required = false) String address,
                              @RequestParam(required = false) String phone,
                              @RequestParam(required = false) Long fatherId,
                              @RequestParam(required = false) Long motherId,
                              @RequestParam(required = false) Long spouseId,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        // Kiểm tra quyền
        if (currentUser.getRole() != Role.ADMIN && currentUser.getRole() != Role.MEMBER_MANAGER) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền chỉnh sửa thành viên!");
            return "redirect:/members";
        }

        try {
            Person father = null;
            Person mother = null;
            Person spouse = null;

            if (fatherId != null) {
                Optional<Person> fatherOptional = personService.getPersonById(fatherId);
                father = fatherOptional.orElse(null);
            }
            
            if (motherId != null) {
                Optional<Person> motherOptional = personService.getPersonById(motherId);
                mother = motherOptional.orElse(null);
            }
            
            if (spouseId != null) {
                Optional<Person> spouseOptional = personService.getPersonById(spouseId);
                spouse = spouseOptional.orElse(null);
            }

            LocalDate dobDate = null;
            LocalDate dodDate = null;
            
            if (dob != null && !dob.isEmpty()) {
                dobDate = LocalDate.parse(dob);
            }
            
            if (dod != null && !dod.isEmpty()) {
                dodDate = LocalDate.parse(dod);
            }

            Person updatedPerson = personService.updatePerson(id, name, Gender.fromCode(gender), 
                                                           dobDate, dodDate, address, phone, 
                                                           father, mother, spouse);

            if (updatedPerson != null) {
                redirectAttributes.addFlashAttribute("success", "Cập nhật thành viên thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy thành viên!");
            }

            return "redirect:/members";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật thành viên: " + e.getMessage());
            return "redirect:/members/edit/" + id;
        }
    }

    // Xóa thành viên (chỉ Admin và Member Manager)
    @GetMapping("/members/delete/{id}")
    public String deleteMember(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        // Kiểm tra quyền
        if (currentUser.getRole() != Role.ADMIN && currentUser.getRole() != Role.MEMBER_MANAGER) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền xóa thành viên!");
            return "redirect:/members";
        }

        try {
            // Sử dụng phương thức xóa trực tiếp với exception handling
            personService.deletePerson(id);
            redirectAttributes.addFlashAttribute("success", "Xóa thành viên thành công!");
        } catch (IllegalStateException e) {
            // Lỗi do ràng buộc khóa ngoại (foreign key constraint)
            redirectAttributes.addFlashAttribute("error", "Không thể xóa thành viên! " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa thành viên: " + e.getMessage());
        }

        return "redirect:/members";
    }

    // API endpoint để lấy danh sách thành viên dưới dạng JSON
    @GetMapping("/api/members")
    @ResponseBody
    public List<Person> getAllMembers() {
        return personService.getAllPersons();
    }

    // API endpoint để lấy thông tin thành viên theo ID
    @GetMapping("/api/members/{id}")
    @ResponseBody
    public Person getMemberById(@PathVariable Long id) {
        return personService.getPersonById(id).orElse(null);
    }
}
