-- Dữ liệu mẫu cho dòng họ Nguyễn
-- Tạo gia đình Nguyễn với khoảng 100 thành viên

-- Tạo gia đình Nguyễn
INSERT INTO families (family_id, name_family, created_at, updated_at) 
VALUES ('NGUYEN_FAMILY_001', 'Dòng họ Nguyễn', NOW(), NOW());

-- Lấy ID của gia đình vừa tạo
SET @family_id = LAST_INSERT_ID();

-- Người gốc của dòng họ (Tổ tiên) - Nguyễn Văn Cội (sinh năm 1850)
INSERT INTO persons (name, gender, date_of_birth, date_of_death, address, phone, family_id, created_at, updated_at)
VALUES ('Nguyễn Văn Cội', 'MALE', '1850-01-15', '1920-03-20', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000001', @family_id, NOW(), NOW());

SET @root_person_id = LAST_INSERT_ID();

-- Vợ của tổ tiên
INSERT INTO persons (name, gender, date_of_birth, date_of_death, address, phone, family_id, created_at, updated_at)
VALUES ('Nguyễn Thị Cội', 'FEMALE', '1855-05-10', '1925-08-15', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000002', @family_id, NOW(), NOW());

SET @root_wife_id = LAST_INSERT_ID();

-- Cập nhật quan hệ vợ chồng
UPDATE persons SET spouse_id = @root_wife_id WHERE id = @root_person_id;
UPDATE persons SET spouse_id = @root_person_id WHERE id = @root_wife_id;

-- Thế hệ 1: Con của tổ tiên (4 con)
INSERT INTO persons (name, gender, date_of_birth, date_of_death, address, phone, father_id, mother_id, family_id, created_at, updated_at)
VALUES 
('Nguyễn Văn Đức', 'MALE', '1875-02-20', '1945-06-10', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000003', @root_person_id, @root_wife_id, @family_id, NOW(), NOW()),
('Nguyễn Văn Minh', 'MALE', '1878-08-15', '1950-12-05', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000004', @root_person_id, @root_wife_id, @family_id, NOW(), NOW()),
('Nguyễn Thị Lan', 'FEMALE', '1880-11-30', '1960-04-20', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000005', @root_person_id, @root_wife_id, @family_id, NOW(), NOW()),
('Nguyễn Văn Hùng', 'MALE', '1883-03-25', '1955-09-15', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000006', @root_person_id, @root_wife_id, @family_id, NOW(), NOW());

-- Lấy ID của các con thế hệ 1
SET @duc_id = @root_person_id + 1;
SET @minh_id = @root_person_id + 2;
SET @lan_id = @root_person_id + 3;
SET @hung_id = @root_person_id + 4;

-- Vợ của các con trai thế hệ 1
INSERT INTO persons (name, gender, date_of_birth, date_of_death, address, phone, family_id, created_at, updated_at)
VALUES 
('Nguyễn Thị Đức', 'FEMALE', '1880-04-12', '1950-07-25', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000007', @family_id, NOW(), NOW()),
('Nguyễn Thị Minh', 'FEMALE', '1885-09-18', '1955-11-30', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000008', @family_id, NOW(), NOW()),
('Nguyễn Thị Hùng', 'FEMALE', '1888-12-05', '1960-03-10', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000009', @family_id, NOW(), NOW());

-- Cập nhật quan hệ vợ chồng thế hệ 1
UPDATE persons SET spouse_id = @root_person_id + 5 WHERE id = @duc_id;
UPDATE persons SET spouse_id = @duc_id WHERE id = @root_person_id + 5;
UPDATE persons SET spouse_id = @root_person_id + 6 WHERE id = @minh_id;
UPDATE persons SET spouse_id = @minh_id WHERE id = @root_person_id + 6;
UPDATE persons SET spouse_id = @root_person_id + 7 WHERE id = @hung_id;
UPDATE persons SET spouse_id = @hung_id WHERE id = @root_person_id + 7;

-- Thế hệ 2: Cháu của tổ tiên (12 cháu)
INSERT INTO persons (name, gender, date_of_birth, date_of_death, address, phone, father_id, mother_id, family_id, created_at, updated_at)
VALUES 
-- Con của Nguyễn Văn Đức (3 con)
('Nguyễn Văn Thành', 'MALE', '1900-01-15', '1970-05-20', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000010', @duc_id, @root_person_id + 5, @family_id, NOW(), NOW()),
('Nguyễn Văn Bình', 'MALE', '1903-06-20', '1975-08-15', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000011', @duc_id, @root_person_id + 5, @family_id, NOW(), NOW()),
('Nguyễn Thị Hoa', 'FEMALE', '1905-09-10', '1980-12-05', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000012', @duc_id, @root_person_id + 5, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Minh (3 con)
('Nguyễn Văn Phúc', 'MALE', '1902-03-25', '1972-07-10', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000013', @minh_id, @root_person_id + 6, @family_id, NOW(), NOW()),
('Nguyễn Văn Lộc', 'MALE', '1906-11-15', '1978-04-20', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000014', @minh_id, @root_person_id + 6, @family_id, NOW(), NOW()),
('Nguyễn Thị Mai', 'FEMALE', '1908-07-30', '1985-01-15', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000015', @minh_id, @root_person_id + 6, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Lan (2 con - lấy họ chồng)
('Trần Văn Nam', 'MALE', '1901-05-12', '1970-09-25', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000016', NULL, @lan_id, @family_id, NOW(), NOW()),
('Trần Thị Nga', 'FEMALE', '1904-08-18', '1975-11-30', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000017', NULL, @lan_id, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Hùng (4 con)
('Nguyễn Văn Tài', 'MALE', '1907-02-08', '1977-06-12', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000018', @hung_id, @root_person_id + 7, @family_id, NOW(), NOW()),
('Nguyễn Văn Đức', 'MALE', '1909-10-22', '1979-03-18', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000019', @hung_id, @root_person_id + 7, @family_id, NOW(), NOW()),
('Nguyễn Thị Loan', 'FEMALE', '1911-12-05', '1982-08-25', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000020', @hung_id, @root_person_id + 7, @family_id, NOW(), NOW()),
('Nguyễn Văn Sơn', 'MALE', '1913-04-15', '1985-10-30', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000021', @hung_id, @root_person_id + 7, @family_id, NOW(), NOW());

-- Thế hệ 3: Chắt của tổ tiên (24 chắt)
INSERT INTO persons (name, gender, date_of_birth, date_of_death, address, phone, father_id, mother_id, family_id, created_at, updated_at)
VALUES 
-- Con của Nguyễn Văn Thành (3 con)
('Nguyễn Văn An', 'MALE', '1925-01-10', '1995-06-15', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000022', @root_person_id + 9, NULL, @family_id, NOW(), NOW()),
('Nguyễn Văn Bảo', 'MALE', '1927-05-20', '1997-09-25', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000023', @root_person_id + 9, NULL, @family_id, NOW(), NOW()),
('Nguyễn Thị Cúc', 'FEMALE', '1929-08-15', '2000-12-10', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000024', @root_person_id + 9, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Bình (2 con)
('Nguyễn Văn Cường', 'MALE', '1930-03-12', '2000-07-20', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000025', @root_person_id + 10, NULL, @family_id, NOW(), NOW()),
('Nguyễn Thị Dung', 'FEMALE', '1932-11-25', '2002-04-15', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000026', @root_person_id + 10, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Hoa (2 con)
('Nguyễn Văn Em', 'MALE', '1931-07-08', '2001-10-30', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000027', NULL, @root_person_id + 11, @family_id, NOW(), NOW()),
('Nguyễn Thị Phương', 'FEMALE', '1933-09-18', '2003-02-12', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000028', NULL, @root_person_id + 11, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Phúc (3 con)
('Nguyễn Văn Giang', 'MALE', '1928-02-14', '1998-05-28', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000029', @root_person_id + 12, NULL, @family_id, NOW(), NOW()),
('Nguyễn Văn Hải', 'MALE', '1930-06-30', '2000-08-15', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000030', @root_person_id + 12, NULL, @family_id, NOW(), NOW()),
('Nguyễn Thị Hương', 'FEMALE', '1932-10-12', '2002-01-20', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000031', @root_person_id + 12, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Lộc (2 con)
('Nguyễn Văn Ích', 'MALE', '1934-04-05', '2004-07-18', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000032', @root_person_id + 13, NULL, @family_id, NOW(), NOW()),
('Nguyễn Thị Kim', 'FEMALE', '1936-12-20', '2006-03-25', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000033', @root_person_id + 13, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Mai (2 con)
('Nguyễn Văn Lâm', 'MALE', '1935-08-10', '2005-11-22', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000034', NULL, @root_person_id + 14, @family_id, NOW(), NOW()),
('Nguyễn Thị Linh', 'FEMALE', '1937-01-25', '2007-04-08', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000035', NULL, @root_person_id + 14, @family_id, NOW(), NOW()),

-- Con của Trần Văn Nam (2 con)
('Trần Văn Mạnh', 'MALE', '1926-03-18', '1996-06-30', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000036', @root_person_id + 15, NULL, @family_id, NOW(), NOW()),
('Trần Thị Nga', 'FEMALE', '1928-07-22', '1998-09-15', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000037', @root_person_id + 15, NULL, @family_id, NOW(), NOW()),

-- Con của Trần Thị Nga (2 con)
('Trần Văn Oanh', 'MALE', '1929-11-05', '1999-02-18', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000038', NULL, @root_person_id + 16, @family_id, NOW(), NOW()),
('Trần Thị Phượng', 'FEMALE', '1931-05-12', '2001-08-25', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000039', NULL, @root_person_id + 16, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Tài (3 con)
('Nguyễn Văn Quang', 'MALE', '1933-09-08', '2003-12-20', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000040', @root_person_id + 17, NULL, @family_id, NOW(), NOW()),
('Nguyễn Văn Rồng', 'MALE', '1935-01-15', '2005-04-10', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000041', @root_person_id + 17, NULL, @family_id, NOW(), NOW()),
('Nguyễn Thị Sương', 'FEMALE', '1937-06-28', '2007-09-15', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000042', @root_person_id + 17, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Đức (2 con)
('Nguyễn Văn Tùng', 'MALE', '1934-10-12', '2004-01-25', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000043', @root_person_id + 18, NULL, @family_id, NOW(), NOW()),
('Nguyễn Thị Uyên', 'FEMALE', '1936-03-20', '2006-06-18', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000044', @root_person_id + 18, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Loan (2 con)
('Nguyễn Văn Vinh', 'MALE', '1938-07-15', '2008-10-30', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000045', NULL, @root_person_id + 19, @family_id, NOW(), NOW()),
('Nguyễn Thị Xuân', 'FEMALE', '1940-12-08', '2010-03-22', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000046', NULL, @root_person_id + 19, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Sơn (2 con)
('Nguyễn Văn Yên', 'MALE', '1939-05-25', '2009-08-12', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000047', @root_person_id + 20, NULL, @family_id, NOW(), NOW()),
('Nguyễn Thị Zin', 'FEMALE', '1941-08-30', '2011-11-15', 'Làng Cội, Huyện Cội, Tỉnh Cội', '0000000048', @root_person_id + 20, NULL, @family_id, NOW(), NOW());

-- Thế hệ 4: Chút của tổ tiên (48 chút) - Thế hệ hiện tại
INSERT INTO persons (name, gender, date_of_birth, date_of_death, address, phone, father_id, mother_id, family_id, created_at, updated_at)
VALUES 
-- Con của Nguyễn Văn An (2 con)
('Nguyễn Văn Anh', 'MALE', '1950-02-15', NULL, 'Hà Nội', '0900000001', @root_person_id + 21, NULL, @family_id, NOW(), NOW()),
('Nguyễn Thị Bích', 'FEMALE', '1952-06-20', NULL, 'Hà Nội', '0900000002', @root_person_id + 21, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Bảo (2 con)
('Nguyễn Văn Cường', 'MALE', '1951-04-10', NULL, 'TP.HCM', '0900000003', @root_person_id + 22, NULL, @family_id, NOW(), NOW()),
('Nguyễn Thị Dung', 'FEMALE', '1953-08-25', NULL, 'TP.HCM', '0900000004', @root_person_id + 22, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Cúc (2 con)
('Nguyễn Văn Em', 'MALE', '1954-01-12', NULL, 'Đà Nẵng', '0900000005', NULL, @root_person_id + 23, @family_id, NOW(), NOW()),
('Nguyễn Thị Phương', 'FEMALE', '1956-05-18', NULL, 'Đà Nẵng', '0900000006', NULL, @root_person_id + 23, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Cường (2 con)
('Nguyễn Văn Giang', 'MALE', '1955-03-08', NULL, 'Hải Phòng', '0900000007', @root_person_id + 24, NULL, @family_id, NOW(), NOW()),
('Nguyễn Thị Hương', 'FEMALE', '1957-07-14', NULL, 'Hải Phòng', '0900000008', @root_person_id + 24, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Dung (2 con)
('Nguyễn Văn Ích', 'MALE', '1958-09-22', NULL, 'Cần Thơ', '0900000009', NULL, @root_person_id + 25, @family_id, NOW(), NOW()),
('Nguyễn Thị Kim', 'FEMALE', '1960-11-30', NULL, 'Cần Thơ', '0900000010', NULL, @root_person_id + 25, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Em (2 con)
('Nguyễn Văn Lâm', 'MALE', '1959-12-05', NULL, 'Nha Trang', '0900000011', @root_person_id + 26, NULL, @family_id, NOW(), NOW()),
('Nguyễn Thị Linh', 'FEMALE', '1961-04-12', NULL, 'Nha Trang', '0900000012', @root_person_id + 26, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Phương (2 con)
('Nguyễn Văn Mạnh', 'MALE', '1962-06-18', NULL, 'Huế', '0900000013', NULL, @root_person_id + 27, @family_id, NOW(), NOW()),
('Nguyễn Thị Nga', 'FEMALE', '1964-08-25', NULL, 'Huế', '0900000014', NULL, @root_person_id + 27, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Giang (2 con)
('Nguyễn Văn Oanh', 'MALE', '1963-10-15', NULL, 'Vinh', '0900000015', @root_person_id + 28, NULL, @family_id, NOW(), NOW()),
('Nguyễn Thị Phượng', 'FEMALE', '1965-12-22', NULL, 'Vinh', '0900000016', @root_person_id + 28, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Hải (2 con)
('Nguyễn Văn Quang', 'MALE', '1964-02-28', NULL, 'Quy Nhon', '0900000017', @root_person_id + 29, NULL, @family_id, NOW(), NOW()),
('Nguyễn Thị Rồng', 'FEMALE', '1966-05-05', NULL, 'Quy Nhon', '0900000018', @root_person_id + 29, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Hương (2 con)
('Nguyễn Văn Sơn', 'MALE', '1967-07-12', NULL, 'Pleiku', '0900000019', NULL, @root_person_id + 30, @family_id, NOW(), NOW()),
('Nguyễn Thị Tùng', 'FEMALE', '1969-09-18', NULL, 'Pleiku', '0900000020', NULL, @root_person_id + 30, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Ích (2 con)
('Nguyễn Văn Uyên', 'MALE', '1968-11-25', NULL, 'Buôn Ma Thuột', '0900000021', @root_person_id + 31, NULL, @family_id, NOW(), NOW()),
('Nguyễn Thị Vinh', 'FEMALE', '1970-01-30', NULL, 'Buôn Ma Thuột', '0900000022', @root_person_id + 31, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Kim (2 con)
('Nguyễn Văn Xuân', 'MALE', '1971-03-08', NULL, 'Đà Lạt', '0900000023', NULL, @root_person_id + 32, @family_id, NOW(), NOW()),
('Nguyễn Thị Yên', 'FEMALE', '1973-05-15', NULL, 'Đà Lạt', '0900000024', NULL, @root_person_id + 32, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Lâm (2 con)
('Nguyễn Văn Zin', 'MALE', '1972-07-22', NULL, 'Long Xuyên', '0900000025', @root_person_id + 33, NULL, @family_id, NOW(), NOW()),
('Nguyễn Thị An', 'FEMALE', '1974-09-28', NULL, 'Long Xuyên', '0900000026', @root_person_id + 33, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Linh (2 con)
('Nguyễn Văn Bảo', 'MALE', '1975-11-05', NULL, 'Rạch Giá', '0900000027', NULL, @root_person_id + 34, @family_id, NOW(), NOW()),
('Nguyễn Thị Cúc', 'FEMALE', '1977-12-12', NULL, 'Rạch Giá', '0900000028', NULL, @root_person_id + 34, @family_id, NOW(), NOW()),

-- Con của Trần Văn Mạnh (2 con)
('Trần Văn Cường', 'MALE', '1976-02-18', NULL, 'Cà Mau', '0900000029', @root_person_id + 35, NULL, @family_id, NOW(), NOW()),
('Trần Thị Dung', 'FEMALE', '1978-04-25', NULL, 'Cà Mau', '0900000030', @root_person_id + 35, NULL, @family_id, NOW(), NOW()),

-- Con của Trần Thị Nga (2 con)
('Trần Văn Em', 'MALE', '1979-06-30', NULL, 'Sóc Trăng', '0900000031', NULL, @root_person_id + 36, @family_id, NOW(), NOW()),
('Trần Thị Phương', 'FEMALE', '1981-08-05', NULL, 'Sóc Trăng', '0900000032', NULL, @root_person_id + 36, @family_id, NOW(), NOW()),

-- Con của Trần Văn Oanh (2 con)
('Trần Văn Giang', 'MALE', '1980-10-12', NULL, 'Bạc Liêu', '0900000033', @root_person_id + 37, NULL, @family_id, NOW(), NOW()),
('Trần Thị Hương', 'FEMALE', '1982-12-18', NULL, 'Bạc Liêu', '0900000034', @root_person_id + 37, NULL, @family_id, NOW(), NOW()),

-- Con của Trần Thị Phượng (2 con)
('Trần Văn Ích', 'MALE', '1983-01-25', NULL, 'Cao Lãnh', '0900000035', NULL, @root_person_id + 38, @family_id, NOW(), NOW()),
('Trần Thị Kim', 'FEMALE', '1985-03-30', NULL, 'Cao Lãnh', '0900000036', NULL, @root_person_id + 38, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Quang (2 con)
('Nguyễn Văn Lâm', 'MALE', '1984-05-08', NULL, 'Mỹ Tho', '0900000037', @root_person_id + 39, NULL, @family_id, NOW(), NOW()),
('Nguyễn Thị Linh', 'FEMALE', '1986-07-15', NULL, 'Mỹ Tho', '0900000038', @root_person_id + 39, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Rồng (2 con)
('Nguyễn Văn Mạnh', 'MALE', '1987-09-22', NULL, 'Tân An', '0900000039', @root_person_id + 40, NULL, @family_id, NOW(), NOW()),
('Nguyễn Thị Nga', 'FEMALE', '1989-11-28', NULL, 'Tân An', '0900000040', @root_person_id + 40, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Sương (2 con)
('Nguyễn Văn Oanh', 'MALE', '1988-12-05', NULL, 'Bến Tre', '0900000041', NULL, @root_person_id + 41, @family_id, NOW(), NOW()),
('Nguyễn Thị Phượng', 'FEMALE', '1990-02-12', NULL, 'Bến Tre', '0900000042', NULL, @root_person_id + 41, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Tùng (2 con)
('Nguyễn Văn Quang', 'MALE', '1991-04-18', NULL, 'Vĩnh Long', '0900000043', @root_person_id + 42, NULL, @family_id, NOW(), NOW()),
('Nguyễn Thị Rồng', 'FEMALE', '1993-06-25', NULL, 'Vĩnh Long', '0900000044', @root_person_id + 42, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Uyên (2 con)
('Nguyễn Văn Sơn', 'MALE', '1992-08-30', NULL, 'Trà Vinh', '0900000045', NULL, @root_person_id + 43, @family_id, NOW(), NOW()),
('Nguyễn Thị Tùng', 'FEMALE', '1994-10-05', NULL, 'Trà Vinh', '0900000046', NULL, @root_person_id + 43, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Vinh (2 con)
('Nguyễn Văn Uyên', 'MALE', '1995-12-12', NULL, 'Kiên Giang', '0900000047', @root_person_id + 44, NULL, @family_id, NOW(), NOW()),
('Nguyễn Thị Vinh', 'FEMALE', '1997-02-18', NULL, 'Kiên Giang', '0900000048', @root_person_id + 44, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Xuân (2 con)
('Nguyễn Văn Xuân', 'MALE', '1996-04-25', NULL, 'An Giang', '0900000049', NULL, @root_person_id + 45, @family_id, NOW(), NOW()),
('Nguyễn Thị Yên', 'FEMALE', '1998-06-30', NULL, 'An Giang', '0900000050', NULL, @root_person_id + 45, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Yên (2 con)
('Nguyễn Văn Zin', 'MALE', '1999-08-08', NULL, 'Đồng Tháp', '0900000051', @root_person_id + 46, NULL, @family_id, NOW(), NOW()),
('Nguyễn Thị An', 'FEMALE', '2001-10-15', NULL, 'Đồng Tháp', '0900000052', @root_person_id + 46, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Zin (2 con)
('Nguyễn Văn Bảo', 'MALE', '2000-12-22', NULL, 'Tiền Giang', '0900000053', NULL, @root_person_id + 47, @family_id, NOW(), NOW()),
('Nguyễn Thị Cúc', 'FEMALE', '2002-02-28', NULL, 'Tiền Giang', '0900000054', NULL, @root_person_id + 47, @family_id, NOW(), NOW());

-- Thế hệ 5: Chít của tổ tiên (24 chít) - Thế hệ trẻ nhất
INSERT INTO persons (name, gender, date_of_birth, date_of_death, address, phone, father_id, mother_id, family_id, created_at, updated_at)
VALUES 
-- Con của Nguyễn Văn Anh (1 con)
('Nguyễn Văn Anh Minh', 'MALE', '1975-03-15', NULL, 'Hà Nội', '0900000055', @root_person_id + 48, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Bích (1 con)
('Nguyễn Văn Bích Chi', 'MALE', '1977-05-20', NULL, 'Hà Nội', '0900000056', NULL, @root_person_id + 49, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Cường (1 con)
('Nguyễn Văn Cường Đức', 'MALE', '1978-07-25', NULL, 'TP.HCM', '0900000057', @root_person_id + 50, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Dung (1 con)
('Nguyễn Văn Dung Em', 'MALE', '1980-09-30', NULL, 'TP.HCM', '0900000058', NULL, @root_person_id + 51, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Em (1 con)
('Nguyễn Văn Em Phương', 'MALE', '1981-11-05', NULL, 'Đà Nẵng', '0900000059', @root_person_id + 52, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Phương (1 con)
('Nguyễn Văn Phương Giang', 'MALE', '1983-01-10', NULL, 'Đà Nẵng', '0900000060', NULL, @root_person_id + 53, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Giang (1 con)
('Nguyễn Văn Giang Hải', 'MALE', '1984-03-15', NULL, 'Hải Phòng', '0900000061', @root_person_id + 54, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Hương (1 con)
('Nguyễn Văn Hương Ích', 'MALE', '1986-05-20', NULL, 'Hải Phòng', '0900000062', NULL, @root_person_id + 55, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Ích (1 con)
('Nguyễn Văn Ích Kim', 'MALE', '1987-07-25', NULL, 'Cần Thơ', '0900000063', @root_person_id + 56, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Kim (1 con)
('Nguyễn Văn Kim Lâm', 'MALE', '1989-09-30', NULL, 'Cần Thơ', '0900000064', NULL, @root_person_id + 57, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Lâm (1 con)
('Nguyễn Văn Lâm Linh', 'MALE', '1990-11-05', NULL, 'Nha Trang', '0900000065', @root_person_id + 58, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Linh (1 con)
('Nguyễn Văn Linh Mạnh', 'MALE', '1992-01-10', NULL, 'Nha Trang', '0900000066', NULL, @root_person_id + 59, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Mạnh (1 con)
('Nguyễn Văn Mạnh Nga', 'MALE', '1993-03-15', NULL, 'Huế', '0900000067', @root_person_id + 60, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Nga (1 con)
('Nguyễn Văn Nga Oanh', 'MALE', '1995-05-20', NULL, 'Huế', '0900000068', NULL, @root_person_id + 61, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Oanh (1 con)
('Nguyễn Văn Oanh Phượng', 'MALE', '1996-07-25', NULL, 'Vinh', '0900000069', @root_person_id + 62, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Phượng (1 con)
('Nguyễn Văn Phượng Quang', 'MALE', '1998-09-30', NULL, 'Vinh', '0900000070', NULL, @root_person_id + 63, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Quang (1 con)
('Nguyễn Văn Quang Rồng', 'MALE', '1999-11-05', NULL, 'Quy Nhon', '0900000071', @root_person_id + 64, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Rồng (1 con)
('Nguyễn Văn Rồng Sơn', 'MALE', '2001-01-10', NULL, 'Quy Nhon', '0900000072', NULL, @root_person_id + 65, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Sơn (1 con)
('Nguyễn Văn Sơn Tùng', 'MALE', '2002-03-15', NULL, 'Pleiku', '0900000073', @root_person_id + 66, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Tùng (1 con)
('Nguyễn Văn Tùng Uyên', 'MALE', '2004-05-20', NULL, 'Pleiku', '0900000074', NULL, @root_person_id + 67, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Uyên (1 con)
('Nguyễn Văn Uyên Vinh', 'MALE', '2005-07-25', NULL, 'Buôn Ma Thuột', '0900000075', @root_person_id + 68, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Vinh (1 con)
('Nguyễn Văn Vinh Xuân', 'MALE', '2007-09-30', NULL, 'Buôn Ma Thuột', '0900000076', NULL, @root_person_id + 69, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Xuân (1 con)
('Nguyễn Văn Xuân Yên', 'MALE', '2008-11-05', NULL, 'Đà Lạt', '0900000077', @root_person_id + 70, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Yên (1 con)
('Nguyễn Văn Yên Zin', 'MALE', '2010-01-10', NULL, 'Đà Lạt', '0900000078', NULL, @root_person_id + 71, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Zin (1 con)
('Nguyễn Văn Zin An', 'MALE', '2011-03-15', NULL, 'Long Xuyên', '0900000079', @root_person_id + 72, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị An (1 con)
('Nguyễn Văn An Bảo', 'MALE', '2013-05-20', NULL, 'Long Xuyên', '0900000080', NULL, @root_person_id + 73, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Bảo (1 con)
('Nguyễn Văn Bảo Cúc', 'MALE', '2014-07-25', NULL, 'Rạch Giá', '0900000081', @root_person_id + 74, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Cúc (1 con)
('Nguyễn Văn Cúc Dung', 'MALE', '2016-09-30', NULL, 'Rạch Giá', '0900000082', NULL, @root_person_id + 75, @family_id, NOW(), NOW()),

-- Con của Trần Văn Cường (1 con)
('Trần Văn Cường Em', 'MALE', '2015-11-05', NULL, 'Cà Mau', '0900000083', @root_person_id + 76, NULL, @family_id, NOW(), NOW()),

-- Con của Trần Thị Dung (1 con)
('Trần Văn Dung Phương', 'MALE', '2017-01-10', NULL, 'Cà Mau', '0900000084', NULL, @root_person_id + 77, @family_id, NOW(), NOW()),

-- Con của Trần Văn Em (1 con)
('Trần Văn Em Giang', 'MALE', '2018-03-15', NULL, 'Sóc Trăng', '0900000085', @root_person_id + 78, NULL, @family_id, NOW(), NOW()),

-- Con của Trần Thị Phương (1 con)
('Trần Văn Phương Hương', 'MALE', '2020-05-20', NULL, 'Sóc Trăng', '0900000086', NULL, @root_person_id + 79, @family_id, NOW(), NOW()),

-- Con của Trần Văn Giang (1 con)
('Trần Văn Giang Ích', 'MALE', '2019-07-25', NULL, 'Bạc Liêu', '0900000087', @root_person_id + 80, NULL, @family_id, NOW(), NOW()),

-- Con của Trần Thị Hương (1 con)
('Trần Văn Hương Kim', 'MALE', '2021-09-30', NULL, 'Bạc Liêu', '0900000088', NULL, @root_person_id + 81, @family_id, NOW(), NOW()),

-- Con của Trần Văn Ích (1 con)
('Trần Văn Ích Lâm', 'MALE', '2022-11-05', NULL, 'Cao Lãnh', '0900000089', @root_person_id + 82, NULL, @family_id, NOW(), NOW()),

-- Con của Trần Thị Kim (1 con)
('Trần Văn Kim Linh', 'MALE', '2024-01-10', NULL, 'Cao Lãnh', '0900000090', NULL, @root_person_id + 83, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Lâm (1 con)
('Nguyễn Văn Lâm Mạnh', 'MALE', '2023-03-15', NULL, 'Mỹ Tho', '0900000091', @root_person_id + 84, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Linh (1 con)
('Nguyễn Văn Linh Nga', 'MALE', '2025-05-20', NULL, 'Mỹ Tho', '0900000092', NULL, @root_person_id + 85, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Mạnh (1 con)
('Nguyễn Văn Mạnh Oanh', 'MALE', '2024-07-25', NULL, 'Tân An', '0900000093', @root_person_id + 86, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Nga (1 con)
('Nguyễn Văn Nga Phượng', 'MALE', '2026-09-30', NULL, 'Tân An', '0900000094', NULL, @root_person_id + 87, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Oanh (1 con)
('Nguyễn Văn Oanh Quang', 'MALE', '2025-11-05', NULL, 'Bến Tre', '0900000095', @root_person_id + 88, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Phượng (1 con)
('Nguyễn Văn Phượng Rồng', 'MALE', '2027-01-10', NULL, 'Bến Tre', '0900000096', NULL, @root_person_id + 89, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Quang (1 con)
('Nguyễn Văn Quang Sơn', 'MALE', '2026-03-15', NULL, 'Vĩnh Long', '0900000097', @root_person_id + 90, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Rồng (1 con)
('Nguyễn Văn Rồng Tùng', 'MALE', '2028-05-20', NULL, 'Vĩnh Long', '0900000098', NULL, @root_person_id + 91, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Sơn (1 con)
('Nguyễn Văn Sơn Uyên', 'MALE', '2027-07-25', NULL, 'Trà Vinh', '0900000099', @root_person_id + 92, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Tùng (1 con)
('Nguyễn Văn Tùng Vinh', 'MALE', '2029-09-30', NULL, 'Trà Vinh', '0900000100', NULL, @root_person_id + 93, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Uyên (1 con)
('Nguyễn Văn Uyên Xuân', 'MALE', '2028-11-05', NULL, 'Kiên Giang', '0900000101', @root_person_id + 94, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Vinh (1 con)
('Nguyễn Văn Vinh Yên', 'MALE', '2030-01-10', NULL, 'Kiên Giang', '0900000102', NULL, @root_person_id + 95, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Xuân (1 con)
('Nguyễn Văn Xuân Zin', 'MALE', '2029-03-15', NULL, 'An Giang', '0900000103', @root_person_id + 96, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Yên (1 con)
('Nguyễn Văn Yên An', 'MALE', '2031-05-20', NULL, 'An Giang', '0900000104', NULL, @root_person_id + 97, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Zin (1 con)
('Nguyễn Văn Zin Bảo', 'MALE', '2030-07-25', NULL, 'Đồng Tháp', '0900000105', @root_person_id + 98, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị An (1 con)
('Nguyễn Văn An Cúc', 'MALE', '2032-09-30', NULL, 'Đồng Tháp', '0900000106', NULL, @root_person_id + 99, @family_id, NOW(), NOW()),

-- Con của Nguyễn Văn Bảo (1 con)
('Nguyễn Văn Bảo Dung', 'MALE', '2031-11-05', NULL, 'Tiền Giang', '0900000107', @root_person_id + 100, NULL, @family_id, NOW(), NOW()),

-- Con của Nguyễn Thị Cúc (1 con)
('Nguyễn Văn Cúc Em', 'MALE', '2033-01-10', NULL, 'Tiền Giang', '0900000108', NULL, @root_person_id + 101, @family_id, NOW(), NOW());

-- Cập nhật thông tin gia đình
UPDATE families SET name_family = 'Dòng họ Nguyễn - Gốc từ Nguyễn Văn Cội' WHERE id = @family_id;

-- Hiển thị thống kê
SELECT 
    'Tổng số thành viên' as ThongKe,
    COUNT(*) as SoLuong
FROM persons 
WHERE family_id = @family_id

UNION ALL

SELECT 
    'Số nam giới' as ThongKe,
    COUNT(*) as SoLuong
FROM persons 
WHERE family_id = @family_id AND gender = 'MALE'

UNION ALL

SELECT 
    'Số nữ giới' as ThongKe,
    COUNT(*) as SoLuong
FROM persons 
WHERE family_id = @family_id AND gender = 'FEMALE'

UNION ALL

SELECT 
    'Số người còn sống' as ThongKe,
    COUNT(*) as SoLuong
FROM persons 
WHERE family_id = @family_id AND date_of_death IS NULL

UNION ALL

SELECT 
    'Số người đã mất' as ThongKe,
    COUNT(*) as SoLuong
FROM persons 
WHERE family_id = @family_id AND date_of_death IS NOT NULL;
