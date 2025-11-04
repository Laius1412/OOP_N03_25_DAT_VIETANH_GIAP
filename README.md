# OOP_Project

- Tên dự án: Xây dựng ứng dụng quản lý gia tộc

# 1. Giới thiệu chung

## 1.1. Members (Thành viên dự án):

- Cao Mậu Thành Đạt - 22010338
- Nguyễn Trần Việt Anh - 22010341
- Võ Quang Giáp - 22010343

## 1.2. Yêu cầu chính:

- Giao diện <b>Java Spring Boot<b>.
- Mô tả: Hệ thống quản lý gia tộc bao gồm các nhóm chức năng chính như quản lý thành viên, cây gia phả, quản lý tài chính, quản lý sự kiện,... trong dòng họ.
- Đối tượng sử dụng: Hội đồng quản lý gia tộc (tộc cán).

## 1.3. Mô tả cụ thể:

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
-Long id
-String name
-Gender gender
-LocalDate dob
-LocalDate dod
-String address
-Person father
-Person mother
-String phone
-Person spouse
-Family family
-LocalDateTime createdAt
-LocalDateTime updatedAt
```

### 2.1.2. Methods (Phương thức)

```
+Constrcutors()
+Getters()
+Setters()
+isAlive() : boolean
+getAge() : int
+preUpdate()
+toString()
```

## 2.2. Gender (Giới tính) <<enumeration>>

### 2.2.1. Attribute (Thuộc tính)

```
MALE
FEMALE
OTHER
-String displayName
```

### 2.2.2. Methods (Phương thức)

```
+Constructor(String displayName)
+getDisplayName() String
+fromCode(String) Gender
```

## 2.3. User (Người dùng)

### 2.3.1. Attribute (Thuộc tính)

```
-Long id
-String username
-String password
-Role role
-String name
-String email
-String phone
-LocalDateTime createdAt
```

### 2.3.2. Methods (Phương thức)

```
+Constrcutors()
+Getters()
+Setters()
+toString()
```

## 2.4. Role (Vai trò) <<enumeration>>

### 2.4.1. Attribute (Thuộc tính)

```
ADMIN
MEMBER_MANAGER
EVENT_MANAGER
FINANCE_MANAGER
USER
-String code
-String displayName
-String description
```

### 2.4.2. Methods (Phương thức)

```
+Constrcutor(String code, String displayName, String description)
+getCode() String
+getDisplayName() String
+getDescription() String
+fromCode(String) Role
+hasPermission(String) boolean
```

## 2.5. Family (Gia đình)

### 2.5.1. Attribute (Thuộc tính)

```
-Long id
-String familyId
-String nameFamily
-LocalDateTime createdAt
-LocalDateTime updatedAt
-List<Person> familyMembers
```
### 2.5.2. Methods (Phương thức)

```
+Constrcutors()
+Getters()
+Setters()
+addFamilyMember(Person)
+removeFamilyMember(Person)
+getMemberCount() int
+getOldestMaleMember() Person
+updateFamilyName()
+preUpdate()
+toString()
```

## 2.6. Event (Sự kiện)

### 2.6.1. Attribute (Thuộc tính)

```
-Long id
-String title
-String description
-String location
-LocalDateTime startTime
-LocalDateTime endTime
-Integer maxParticipants
-Integer currentParticipants
-Long createdById
-LocalDateTime createdAt
-LocalDateTime updatedAt
-EventStatus status
-EventRecurrenceType recurrence
-EventCategory category
-List<EventParticipant> participants
```

### 2.6.2. Methods (Phương thức)

```
+Constrcutors()
+Getters()
+Setters()
+isUpcoming() : boolean
+isOngoing() : boolean
+getDurationInHours() : long
```

## 2.7. EventCategory (Thể loại sự kiện)

### 2.7.1. Attribute (Thuộc tính)

```
-Long id
-String name
-String description
```

### 2.7.2. Methods (Phương thức)

```
+Constrcutors()
+Getters()
+Setters()
```

## 2.8. EventParticipant (Người tham gia sự kiện)

### 2.8.1. Attribute (Thuộc tính)

```
-Long id
-Event event
-Long userId
-ParticipantStatus status
-LocalDateTime registeredAt
```

### 2.8.2. Methods (Phương thức)

```
+Constrcutors()
+Getters()
+Setters()
```

## 2.9. EventRecurrenceType (Kiểu lặp lại sự kiện) <<enumeration>>

### 2.9.1. Attribute (Thuộc tính)

```
NONE
MONTHLY
YEARLY
```

### 2.9.2. Methods (Phương thức)

```
+values()
+valueOf(String)
```

## 2.10. EventStatus (Trạng thái sự kiện) <<enumeration>>

### 2.10.1. Attribute (Thuộc tính)

```
UPCOMING
ONGOING
COMPLETED
CANCELLED
```

### 2.10.2. Methods (Phương thức)

```
+values()
+valueOf(String)
```

## 2.11. ParticipantStatus (Trạng thái người tham gia) <<enumeration>>

### 2.11.1. Attribute (Thuộc tính)

```
REGISTERED
CHECKED_IN
CANCELLED
```

### 2.11.2. Methods (Phương thức)

```
+values()
+valueOf(String)
```

## 2.12. ReceiveManagement (Quản lý thu)

### 2.12.1. Attribute (Thuộc tính)

```
- String id (mã khoản thu).
- String name (tên khoản thu).
- float money (số tiền thu).
- String type (hạng mục thu).
- String description (mô tả).
- Date date (ngày thu tiền),
- List <ReceiveManagement> finishedmembers (danh sách thành viên đóng tiền).
```

### 2.12.2. Methods (Phương thức)

```
- getName()
- getMoney()
- getType()
- getdescription()
- getDate()
```
## 2.13. ExpenseManagement (Quản lý chi)

### 2.13.1. Attribute (Thuộc tính)

```
- String id (mã khoản chi).
- String name (tên khoản chi).
- float money (số tiền chi).
- String type (hạng mục chi).
- String description (mô tả).
- Date date (ngày chi tiền),
```

### 2.13.2. Methods (Phương thức)

```
- getName()
- getMoney()
- getType()
- getdescription()
- getDate()
```
## 2.14. FinancialManagement (Quản lý tài chính)

### 2.14.1. Attribute (Thuộc tính)

```
- incomes: List<ReceiveManagement>,
- expenses: List<ExpenseManagement>,

```

### 2.14.2. Methods (Phương thức)

```
addIncome(receive: ReceiveManagement),
addExpense(expense: ExpenseManagement),
getTotalReceive(),
getTotalExpense(),
getBalance(),
getCategoryReport(),
```

## 2.15. AbstractFinanceEntity (Thực thể tài chính trừu tượng)

### 2.15.1. Attribute (Thuộc tính)

```
-String notes
```

### 2.15.2. Methods (Phương thức)

```
+Getters()
+Setters()
```

## 2.16. FinanceCategory (Danh mục tài chính)

### 2.16.1. Attribute (Thuộc tính)

```
-Long id
-String name
-String description
-TransactionType type
-Boolean isActive
-LocalDateTime createdAt
-LocalDateTime updatedAt
-List<Transaction> transactions
```

### 2.16.2. Methods (Phương thức)

```
+Constrcutors()
+Getters()
+Setters()
+toString()
```

## 2.17. FinancialReport (Báo cáo tài chính)

### 2.17.1. Attribute (Thuộc tính)

```
-Long id
-String reportName
-ReportType reportType
-LocalDate startDate
-LocalDate endDate
-BigDecimal totalIncome
-BigDecimal totalExpense
-BigDecimal netBalance
-Integer transactionCount
-Long generatedById
-LocalDateTime generatedAt
-String reportData
-String notes (inherited from AbstractFinanceEntity)
```

### 2.17.2. Methods (Phương thức)

```
+Constrcutors()
+Getters()
+Setters()
+isProfit() : boolean
+isLoss() : boolean
+isBreakEven() : boolean
+getProfitMargin() : BigDecimal
+getExpenseRatio() : BigDecimal
+getDaysInPeriod() : long
+getAverageDailyIncome() : BigDecimal
+getAverageDailyExpense() : BigDecimal
+toString()
```

## 2.18. PaymentMethod (Phương thức thanh toán) <<enumeration>>

### 2.18.1. Attribute (Thuộc tính)

```
CASH
BANK_TRANSFER
CREDIT_CARD
CHECK
OTHER
-String code
-String displayName
-String description
```

### 2.18.2. Methods (Phương thức)

```
+Constructor(String code, String displayName, String description)
+getCode() String
+getDisplayName() String
+getDescription() String
+fromCode(String) PaymentMethod
```

## 2.19. ReportType (Loại báo cáo) <<enumeration>>

### 2.19.1. Attribute (Thuộc tính)

```
MONTHLY
QUARTERLY
YEARLY
CUSTOM
WEEKLY
DAILY
-String code
-String displayName
-String description
```

### 2.19.2. Methods (Phương thức)

```
+Constructor(String code, String displayName, String description)
+getCode() String
+getDisplayName() String
+getDescription() String
+fromCode(String) ReportType
```

## 2.20. Transaction (Giao dịch)

### 2.20.1. Attribute (Thuộc tính)

```
-Long id
-String title
-String description
-BigDecimal amount
-TransactionType transactionType
-FinanceCategory category
-Long createdById
-LocalDate transactionDate
-PaymentMethod paymentMethod
-String receiptNumber
-String contributorName
-String contributorPhone
-String contributorRelationship
-Person contributor
-LocalDateTime createdAt
-LocalDateTime updatedAt
-String notes (inherited from AbstractFinanceEntity)
```

### 2.20.2. Methods (Phương thức)

```
+Constrcutors()
+Getters()
+Setters()
+isIncome() : boolean
+isExpense() : boolean
+getSignedAmount() : BigDecimal
+toString()
```

## 2.21. TransactionType (Loại giao dịch) <<enumeration>>

### 2.21.1. Attribute (Thuộc tính)

```
INCOME
EXPENSE
-String code
-String displayName
-String description
```

### 2.21.2. Methods (Phương thức)

```
+Constructor(String code, String displayName, String description)
+getCode() String
+getDisplayName() String
+getDescription() String
+fromCode(String) TransactionType
```

# 3. Sơ đồ khối

## 3.1 UML Class Diagram

<img width="2022" height="1664" alt="OOP (1)" src="https://github.com/user-attachments/assets/bfd590c7-dc4b-4646-b3f1-34d3f99bd139" />

## 3.2 UML Sequence Diagram
### 3.2.1 Sơ đồ thuật toán chức năng đăng kí, đăng nhập và đăng xuất.

<img width="2947" height="3186" alt="đăng kí đăng nhập , đăng xuất" src="https://github.com/user-attachments/assets/6fe0337c-3b9b-49d1-8ea6-446527475943" />

### 3.2.2. Sơ đồ thuật toán CRUD của đối tương user.

- Read

<img width="2884" height="1134" alt="danh sách" src="https://github.com/user-attachments/assets/5c4f6289-97ae-4c3b-9935-efe94f923a26" />

- Create

<img width="2920" height="1330" alt="them user" src="https://github.com/user-attachments/assets/bb7ca677-cf5c-4f23-9d89-821b55a9347a" />


- Update

<img width="2432" height="1618" alt="sửa user" src="https://github.com/user-attachments/assets/671468fc-dca8-4658-8570-9cb862d6212f" />


- Delete

<img width="2530" height="1326" alt="xóa user" src="https://github.com/user-attachments/assets/1125593a-945f-4c6f-8a58-572b7f270e4a" />


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



