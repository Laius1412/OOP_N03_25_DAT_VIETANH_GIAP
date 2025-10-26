package com.example.servingwebcontent.controller.PersonControllers;

import com.example.servingwebcontent.Model.Role;
import com.example.servingwebcontent.Model.User;
import com.example.servingwebcontent.Model.PersonManagement.Family;
import com.example.servingwebcontent.Model.PersonManagement.Person;
import com.example.servingwebcontent.service.FamilyService;
import com.example.servingwebcontent.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/families")
public class FamilyController {

    @Autowired
    private FamilyService familyService;

    @Autowired
    private PersonService personService;

    // Trang danh sách gia đình
    @GetMapping
    public String familiesList(Model model, HttpSession session,
                              @RequestParam(required = false) String search,
                              @RequestParam(required = false) String sort) {
        
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        List<Family> families;
        
        // Tìm kiếm
        if (search != null && !search.trim().isEmpty()) {
            families = familyService.searchFamilies(search.trim());
        } else {
            families = familyService.getAllFamilies();
        }

        // Sắp xếp
        if ("members_desc".equals(sort)) {
            families = familyService.getFamiliesWithMostMembers();
        } else if ("members_asc".equals(sort)) {
            families = familyService.getFamiliesWithLeastMembers();
        }

        // Thống kê
        long totalFamilies = familyService.getTotalFamilies();
        long familiesWithMembers = familyService.getFamiliesWithMembersCount();
        long emptyFamilies = familyService.getEmptyFamiliesCount();

        model.addAttribute("families", families);
        model.addAttribute("totalFamilies", totalFamilies);
        model.addAttribute("familiesWithMembers", familiesWithMembers);
        model.addAttribute("emptyFamilies", emptyFamilies);
        model.addAttribute("user", currentUser);
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);

        return "families/list";
    }

    // Trang thêm gia đình (chỉ Admin và Member Manager)
    @GetMapping("/add")
    public String addFamilyForm(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        // Kiểm tra quyền
        if (currentUser.getRole() != Role.ADMIN && currentUser.getRole() != Role.MEMBER_MANAGER) {
            return "redirect:/families";
        }

        model.addAttribute("family", new Family());
        model.addAttribute("user", currentUser);

        return "families/add";
    }

    // Xử lý thêm gia đình
    @PostMapping("/add")
    public String addFamily(@RequestParam String familyId,
                           @RequestParam String nameFamily,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        // Kiểm tra quyền
        if (currentUser.getRole() != Role.ADMIN && currentUser.getRole() != Role.MEMBER_MANAGER) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền thêm gia đình!");
            return "redirect:/families";
        }

        try {
            familyService.createFamily(familyId, nameFamily);
            redirectAttributes.addFlashAttribute("success", "Thêm gia đình thành công!");
            return "redirect:/families";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm gia đình: " + e.getMessage());
            return "redirect:/families/add";
        }
    }

    // Trang chi tiết gia đình
    @GetMapping("/{id}")
    public String familyDetail(@PathVariable Long id, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        Optional<Family> familyOptional = familyService.getFamilyById(id);
        if (!familyOptional.isPresent()) {
            return "redirect:/families";
        }

        Family family = familyOptional.get();
        List<Person> members = familyService.getFamilyMembers(id);
        List<Person> availablePersons = personService.getAllPersons();

        model.addAttribute("family", family);
        model.addAttribute("members", members);
        model.addAttribute("availablePersons", availablePersons);
        model.addAttribute("user", currentUser);

        return "families/detail";
    }

    // Trang chỉnh sửa gia đình (chỉ Admin và Member Manager)
    @GetMapping("/edit/{id}")
    public String editFamilyForm(@PathVariable Long id, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        // Kiểm tra quyền
        if (currentUser.getRole() != Role.ADMIN && currentUser.getRole() != Role.MEMBER_MANAGER) {
            return "redirect:/families";
        }

        Optional<Family> familyOptional = familyService.getFamilyById(id);
        if (!familyOptional.isPresent()) {
            return "redirect:/families";
        }

        model.addAttribute("family", familyOptional.get());
        model.addAttribute("user", currentUser);

        return "families/edit";
    }

    // Xử lý cập nhật gia đình
    @PostMapping("/edit/{id}")
    public String updateFamily(@PathVariable Long id,
                              @RequestParam String familyId,
                              @RequestParam String nameFamily,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        // Kiểm tra quyền
        if (currentUser.getRole() != Role.ADMIN && currentUser.getRole() != Role.MEMBER_MANAGER) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền chỉnh sửa gia đình!");
            return "redirect:/families";
        }

        try {
            Family updatedFamily = familyService.updateFamily(id, familyId, nameFamily);
            if (updatedFamily != null) {
                redirectAttributes.addFlashAttribute("success", "Cập nhật gia đình thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy gia đình!");
            }
            return "redirect:/families";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật gia đình: " + e.getMessage());
            return "redirect:/families/edit/" + id;
        }
    }

    // Xóa gia đình (chỉ Admin và Member Manager)
    @GetMapping("/delete/{id}")
    public String deleteFamily(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        // Kiểm tra quyền
        if (currentUser.getRole() != Role.ADMIN && currentUser.getRole() != Role.MEMBER_MANAGER) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền xóa gia đình!");
            return "redirect:/families";
        }

        try {
            if (familyService.canDeleteFamily(id)) {
                familyService.deleteFamily(id);
                redirectAttributes.addFlashAttribute("success", "Xóa gia đình thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không thể xóa gia đình có thành viên!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa gia đình: " + e.getMessage());
        }

        return "redirect:/families";
    }

    // Thêm thành viên vào gia đình
    @PostMapping("/{familyId}/add-member")
    public String addMemberToFamily(@PathVariable Long familyId,
                                   @RequestParam Long personId,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        // Kiểm tra quyền
        if (currentUser.getRole() != Role.ADMIN && currentUser.getRole() != Role.MEMBER_MANAGER) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền thêm thành viên!");
            return "redirect:/families/" + familyId;
        }

        try {
            boolean success = familyService.addMemberToFamily(familyId, personId);
            if (success) {
                redirectAttributes.addFlashAttribute("success", "Thêm thành viên thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không thể thêm thành viên!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm thành viên: " + e.getMessage());
        }

        return "redirect:/families/" + familyId;
    }

    // Xóa thành viên khỏi gia đình
    @GetMapping("/{familyId}/remove-member/{personId}")
    public String removeMemberFromFamily(@PathVariable Long familyId,
                                        @PathVariable Long personId,
                                        HttpSession session,
                                        RedirectAttributes redirectAttributes) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        // Kiểm tra quyền
        if (currentUser.getRole() != Role.ADMIN && currentUser.getRole() != Role.MEMBER_MANAGER) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền xóa thành viên!");
            return "redirect:/families/" + familyId;
        }

        try {
            boolean success = familyService.removeMemberFromFamily(familyId, personId);
            if (success) {
                redirectAttributes.addFlashAttribute("success", "Xóa thành viên thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không thể xóa thành viên!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa thành viên: " + e.getMessage());
        }

        return "redirect:/families/" + familyId;
    }

    // Tìm kiếm gia đình theo ID
    @GetMapping("/search")
    public String searchFamily(@RequestParam String familyId, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        Optional<Family> familyOptional = familyService.getFamilyByFamilyId(familyId);
        if (familyOptional.isPresent()) {
            return "redirect:/families/" + familyOptional.get().getId();
        } else {
            model.addAttribute("error", "Không tìm thấy gia đình với ID: " + familyId);
            model.addAttribute("user", currentUser);
            return "families/search";
        }
    }

    // API endpoint để lấy danh sách gia đình dưới dạng JSON
    @GetMapping("/api")
    @ResponseBody
    public List<Family> getAllFamilies() {
        return familyService.getAllFamilies();
    }

    // API endpoint để lấy thông tin gia đình theo ID
    @GetMapping("/api/{id}")
    @ResponseBody
    public Family getFamilyById(@PathVariable Long id) {
        return familyService.getFamilyById(id).orElse(null);
    }

    // API endpoint để tìm kiếm gia đình
    @GetMapping("/api/search")
    @ResponseBody
    public List<Family> searchFamilies(@RequestParam String keyword) {
        return familyService.searchFamilies(keyword);
    }
}
