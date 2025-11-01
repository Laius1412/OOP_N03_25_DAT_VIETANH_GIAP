package com.example.servingwebcontent.controller.PersonControllers;

import com.example.servingwebcontent.model.User;
import com.example.servingwebcontent.model.PersonManagement.Person;
import com.example.servingwebcontent.service.FamilyTreeService;
import com.example.servingwebcontent.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/family-tree")
public class FamilyTreeController {

    @Autowired
    private FamilyTreeService familyTreeService;

    @Autowired
    private PersonService personService;

    /**
     * Trang chính hiển thị cây gia phả
     */
    @GetMapping
    public String familyTreePage(Model model, HttpSession session,
                                @RequestParam(required = false) Long rootId) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", currentUser);

        // Lấy danh sách tất cả người để làm root
        List<Person> allPersons = personService.getAllPersons();
        model.addAttribute("allPersons", allPersons);
        
        // Chuyển đổi Person thành Map để dễ serialize thành JSON
        List<java.util.Map<String, Object>> personsJson = allPersons.stream()
            .map(person -> {
                java.util.Map<String, Object> personMap = new java.util.HashMap<>();
                personMap.put("id", person.getId());
                personMap.put("name", person.getName());
                personMap.put("gender", person.getGender() != null ? person.getGender().name() : "");
                personMap.put("genderDisplay", person.getGender() != null ? person.getGender().getDisplayName() : "");
                return personMap;
            })
            .collect(java.util.stream.Collectors.toList());
        model.addAttribute("allPersonsJson", personsJson);

        // Nếu có rootId, lấy thông tin người đó
        if (rootId != null) {
            Optional<Person> rootPerson = personService.getPersonById(rootId);
            if (rootPerson.isPresent()) {
                model.addAttribute("rootPerson", rootPerson.get());
                model.addAttribute("selectedRootId", rootId);
            }
        }

        return "family-tree/tree";
    }

    /**
     * Trang hiển thị cây gia phả với root cụ thể
     */
    @GetMapping("/view/{rootId}")
    public String viewFamilyTree(@PathVariable Long rootId, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        Optional<Person> rootPerson = personService.getPersonById(rootId);
        if (rootPerson.isEmpty()) {
            return "redirect:/family-tree";
        }

        model.addAttribute("user", currentUser);
        model.addAttribute("rootPerson", rootPerson.get());
        model.addAttribute("selectedRootId", rootId);

        return "family-tree/tree";
    }

    /**
     * API để lấy dữ liệu cây gia phả dưới dạng JSON
     */
    @GetMapping("/api/data/{rootId}")
    @ResponseBody
    public FamilyTreeService.TreeNode getFamilyTreeData(@PathVariable Long rootId,
                                                       @RequestParam(required = false) String mode) {
        if ("descendant".equals(mode)) {
            return familyTreeService.buildDescendantOnlyTree(rootId);
        } else if ("ancestor".equals(mode)) {
            return familyTreeService.buildAncestorOnlyTree(rootId);
        } else {
            return familyTreeService.buildFamilyTree(rootId);
        }
    }

    /**
     * API để lấy thống kê cây gia phả
     */
    @GetMapping("/api/stats/{rootId}")
    @ResponseBody
    public FamilyTreeStats getFamilyTreeStats(@PathVariable Long rootId) {
        FamilyTreeService.TreeNode tree = familyTreeService.buildFamilyTree(rootId);
        
        if (tree == null) {
            return null;
        }

        FamilyTreeStats stats = new FamilyTreeStats();
        stats.setTotalPeople(familyTreeService.countPeople(tree));
        stats.setMaxGeneration(familyTreeService.getMaxGeneration(tree));

        return stats;
    }

    /**
     * Class để trả về thống kê
     */
    public static class FamilyTreeStats {
        private int totalPeople;
        private int maxGeneration;

        public int getTotalPeople() { return totalPeople; }
        public void setTotalPeople(int totalPeople) { this.totalPeople = totalPeople; }

        public int getMaxGeneration() { return maxGeneration; }
        public void setMaxGeneration(int maxGeneration) { this.maxGeneration = maxGeneration; }
    }
}

