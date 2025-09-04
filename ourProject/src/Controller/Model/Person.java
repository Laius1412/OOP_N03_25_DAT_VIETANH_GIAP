import java.time.LocalDate;

public class Person {
    private String name;
    private String gender;
    private LocalDate dob; // Date of Birth
    private LocalDate dod; // Date of Death
    private int phone;
    private String address;
    private Person father;
    private Person mother;
    private Person spouse;

    public Person() {
    }

    public Person(String name, String gender, LocalDate dob, LocalDate dod, int phone, String address) {
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.dod = dod;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public LocalDate getDod() {
        return dod;
    }

    public void setDod(LocalDate dod) {
        this.dod = dod;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Person getFather() {
        return father;
    }

    public void setFather(Person father) {
        this.father = father;
    }

    public Person getMother() {
        return mother;
    }

    public void setMother(Person mother) {
        this.mother = mother;
    }

    public Person getSpouse() {
        return spouse;
    }

    public void setSpouse(Person spouse) {
        this.spouse = spouse;
        if (spouse != null && spouse.getSpouse() != this) {
            spouse.setSpouse(this);
        }
    }

    public void setParents(Person father, Person mother) {
        this.father = father;
        this.mother = mother;
    }

    // Hiển thị thông tin
    public void display() {
        System.out.println("Tên: " + this.name);
        System.out.println("Giới tính: " + this.gender);
        System.out.println("Ngày sinh: " + this.dob);
        if (this.dod != null) {
            System.out.println("Ngày mất: " + this.dod);
        }
        System.out.println("Số điện thoại: " + this.phone);
        System.out.println("Địa chỉ: " + this.address);

        if (this.father != null) {
            System.out.println("Cha: " + this.father.getName());
        }
        if (this.mother != null) {
            System.out.println("Mẹ: " + this.mother.getName());
        }
        if (this.spouse != null) {
            System.out.println("Vợ/Chồng: " + this.spouse.getName());
        }
    }
}
