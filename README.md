# internship_java_spring_tronglm_202006_ver2
Thực tập Java Spring Boot - Quản lý giải đấu - TrongLM - 202006

## Môi trường phát triển
- Java 8
- Java Spring Boot + Mybatis
- DB MYSQL 5.7 trở lên
- Vuejs

## Nội dung

### Login
	Nhập thông tin user/password để thực hiện đăng nhập
	Password mã hóa Bcrypt
### Đăng ký user
	Cần validate các trường user là duy nhất, địa chỉ email phải hợp lệ, số điện thoại chỉ được nhập số
### Lấy lại mật khẩu
	Tự động tạo mật khẩu mới rồi gửi mail thông tin mật khẩu
### Thay đổi thông tin cá nhân
	Chỉ được thay đổi thông tin của cá nhân
### Danh sách user
	Chỉ có Admin có chức năng này
### Sửa thông tin cá nhân
	Admin có quyền đóng user, thay đổi thông tin số điện thoại, mật khẩu, địa chỉ của user thông thường
	Admin có thể thay đổi thông tin số điện thoại, mật khẩu, địa chỉ, role của tất cả các user, có quyền đóng user

### Dashboard
    - Hiển thị lịch thi đấu sắp tới
    - Hiển thị kết quả thi đấu gần nhất
    - Hiển thị bảng xếp hạng
    - Mọi user có thể xem

### Quản lý thông tin giải đấu
	- Thêm/sửa/xóa/xem chi tiết giải đấu
	- Tìm kiếm/hiển thị danh sách giải đấu
	- Chỉ Admin có quyền thực hiện

### Quản lý thông tin team
	- Thêm/sửa/xóa/xem chi tiết team
	- Tìm kiếm/hiển thị danh sách team
	- Hiển thị danh sách thành viên trong team
	- Thêm/xóa thành viên vào team (1 thành viên thì ko thể ở 2 team cùng 1 lúc)
	- Chỉ Admin có quyền thực hiện

### Quản lý thành viên trong team
	- Thêm/sửa/xóa/xem chi tiết thành viên
	- Tìm kiếm/hiển thị danh sách thành viên
	- Chỉ Admin có quyền thực hiện
	
### Quản lý lịch thi đấu
	- Thêm/sửa/xóa/xem chi tiết lịch cho từng trận đấu
	- Tìm kiếm/hiển thị lịch thi trận đấu
	- Chỉ Admin có quyền thực hiện

### Cập nhật kết quả trận đấu
	- Chức năng cập nhật kết quả cho từng trận đấu(cập nhật trạng thái trận đấu khi có kết quả)
	- Chỉ Admin có quyền thực hiện

### Bảng xếp hạng team
	- Tự động tổng hợp kết quả thi đấu để lấy lên bảng xếp hạng(sắp xếp theo điểm số)

## Tham khảo
  Coding convention: Google Java Style Guide https://google.github.io/styleguide/javaguide.html
