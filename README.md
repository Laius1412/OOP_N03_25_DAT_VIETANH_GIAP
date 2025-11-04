# OOP_Project

- Tên dự án: Xây dựng hệ thống quản lý gia phả

# Mục lục
- [1. Giới thiệu chung](#1-giới-thiệu-chung)
  - [1.1. Members (Thành viên dự án):](#11-members-thành-viên-dự-án)
  - [1.2. Yêu cầu chính:](#12-yêu-cầu-chính)
  - [1.3. Mô tả cụ thể:](#13-mô-tả-cụ-thể)
- [2. Objects (Đối tượng)](#2-objects-đối-tượng)
  - [2.1. Person (Thành viên)](#21-person-thành-viên)
    - [2.1.1. Attribute (Thuộc tính)](#211-attribute-thuộc-tính)
    - [2.1.2. Methods (Phương thức)](#212-methods-phương-thức)
  - [2.2. Gender (Giới tính) <<enumeration>>](#22-gender-giới-tính-enumeration)
    - [2.2.1. Attribute (Thuộc tính)](#221-attribute-thuộc-tính)
    - [2.2.2. Methods (Phương thức)](#222-methods-phương-thức)
  - [2.3. User (Người dùng)](#23-user-người-dùng)
    - [2.3.1. Attribute (Thuộc tính)](#231-attribute-thuộc-tính)
    - [2.3.2. Methods (Phương thức)](#232-methods-phương-thức)
  - [2.4. Role (Vai trò) <<enumeration>>](#24-role-vai-trò-enumeration)
    - [2.4.1. Attribute (Thuộc tính)](#241-attribute-thuộc-tính)
    - [2.4.2. Methods (Phương thức)](#242-methods-phương-thức)
  - [2.5. Family (Gia đình)](#25-family-gia-đình)
    - [2.5.1. Attribute (Thuộc tính)](#251-attribute-thuộc-tính)
    - [2.5.2. Methods (Phương thức)](#252-methods-phương-thức)
  - [2.6. Event (Sự kiện)](#26-event-sự-kiện)
    - [2.6.1. Attribute (Thuộc tính)](#261-attribute-thuộc-tính)
    - [2.6.2. Methods (Phương thức)](#262-methods-phương-thức)
  - [2.7. EventCategory (Thể loại sự kiện)](#27-eventcategory-thể-loại-sự-kiện)
    - [2.7.1. Attribute (Thuộc tính)](#271-attribute-thuộc-tính)
    - [2.7.2. Methods (Phương thức)](#272-methods-phương-thức)
  - [2.8. EventParticipant (Người tham gia sự kiện)](#28-eventparticipant-người-tham-gia-sự-kiện)
    - [2.8.1. Attribute (Thuộc tính)](#281-attribute-thuộc-tính)
    - [2.8.2. Methods (Phương thức)](#282-methods-phương-thức)
  - [2.9. EventRecurrenceType (Kiểu lặp lại sự kiện) <<enumeration>>](#29-eventrecurrencetype-kiểu-lặp-lại-sự-kiện-enumeration)
    - [2.9.1. Attribute (Thuộc tính)](#291-attribute-thuộc-tính)
    - [2.9.2. Methods (Phương thức)](#292-methods-phương-thức)
  - [2.10. EventStatus (Trạng thái sự kiện) <<enumeration>>](#210-eventstatus-trạng-thái-sự-kiện-enumeration)
    - [2.10.1. Attribute (Thuộc tính)](#2101-attribute-thuộc-tính)
    - [2.10.2. Methods (Phương thức)](#2102-methods-phương-thức)
  - [2.11. ParticipantStatus (Trạng thái người tham gia) <<enumeration>>](#211-participantstatus-trạng-thái-người-tham-gia-enumeration)
    - [2.11.1. Attribute (Thuộc tính)](#2111-attribute-thuộc-tính)
    - [2.11.2. Methods (Phương thức)](#2112-methods-phương-thức)
  - [2.12. ReceiveManagement (Quản lý thu)](#212-receivemanagement-quản-lý-thu)
    - [2.12.1. Attribute (Thuộc tính)](#2121-attribute-thuộc-tính)
    - [2.12.2. Methods (Phương thức)](#2122-methods-phương-thức)
  - [2.13. ExpenseManagement (Quản lý chi)](#213-expensEmanagement-quản-lý-chi)
    - [2.13.1. Attribute (Thuộc tính)](#2131-attribute-thuộc-tính)
    - [2.13.2. Methods (Phương thức)](#2132-methods-phương-thức)
  - [2.14. FinancialManagement (Quản lý tài chính)](#214-financialmanagement-quản-lý-tài-chính)
    - [2.14.1. Attribute (Thuộc tính)](#2141-attribute-thuộc-tính)
    - [2.14.2. Methods (Phương thức)](#2142-methods-phương-thức)
  - [2.15. AbstractFinanceEntity (Thực thể tài chính trừu tượng)](#215-abstractfinanceentity-thực-thể-tài-chính-trừu-tượng)
    - [2.15.1. Attribute (Thuộc tính)](#2151-attribute-thuộc-tính)
    - [2.15.2. Methods (Phương thức)](#2152-methods-phương-thức)
  - [2.16. FinanceCategory (Danh mục tài chính)](#216-financecategory-danh-mục-tài-chính)
    - [2.16.1. Attribute (Thuộc tính)](#2161-attribute-thuộc-tính)
    - [2.16.2. Methods (Phương thức)](#2162-methods-phương-thức)
  - [2.17. FinancialReport (Báo cáo tài chính)](#217-financialreport-báo-cáo-tài-chính)
    - [2.17.1. Attribute (Thuộc tính)](#2171-attribute-thuộc-tính)
    - [2.17.2. Methods (Phương thức)](#2172-methods-phương-thức)
  - [2.18. PaymentMethod (Phương thức thanh toán) <<enumeration>>](#218-paymentmethod-phương-thức-thanh-toán-enumeration)
    - [2.18.1. Attribute (Thuộc tính)](#2181-attribute-thuộc-tính)
    - [2.18.2. Methods (Phương thức)](#2182-methods-phương-thức)
  - [2.19. ReportType (Loại báo cáo) <<enumeration>>](#219-reporttype-loại-báo-cáo-enumeration)
    - [2.19.1. Attribute (Thuộc tính)](#2191-attribute-thuộc-tính)
    - [2.19.2. Methods (Phương thức)](#2192-methods-phương-thức)
  - [2.20. Transaction (Giao dịch)](#220-transaction-giao-dịch)
    - [2.20.1. Attribute (Thuộc tính)](#2201-attribute-thuộc-tính)
    - [2.20.2. Methods (Phương thức)](#2202-methods-phương-thức)
  - [2.21. TransactionType (Loại giao dịch) <<enumeration>>](#221-transactiontype-loại-giao-dịch-enumeration)
    - [2.21.1. Attribute (Thuộc tính)](#2211-attribute-thuộc-tính)
    - [2.21.2. Methods (Phương thức)](#2212-methods-phương-thức)
- [3. Sơ đồ khối](#3-sơ-đồ-khối)
  - [3.1 UML Class Diagram](#31-uml-class-diagram)
  - [3.2 UML Sequence Diagram](#32-uml-sequence-diagram)
    - [3.2.1 Sơ đồ thuật toán chức năng đăng kí, đăng nhập và đăng xuất.](#321-sơ-đồ-thuật-toán-chức-năng-đăng-kí-đăng-nhập-và-đăng-xuất)
    - [3.2.2. Sơ đồ thuật toán CRUD của đối tương user.](#322-sơ-đồ-thuật-toán-crud-của-đối-tương-user)
    - [3.2.3 Sơ đồ thuật toán CRUD của đối tượng Person](#323-sơ-đồ-thuật-toán-crud-của-đối-tượng-person)
    - [3.2.4 Sơ đồ thuật toán CRUD của đối tượng Finance.](#324-sơ-đồ-thuật-toán-crud-của-đối-tượng-finance)
    - [3.2.5 Sơ đồ thuật toán CRUD của đối tượng Event.](#325-sơ-đồ-thuật-toán-crud-của-đối-tượng-event)
    - [3.2.6 Sơ đồ thuật toán các chức năng của người dùng](#326-sơ-đồ-thuật-toán-các-chức-năng-của-người-dùng)
- [4. Giao diện chính](#4-giao-diện-chính)
  - [4.1. Đăng nhập](#41-đăng-nhập)
  - [4.2. Trang chủ](#42-trang-chủ)
  - [4.3. Quản lý thành viên](#43-quản-lý-thành-viên)
  - [4.4. Quản lý gia đình](#44-quản-lý-gia-đình)
  - [4.5. Quản lý sự kiện](#45-quản-lý-sự-kiện)
  - [4.6. Quản lý tài chính](#46-quản-lý-tài-chính)
  - [4.7. Quản lý người dùng](#47-quản-lý-người-dùng)
  - [4.8. Xem cây gia phả](#48-xem-cây-gia-phả)
- [5. Triển khai](#5-triển-khai)

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

- Chức năng hiển thị danh sách User

<img width="2884" height="1134" alt="danh sách" src="https://github.com/user-attachments/assets/d58c4e61-9d3c-4e47-a4dc-d5357916bea5" />

- Chức năng thêm User

<img width="2920" height="1330" alt="them user" src="https://github.com/user-attachments/assets/515990d3-7f71-4248-b73c-cc0c31ce3cb7" />

- Chức năng chỉnh sửa User

<img width="2432" height="1618" alt="sửa user" src="https://github.com/user-attachments/assets/94646a50-e374-4967-a288-451f4117085f" />

- Chức năng xóa User

<img width="2530" height="1326" alt="xóa user" src="https://github.com/user-attachments/assets/c46fb8e9-65a8-4db1-88ba-26dfc09b9bfe" />


### 3.2.3 Sơ đồ thuật toán CRUD của đối tượng Person

- Chức năng hiển thị danh sách và tìm kiếm thành viên

<img width="3038" height="2006" alt="hiển thị danh sách và tìm kiếm person" src="https://github.com/user-attachments/assets/ac3fce8a-27d6-472b-bb7f-3dc1736b0bb0" />

- Chức năng thêm, sửa, xóa thành viên 

<img width="3642" height="4158" alt="thêm sửa xóa person" src="https://github.com/user-attachments/assets/60458c18-1435-4f30-a5e1-54efb696cf83" />

- Chức năng tạo cây gia phả và xem chi tiết thành viên

<img width="2920" height="2094" alt="cây gia phả và thông tin chi tiết " src="https://github.com/user-attachments/assets/c2b16818-0dd6-42a5-8096-2dddd24574dc" />

### 3.2.4 Sơ đồ thuật toán CRUD của đối tượng Finance.

- Chức năng thêm danh mục 
  
<img width="2626" height="1402" alt="thêm danh mục" src="https://github.com/user-attachments/assets/e0108c8c-d598-44b3-bf03-d41489500253" />

- Chức năng sửa và xóa danh mục 

<img width="3124" height="2418" alt="sửa- xóa danh mục" src="https://github.com/user-attachments/assets/f7fc0f66-bda7-4a30-89fe-edc8cfac61a5" />

- Chức năng thêm, sửa và xóa giao dịch
  
<img width="3068" height="3286" alt="them sua xoa , giao dich" src="https://github.com/user-attachments/assets/0b0cec43-fc32-4898-919a-3fd00be9bbfc" />

- Chức năng tạo báo cáo
  
<img width="2914" height="2126" alt="tạo bao cao" src="https://github.com/user-attachments/assets/3cf1350d-db37-4527-b23a-c92fd1f264b8" />

### 3.2.5 Sơ đồ thuật toán CRUD của đối tượng Event.

- Chức năng hiển thị danh sách sự kiện và tìm kiếm

<img width="3028" height="1870" alt="danh sách sự kiện và tìm kiếm event " src="https://github.com/user-attachments/assets/5c2542d0-3f9f-45e6-9ecd-107c6fa736b1" />

- Chức năng thêm, sửa, xóa sự kiện

<img width="2461" height="3310" alt="thêm sửa xóa event" src="https://github.com/user-attachments/assets/9cba1fb1-b29e-495e-9c20-8db59db65a93" />

### 3.2.6 Sơ đồ thuật toán các chức năng của người dùng

<img width="4090" height="1554" alt="người dùng" src="https://github.com/user-attachments/assets/718f4b12-1814-4eb9-b07d-b3a575d24e74" />

# 4. Giao diện chính

## 4.1. Đăng nhập

<img width="1861" height="874" alt="image" src="https://github.com/user-attachments/assets/c27bb646-3320-42ad-a534-3f0d9c8ff156" />

## 4.2. Trang chủ

<img width="1838" height="877" alt="image" src="https://github.com/user-attachments/assets/efa52773-ebd8-444a-875c-95429141d971" />

## 4.3. Quản lý thành viên

<img width="1860" height="875" alt="image" src="https://github.com/user-attachments/assets/47dbdd65-1a79-4912-be56-b44d838250a0" />

## 4.4. Quản lý gia đình

<img width="1864" height="879" alt="image" src="https://github.com/user-attachments/assets/7ebf2266-ee30-4b55-a6d6-09e2e3e0f974" />

## 4.5. Quản lý sự kiện

<img width="1849" height="886" alt="image" src="https://github.com/user-attachments/assets/b3f966f4-3996-412e-9d50-b5d442e9aa9f" />

## 4.6. Quản lý tài chính

<img width="1862" height="880" alt="image" src="https://github.com/user-attachments/assets/f3af37fe-c47a-451c-b976-1071dff76a60" />

## 4.7. Quản lý người dùng

<img width="1860" height="876" alt="image" src="https://github.com/user-attachments/assets/7762b8ff-3251-47bf-925c-a19369d54076" />

## 4.8. Xem cây gia phả

<img width="1856" height="881" alt="image" src="https://github.com/user-attachments/assets/958a4902-5cf8-4eb7-8a41-13af0aeaf683" />

# 5. Triển khai

- Link Github Source Code: https://github.com/Laius1412/OOP_N03_25_DAT_VIETANH_GIAP
- Link Youtube demo ứng dụng:
- Link trang web chính thức: https://oop-n03-25-dat-vietanh-giap.onrender.com

* Lưu ý: Liên kết trang web có thể mất vài phút để khởi động do deploy free.
* Tài khoản admin (full chức năng): admin
* Mật khẩu admin: admin123
