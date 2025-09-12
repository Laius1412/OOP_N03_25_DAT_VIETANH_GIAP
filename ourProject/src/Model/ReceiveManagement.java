package Model;

import java.util.Date;
import java.util.List;

public class ReceiveManagement {
    private String id; // mã khoản thu
    private String name; // tên khoản thu
    private float money; // số tiền thu
    private String type; // hạng mục thu
    private String description; // mô tả
    private Date date; // ngày thu tiền
    private List<ReceiveManagement> finishedmembers; // danh sách thành viên đóng tiền

    public ReceiveManagement(){}

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

    public List<ReceiveManagement> getFinishedmembers() {
        return finishedmembers;
    }

    public void setFinishedmembers(List<ReceiveManagement> finishedmembers) {
        this.finishedmembers = finishedmembers;
    }
}
