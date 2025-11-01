package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.PersonManagement.Person;
import com.example.servingwebcontent.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FamilyTreeService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    /**
     * Lớp nội để biểu diễn một node trong cây gia phả
     */
    public static class TreeNode {
        private Long id;
        private String name;
        private String gender;
        private String dob;
        private String dod;
        private Boolean isAlive;
        private String address;
        private String phone;
        private List<TreeNode> children;
        private TreeNode spouse;
        private Integer generation;

        public TreeNode() {
            this.children = new ArrayList<>();
        }

        public TreeNode(Person person) {
            this();
            this.id = person.getId();
            this.name = person.getName();
            this.gender = person.getGender().getDisplayName();
            this.dob = person.getDob() != null ? person.getDob().toString() : null;
            this.dod = person.getDod() != null ? person.getDod().toString() : null;
            this.isAlive = person.isAlive();
            this.address = person.getAddress();
            this.phone = person.getPhone();
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getGender() { return gender; }
        public void setGender(String gender) { this.gender = gender; }

        public String getDob() { return dob; }
        public void setDob(String dob) { this.dob = dob; }

        public String getDod() { return dod; }
        public void setDod(String dod) { this.dod = dod; }

        public Boolean getIsAlive() { return isAlive; }
        public void setIsAlive(Boolean isAlive) { this.isAlive = isAlive; }

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public List<TreeNode> getChildren() { return children; }
        public void setChildren(List<TreeNode> children) { this.children = children; }

        public TreeNode getSpouse() { return spouse; }
        public void setSpouse(TreeNode spouse) { this.spouse = spouse; }

        public Integer getGeneration() { return generation; }
        public void setGeneration(Integer generation) { this.generation = generation; }

        public void addChild(TreeNode child) {
            if (child != null) {
                this.children.add(child);
            }
        }
    }

    /**
     * Tạo cây gia phả cho một người cụ thể
     * @param personId ID của người để bắt đầu cây
     * @return TreeNode đại diện cho cây
     */
    public TreeNode buildFamilyTree(Long personId) {
        Optional<Person> personOptional = personRepository.findById(personId);
        if (personOptional.isEmpty()) {
            return null;
        }

        Person person = personOptional.get();
        TreeNode root = new TreeNode(person);
        
        // Thêm vợ/chồng
        if (person.getSpouse() != null) {
            TreeNode spouseNode = new TreeNode(person.getSpouse());
            root.setSpouse(spouseNode);
        }

        // Tạo map để lưu các node đã xử lý (tránh duplicate)
        Map<Long, TreeNode> processedNodes = new HashMap<>();
        processedNodes.put(root.getId(), root);

        // Xây dựng cây hướng lên (tổ tiên)
        buildAncestorTree(root, processedNodes);

        // Xây dựng cây hướng xuống (con cháu)
        buildDescendantTree(root, processedNodes);

        // Tính thế hệ cho mỗi node
        calculateGenerations(root);

        return root;
    }

    /**
     * Xây dựng phần cây tổ tiên (cha mẹ, ông bà...)
     */
    private void buildAncestorTree(TreeNode node, Map<Long, TreeNode> processedNodes) {
        // Lấy thông tin về bố mẹ từ node hiện tại
        // Node sẽ được tạo từ Person đã có thông tin father và mother
        
        Optional<Person> personOptional = personRepository.findById(node.getId());
        if (personOptional.isEmpty()) {
            return;
        }

        Person person = personOptional.get();
        Person father = person.getFather();
        Person mother = person.getMother();

        // Thêm bố
        if (father != null && !processedNodes.containsKey(father.getId())) {
            TreeNode fatherNode = new TreeNode(father);
            if (father.getSpouse() != null) {
                TreeNode fatherSpouse = new TreeNode(father.getSpouse());
                fatherNode.setSpouse(fatherSpouse);
            }
            node.addChild(fatherNode);
            processedNodes.put(fatherNode.getId(), fatherNode);
            
            // Đệ quy để tìm ông bà
            buildAncestorTree(fatherNode, processedNodes);
        }

        // Thêm mẹ
        if (mother != null && !processedNodes.containsKey(mother.getId())) {
            TreeNode motherNode = new TreeNode(mother);
            if (mother.getSpouse() != null) {
                TreeNode motherSpouse = new TreeNode(mother.getSpouse());
                motherNode.setSpouse(motherSpouse);
            }
            node.addChild(motherNode);
            processedNodes.put(motherNode.getId(), motherNode);
            
            // Đệ quy để tìm ông bà
            buildAncestorTree(motherNode, processedNodes);
        }
    }

    /**
     * Xây dựng phần cây con cháu
     */
    private void buildDescendantTree(TreeNode node, Map<Long, TreeNode> processedNodes) {
        Optional<Person> personOptional = personRepository.findById(node.getId());
        if (personOptional.isEmpty()) {
            return;
        }

        Person person = personOptional.get();
        
        // Tìm tất cả các con
        List<Person> children = personService.getChildren(person);
        
        for (Person child : children) {
            if (!processedNodes.containsKey(child.getId())) {
                TreeNode childNode = new TreeNode(child);
                
                // Thêm vợ/chồng của con
                if (child.getSpouse() != null) {
                    TreeNode childSpouse = new TreeNode(child.getSpouse());
                    childNode.setSpouse(childSpouse);
                    processedNodes.put(childSpouse.getId(), childSpouse);
                }
                
                node.addChild(childNode);
                processedNodes.put(childNode.getId(), childNode);
                
                // Đệ quy để tìm cháu
                buildDescendantTree(childNode, processedNodes);
            }
        }
    }

    /**
     * Tính số thế hệ cho mỗi node
     * Thế hệ 0 là người được chọn làm root
     */
    private void calculateGenerations(TreeNode node) {
        calculateGenerations(node, 0);
    }

    private void calculateGenerations(TreeNode node, int level) {
        node.setGeneration(level);
        
        for (TreeNode child : node.getChildren()) {
            calculateGenerations(child, level + 1);
        }
    }

    /**
     * Lấy tất cả các node trong cây
     */
    public List<TreeNode> getAllNodes(TreeNode root) {
        List<TreeNode> nodes = new ArrayList<>();
        if (root != null) {
            collectNodes(root, nodes);
        }
        return nodes;
    }

    private void collectNodes(TreeNode node, List<TreeNode> nodes) {
        nodes.add(node);
        
        for (TreeNode child : node.getChildren()) {
            collectNodes(child, nodes);
        }
    }

    /**
     * Tìm node theo ID trong cây
     */
    public TreeNode findNodeById(TreeNode root, Long id) {
        if (root == null) {
            return null;
        }
        
        if (root.getId().equals(id)) {
            return root;
        }
        
        for (TreeNode child : root.getChildren()) {
            TreeNode found = findNodeById(child, id);
            if (found != null) {
                return found;
            }
        }
        
        return null;
    }

    /**
     * Tính số người trong cây
     */
    public int countPeople(TreeNode root) {
        return getAllNodes(root).size();
    }

    /**
     * Lấy số thế hệ trong cây
     */
    public int getMaxGeneration(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        int maxGen = root.getGeneration() != null ? root.getGeneration() : 0;
        
        for (TreeNode child : root.getChildren()) {
            int childGen = getMaxGeneration(child);
            if (childGen > maxGen) {
                maxGen = childGen;
            }
        }
        
        return maxGen;
    }

    /**
     * Lấy tất cả người trong cùng một thế hệ
     */
    public List<TreeNode> getPeopleInGeneration(TreeNode root, int generation) {
        List<TreeNode> result = new ArrayList<>();
        getAllNodes(root).stream()
            .filter(node -> node.getGeneration() != null && node.getGeneration().equals(generation))
            .forEach(result::add);
        return result;
    }

    /**
     * Tạo cây gia phả đơn giản (chỉ hiển thị con cháu)
     */
    public TreeNode buildDescendantOnlyTree(Long personId) {
        Optional<Person> personOptional = personRepository.findById(personId);
        if (personOptional.isEmpty()) {
            return null;
        }

        Person person = personOptional.get();
        TreeNode root = new TreeNode(person);
        
        // Thêm vợ/chồng
        if (person.getSpouse() != null) {
            TreeNode spouseNode = new TreeNode(person.getSpouse());
            root.setSpouse(spouseNode);
        }

        Map<Long, TreeNode> processedNodes = new HashMap<>();
        processedNodes.put(root.getId(), root);
        
        buildDescendantTree(root, processedNodes);
        calculateGenerations(root);
        
        return root;
    }

    /**
     * Tạo cây gia phả ngược (chỉ hiển thị tổ tiên)
     */
    public TreeNode buildAncestorOnlyTree(Long personId) {
        Optional<Person> personOptional = personRepository.findById(personId);
        if (personOptional.isEmpty()) {
            return null;
        }

        Person person = personOptional.get();
        TreeNode root = new TreeNode(person);
        
        // Thêm vợ/chồng
        if (person.getSpouse() != null) {
            TreeNode spouseNode = new TreeNode(person.getSpouse());
            root.setSpouse(spouseNode);
        }

        Map<Long, TreeNode> processedNodes = new HashMap<>();
        processedNodes.put(root.getId(), root);
        
        buildAncestorTree(root, processedNodes);
        calculateGenerations(root);
        
        return root;
    }

    /**
     * Lấy danh sách tất cả người dùng có thể làm root
     */
    public List<Person> getAvailableRoots() {
        return personRepository.findAll();
    }
}
