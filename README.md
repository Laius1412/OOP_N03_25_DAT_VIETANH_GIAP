# OOP_Project

- Tên dự án: Hệ thống quản lý gia tộc
- Mô tả: Hệ thống quản lý gia tộc bao gồm các nhóm chức năng chính như quản lý thành viên, cây gia phả, quản lý thu chi, quản lý sự kiện,... trong dòng họ.
- Đối tượng sử dụng: Hội đồng quản lý gia tộc (tộc cán).

# 1. Members (Thành viên dự án)

- Cao Mậu Thành Đạt - 22010338
- Nguyễn Trần Vanh Thúy - 22010341
- Võ Quang Giáp - 22010343

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

## 2.2. User (Người dùng)

### 2.2.1. Attribute (Thuộc tính)

```
- String username (tài khoản)
- String password (mật khẩu)
- String role (chức vụ)
```

### 2.2.2. Methods (Phương thức)

## 2.3. Family (Gia đình)

### 2.3.1. Attribute (Thuộc tính)

```
- String id (mã gia đình)
- String nameFamily (tên của gia đình, lấy tên con trai lớn tuổi nhất hiện tại trong gia đình)
- 
```
