# 📦 QUẢN LÝ SẢN PHẨM (Spring Boot CRUD & Laragon MySQL)

Dự án Java Web cơ bản học phần Công nghệ phần mềm, tập trung vào kiến thức **Thêm - Sửa - Xóa - Xem danh sách (CRUD)** cho đối tượng **Sản phẩm (Product)** sử dụng **Spring Boot 3**, **Spring Data JPA**, **Thymeleaf** và **Laragon MySQL**.

---

## 🎯 Chức năng CRUD cơ bản đã thực hiện

1. **Xem danh sách sản phẩm (`GET /products`)**:
   - Hiển thị danh sách sản phẩm dạng **Bảng dữ liệu (Table)** gồm các thông tin: ID, Hình ảnh, Tên sản phẩm, Danh mục, Giá bán, Tồn kho, Mô tả và Cột Hành động.
   - Hỗ trợ ô lọc & tìm kiếm sản phẩm theo tên, danh mục và khoảng giá.

2. **Thêm sản phẩm mới (`GET /products/add` & `POST /products/save`)**:
   - Giao diện Form thêm mới sản phẩm gọn gàng, có chọn danh mục từ danh sách.
   - Xử lý lưu thông tin sản phẩm mới vào MySQL Laragon qua Spring Data JPA (`save`).

3. **Chỉnh sửa sản phẩm (`GET /products/edit/{id}` & `POST /products/save`)**:
   - Nút **Sửa** điền sẵn thông tin sản phẩm cần chỉnh sửa vào Form.
   - Xử lý cập nhật thông tin sản phẩm và lưu lại vào MySQL.

4. **Xóa sản phẩm (`GET /products/delete/{id}`)**:
   - Nút **Xóa** có hộp thoại xác nhận JavaScript (`confirm`).
   - Xử lý xóa sản phẩm khỏi MySQL Laragon (`deleteById`).

---

## 🗄️ Cấu hình Laragon MySQL

Tệp `src/main/resources/application.properties`:

```properties
spring.application.name=saleappv1
server.port=8088

# Kết nối MySQL trong Laragon (đổi cổng 4406 thành 3306 nếu máy bạn dùng port mặc định)
spring.datasource.url=jdbc:mysql://localhost:4406/webbanhang?useSSL=false&serverTimezone=Asia/Ho_Chi_Minh&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

---

## 🚀 Các bước khởi chạy dự án

1. **Bật Laragon**: Mở Laragon và nhấn **Start All** (dịch vụ MySQL đang chạy).
2. **Tạo CSDL**: Tạo Database tên `webbanhang` trong Laragon (HeidiSQL hoặc phpMyAdmin).
3. **Chạy ứng dụng**:
   ```powershell
   ./mvnw.cmd spring-boot:run
   ```
4. **Truy cập đường dẫn**:
   👉 **[http://localhost:8088/products](http://localhost:8088/products)**

---

## 🌐 Các đường dẫn (URL) thử nghiệm CRUD

| Chức năng | Đường dẫn (URL) |
| :--- | :--- |
| **Bảng danh sách sản phẩm** | [http://localhost:8088/products](http://localhost:8088/products) |
| **Form thêm sản phẩm mới** | [http://localhost:8088/products/add](http://localhost:8088/products/add) |
| **Sửa sản phẩm ID 101** | [http://localhost:8088/products/edit/101](http://localhost:8088/products/edit/101) |
| **Xóa sản phẩm ID 101** | [http://localhost:8088/products/delete/101](http://localhost:8088/products/delete/101) |
