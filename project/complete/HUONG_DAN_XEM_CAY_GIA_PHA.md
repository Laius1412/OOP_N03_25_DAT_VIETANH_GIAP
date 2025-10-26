# 🌳 HƯỚNG DẪN XEM CÂY GIA PHẢ

## 📌 CÁCH XEM CÂY GIA PHẢ

### **Bước 1: Khởi động ứng dụng**

Mở terminal/PowerShell trong thư mục `project/complete` và chạy:

```bash
# Windows
gradlew.bat bootRun

# Linux/Mac
./gradlew bootRun
```

Đợi ứng dụng khởi động (thường mất 30-60 giây lần đầu).

### **Bước 2: Mở trình duyệt**

Truy cập: **http://localhost:8080**

### **Bước 3: Đăng nhập**

- Username: `admin` 
- Password: `admin`

(Nếu chưa có tài khoản, đăng ký mới)

### **Bước 4: Vào trang Gia đình**

1. Click vào menu **"Gia đình"** (icon nhà)
2. Bạn sẽ thấy danh sách các gia đình

### **Bước 5: Chọn gia đình**

- Click vào tên gia đình (ví dụ: "Dòng họ Nguyễn")
- Hoặc search theo mã gia đình: `NGUYEN_FAMILY_001`

### **Bước 6: Xem Cây Gia phả**

Trong trang **Chi tiết Gia đình**, bạn sẽ thấy:

```
┌─────────────────────────────────────┐
│  [← Quay lại]  [Xem cây gia phả 🌳]  │  ← Click nút này!
└─────────────────────────────────────┘
```

Click nút **"Xem cây gia phả"** (màu xanh, có icon sitemap)

---

## 🎨 GIAO DIỆN CÂY GIA PHẢ

### **Cấu trúc hiển thị:**

```
╔═══════════════════════════════════════╗
║           CÂY GIA PHẢ                 ║
║      Dòng họ Nguyễn                   ║
╠═══════════════════════════════════════╣
║                                       ║
║  ┌──────┐      ┌──────┐              ║
║  │ ♂ Cội │ ────│♀ Thị │  Thế hệ 1   ║
║  │(1850) │      │Cội   │              ║
║  └──────┘      └──────┘              ║
║                                       ║
║  ┌──────┐  ┌──────┐  ┌──────┐       ║
║  │♂ Đức │─│♀ Đức │  │♂ Minh│─│♀Minh│  ║
║  └──────┘  └──────┘  └──────┘ └────┘  ║
║          Thế hệ 2                     ║
║                                       ║
║  [Các thế hệ con tiếp theo...]        ║
║                                       ║
╚═══════════════════════════════════════╝
```

### **Màu sắc:**
- 💙 **Xanh dương** = Nam giới (♂)
- 💗 **Hồng** = Nữ giới (♀)
- 🟢 **Badge xanh** = Còn sống
- 🔴 **Badge đỏ** = Đã mất

---

## 🚀 TRUY CẬP NHANH

### **Option 1: Qua Menu**
```
Home → Gia đình → [Chọn gia đình] → Xem cây gia phả
```

### **Option 2: Qua URL trực tiếp**
```
http://localhost:8080/families/1/tree
```

*(ID `1` là ID của gia đình Nguyễn)*

---

## 📊 DỮ LIỆU MẪU

Ứng dụng tự động tạo dữ liệu mẫu cho **Dòng họ Nguyễn** với:

- **Thế hệ 0 (Gốc):** Nguyễn Văn Cội & Nguyễn Thị Cội
- **Thế hệ 1:** 4 con (Đức, Minh, Lan, Hùng)
- **Thế hệ 2:** 12 cháu
- **Thế hệ 3:** 24 chắt  
- **Thế hệ 4:** 48 chút
- **Thế hệ 5:** 24 chít

**Tổng:** ~113 thành viên

---

## ✨ TÍNH NĂNG

### **Hiển thị:**
- ✅ Phân chia theo thế hệ
- ✅ Nhóm vợ chồng
- ✅ Màu theo giới tính
- ✅ Hiển thị năm sinh/mất
- ✅ Badge trạng thái

### **Tương tác:**
- 🖱️ **Click vào một người** → Xem chi tiết
- 🔄 **Reload** → Cập nhật dữ liệu mới
- 📱 **Responsive** → Xem trên mobile

---

## 🛠️ TROUBLESHOOTING

### **Không thấy dữ liệu?**
- Kiểm tra database đã kết nối chưa
- Xem log console: `Bắt đầu tạo dữ liệu mẫu cho dòng họ Nguyễn...`

### **Lỗi 404 Not Found?**
- Đảm bảo ứng dụng đang chạy
- Kiểm tra port: `localhost:8080`

### **Cây không hiển thị đúng?**
- F12 → Console để xem lỗi JavaScript
- Clear cache trình duyệt (Ctrl+F5)

---

## 📝 LƯU Ý

1. **Lần đầu chạy:** Dữ liệu được load tự động
2. **Các lần sau:** Chỉ cần login và xem
3. **Tùy chỉnh:** Có thể thêm/sửa/xóa thành viên qua menu "Thành viên"

---

## 🎯 DEMO VIDEO

*Sẽ cập nhật sau...*

---

Chúc bạn khám phá cây gia phả thành công! 🎉

