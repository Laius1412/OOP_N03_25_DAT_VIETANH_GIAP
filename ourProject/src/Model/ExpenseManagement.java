package Model;

import java.util.Date;

public class ExpenseManagement {
    private String id; // mã khoản chi
    private String name; // tên khoản chi
    private float money; // số tiền chi
    private String type; // hạng mục chi
    private String description; // mô tả
    private Date date; // ngày chi tiền

    public ExpenseManagement(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
