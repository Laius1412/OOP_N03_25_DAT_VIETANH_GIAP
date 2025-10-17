# Database Connection - Aiven MySQL

## Tổng quan
Folder này chứa các class để quản lý kết nối với MySQL database trên Aiven.

## Files trong folder

### 1. DatabaseConnection.java
Class chính để quản lý kết nối database với các tính năng:
- Kết nối MySQL với SSL (bắt buộc cho Aiven)
- Quản lý connection pool
- Test kết nối
- Đóng kết nối an toàn

### 2. DatabaseConfig.java
Spring Boot configuration class để:
- Cấu hình DataSource
- Test kết nối khi khởi động ứng dụng
- Quản lý profiles

## Cách sử dụng

### 1. Cấu hình thông tin Aiven
Cập nhật các biến môi trường hoặc file `application.properties`:

```properties
# Aiven MySQL Configuration
AIVEN_MYSQL_HOST=your-aiven-mysql-host.aivencloud.com
AIVEN_MYSQL_PORT=12345
AIVEN_MYSQL_DATABASE=your_database_name
AIVEN_MYSQL_USERNAME=avnadmin
AIVEN_MYSQL_PASSWORD=your_aiven_password
```

### 2. Sử dụng trong code

```java
// Lấy kết nối mặc định
Connection conn = DatabaseConnection.getConnection();

// Lấy kết nối với thông tin tùy chỉnh
Connection conn = DatabaseConnection.getConnection(
    "host", "port", "database", "username", "password"
);

// Test kết nối
DatabaseConnection.testConnection();

// Kiểm tra kết nối có hoạt động
boolean isValid = DatabaseConnection.isConnectionValid();

// Đóng kết nối
DatabaseConnection.closeConnection();
```

### 3. Sử dụng với Spring Boot
```java
@Autowired
private DatabaseConnection databaseConnection;

@GetMapping("/test-db")
public String testDatabase() {
    try {
        Connection conn = databaseConnection.getConnection();
        return "Database connection successful!";
    } catch (SQLException e) {
        return "Database connection failed: " + e.getMessage();
    }
}
```

## Lưu ý quan trọng

1. **SSL Certificate**: Aiven yêu cầu SSL. Đảm bảo có CA certificate từ Aiven Console.

2. **Environment Variables**: Sử dụng biến môi trường để bảo mật thông tin kết nối.

3. **Connection Pool**: Spring Boot tự động quản lý connection pool thông qua HikariCP.

4. **Error Handling**: Luôn xử lý SQLException khi làm việc với database.

## Troubleshooting

### Lỗi SSL
```
SSL connection error: unable to find valid certification path
```
**Giải pháp**: Tải CA certificate từ Aiven Console và cấu hình đường dẫn.

### Lỗi kết nối
```
Connection refused
```
**Giải pháp**: Kiểm tra host, port và firewall settings.

### Lỗi authentication
```
Access denied for user
```
**Giải pháp**: Kiểm tra username/password trong Aiven Console.
