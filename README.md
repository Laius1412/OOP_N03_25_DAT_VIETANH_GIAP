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
- List <ReceiveManagement> finishedmembers (danh sách thành viên đóng tiền).
```

### 2.5.2. Methods (Phương thức)

```
- 
