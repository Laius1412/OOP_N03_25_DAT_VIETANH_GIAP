# OOP_Project

- Tên dự án: Xây dựng ứng dụng quản lý gia tộc

# 1. Members (Thành viên dự án)

- Cao Mậu Thành Đạt - 22010338
- Nguyễn Trần Việt Anh - 22010341
- Võ Quang Giáp - 22010343

# Yêu cầu chính

- Giao diện <b>Java Spring Boot<b>.
- Mô tả: Hệ thống quản lý gia tộc bao gồm các nhóm chức năng chính như quản lý thành viên, cây gia phả, quản lý tài chính, quản lý sự kiện,... trong dòng họ.
- Đối tượng sử dụng: Hội đồng quản lý gia tộc (tộc cán).

# Mô tả cụ thể

- Quản lý tài khoản:
  + Đăng nhập.
  + Phân quyền cho mỗi tài khoản theo từng chức năng.
- Quản lý thành viên:
  + Thêm, sửa, xóa thành viên dòng họ.
  + Liệt kê danh sách thành viên, tìm kiếm và hiển thị thông tin thành viên.
- Quản lý gia đình:
  + Thêm, sửa, xóa gia đình.
  + Thêm, sửa, xóa thành viên trong gia đình.
  + Liệt kê danh sách các hộ gia đình trong dòng họ.
  + Hiển thị danh sách thành viên trong từng gia đình.
  + Tìm kiếm gia đình theo id.
- Cây gia phả:
  + Hiển thị cây gia phả của dòng họ.
  + Có thể xuất file ảnh (jpg, png).
- Quản lý tài chính:
  + Thêm, sửa, xóa các khoản thu/chi.
  + Liệt kê danh sách thu/chi.
  + Có chức năng quản lý các khoản đóng góp (bắt buộc hoặc ủng hộ).
  + Thống kế tài chính (tổng số dư, số tiền cho từng quỹ,...).
- Quản lý sự kiện:
  + Thêm, sửa, xóa sự kiện của dòng họ.
  + Hiển thị lịch sự kiện trong tuần/tháng/năm.
  + Có chức năng tìm kiếm sự kiện theo ngày, từ khóa,...
- Có chức năng gán thành viên cho gia đình, gán thành viên cho cây gia phả, gán thành viên cho đóng góp/thu/chi.
- Dữ liệu được lưu trữ dưới dạng nhị phân:
  + Cần tạo các lớp liên quan đến "tài khoản", "thành viên", "cây gia phả", "gia đình", "tài chính", "quản lý thu", "quản lý chi", "sự kiện" để đọc, ghi xuống 1 hay nhiều file.
- Khi làm việc với dữ liệu trong bộ nhớ, dữ liệu cần được lưu trữ dưới dạng các Collection.
- Sử dụng MySQL làm cơ sở dữ liệu chính.

# 2. Objects (Đối tượng)

## 2.1. Person (Thành viên)

### 2.1.1. Attribute (Thuộc tính)

```
- String id (mã thành viên)
- String name (họ và tên)
- String gender (giới tính)
- Date dob (ngày sinh)
- Date dod (ngày mất, có thể null)
- String address (địa chỉ)
- Person father (bố)
- Person mother (mẹ)
- String phone (số điện thoại)
- Person spouse (quan hệ vợ chồng, có thể null)
```

### 2.1.2. Methods (Phương thức)

```
- Person().
- set(), get() cho từng thuộc tính.
- int getAge().
- setParents().
- setSpouse().
- editMembereditMember().
- removeMember().
- display().
```

## 2.2. User (Người dùng)

### 2.2.1. Attribute (Thuộc tính)

```
- String username (tài khoản)
- String password (mật khẩu)
- String role (chức vụ)
```

### 2.2.2. Methods (Phương thức)

```
- User().
- set(), get() cho từng thuộc tính.
- login().
- logout().
- changePassword().
- resetPassword().
```

## 2.3. Family (Gia đình)

### 2.3.1. Attribute (Thuộc tính)

```
- String id (mã gia đình)
- String nameFamily (tên của gia đình, lấy tên con trai lớn tuổi nhất hiện tại trong gia đình)
- List <Person> familyMembers (danh sách thành viên trong gia đình)
```
### 2.3.2. Methods (Phương thức)

```
- Family().
- getMember().
- addMembers().
- removeMember().
- display().
- removeFamily().
```

## 2.4. FamilyTree (Cây gia phả)

### 2.4.1. Attribute (Thuộc tính)

```
- Person root (thành viên đời thứ nhất - tổ tiên)
- List <Person> members (danh sách thành viên)
```

### 2.4.2. Methods (Phương thức)

```
- FamilyTree().
- addNode().
- display().
```

## 2.5. ReceiveManagement (Quản lý thu)

### 2.5.1. Attribute (Thuộc tính)

```
- String id (mã khoản thu).
- String name (tên khoản thu).
- float money (số tiền thu).
- String type (hạng mục thu).
- String description (mô tả).
- Date date (ngày thu tiền),
- List <ReceiveManagement> finishedmembers (danh sách thành viên đóng tiền).
```

### 2.5.2. Methods (Phương thức)

```
- getName()
- getMoney()
- getType()
- getdescription()
- getDate()
```
## 2.6. ExpenseManagement (Quản lý chi)

### 2.6.1. Attribute (Thuộc tính)

```
- String id (mã khoản chi).
- String name (tên khoản chi).
- float money (số tiền chi).
- String type (hạng mục chi).
- String description (mô tả).
- Date date (ngày chi tiền),
```

### 2.6.2. Methods (Phương thức)

```
- getName()
- getMoney()
- getType()
- getdescription()
- getDate()
```
## 2.7. FinancialManagement (Quản lý tài chính)

### 2.7.1. Attribute (Thuộc tính)

```
- incomes: List<ReceiveManagement>,
- expenses: List<ExpenseManagement>,

```

### 2.7.2. Methods (Phương thức)

```
addIncome(receive: ReceiveManagement),
addExpense(expense: ExpenseManagement),
getTotalReceive(),
getTotalExpense(),
getBalance(),
getCategoryReport(),
```

# 3. Sơ đồ khối

## 3.1 UML Class Diagram

<img width="1241" height="1284" alt="OPP2 drawio" src="https://github.com/user-attachments/assets/f000eac5-b27d-414d-891f-535525d108c2" />

## 3.2 UML Sequence Diagram
### 3.2.1 Sơ đồ thuật toán CRUD của đối tượng User

- Create

<img width="1077" height="680" alt="createUser" src="https://github.com/user-attachments/assets/dc7863b9-22c0-46d2-ba61-5eabe009f52b" />


- Read

<img width="840" height="518" alt="readUser" src="https://github.com/user-attachments/assets/b608274b-efe1-4bd6-b598-260e662431e9" />

- Update

<img width="904" height="576" alt="updateUser" src="https://github.com/user-attachments/assets/45161c96-d7a0-4a28-aca5-307f748c7949" />

- Delete

<img width="827" height="576" alt="deleteUser" src="https://github.com/user-attachments/assets/e8d7717a-8fd4-413d-8051-1df1d0028088" />

### 3.2.2 Sơ đồ thuật toán CRUD của đối tượng Person

- Create

<img width="3730" height="3840" alt="themPerson" src="https://github.com/user-attachments/assets/550ab705-7e96-4794-a931-69972fb610c7" />

- Read

<img width="3840" height="3574" alt="XemPerson" src="https://github.com/user-attachments/assets/af45512f-d536-441e-9c2c-45ff4ea728ab" />

- Update

<img width="2875" height="3840" alt="Chinhsua" src="https://github.com/user-attachments/assets/3f33bb46-7f80-4f67-8c84-8489623b38ba" />

- Delete

<img width="2712" height="3840" alt="xóa" src="https://github.com/user-attachments/assets/e575d163-0abc-4b35-8702-051d01ff764f" />

### 3.2.3 Sơ đồ thuật toán CRUD của đối tượng Receive

- Create
  
<img width="3840" height="3193" alt="themtk" src="https://github.com/user-attachments/assets/e87fcbd3-3383-41a9-a7ba-da6f9944ff0f" />

- Read

<img width="3840" height="1960" alt="xem danh sách" src="https://github.com/user-attachments/assets/cca3238a-567d-467a-9daa-470f9eb1f9a3" />
  
- Update
  
<img width="3840" height="2753" alt="sửatk" src="https://github.com/user-attachments/assets/91e5053d-fc34-44d2-a874-5df5a0a8eadc" />
  
- Delete
  
<img width="3840" height="3019" alt="xoatk" src="https://github.com/user-attachments/assets/bcce7f8c-1f04-43ff-addb-1b9c293c3809" />
  
- Search
  
<img width="3840" height="2150" alt="tìm kiếm" src="https://github.com/user-attachments/assets/a8100e59-8908-479e-8e82-2b2bc7aaa5d4" />



