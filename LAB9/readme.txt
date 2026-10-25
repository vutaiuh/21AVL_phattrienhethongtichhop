THỰC HÀNH LAB 06 – RMI
Bài 1. Máy chủ RMI trả về chuỗi "Hello, World!"
Mô tả:
Viết một ứng dụng RMI trong đó client gọi phương thức từ server để nhận về chuỗi "Hello, World!".

Yêu cầu:
- Tạo interface chứa phương thức sayHello().
- Triển khai phương thức này ở server.
- Client gọi phương thức từ xa.     LAB_06_RMI
Bài 2. Bài tập Tính Tổng Hai Số
Mô tả:
Viết ứng dụng RMI cho phép client gửi hai số nguyên a và b đến server, sau đó server trả về tổng a + b.

Yêu cầu:
- Interface chứa phương thức int add(int a, int b).
- Server triển khai phương thức này.
- Client nhập hai số từ bàn phím và gửi đến server để nhận kết quả.     LAB_06_RMI
Bài 3. Bài tập Kiểm Tra Số Nguyên Tố
Mô tả:
Viết chương trình RMI giúp client kiểm tra xem một số có phải là số nguyên tố hay không.

Yêu cầu:
- Interface chứa phương thức boolean isPrime(int n).
- Server kiểm tra số nguyên tố và trả kết quả.
- Client nhập số và nhận kết quả từ server.     LAB_06_RMI
Bài 4. Bài tập Quản Lý Danh Bạ
Mô tả:
Xây dựng một hệ thống quản lý danh bạ từ xa bằng RMI. Client có thể thêm, sửa, xóa và tìm kiếm liên hệ trong danh bạ lưu trên server.

Yêu cầu:
- Interface có các phương thức:void addContact(String name, String phone)
  String findContact(String name)
  boolean deleteContact(String name)
- Server lưu trữ danh bạ bằng HashMap<String, String>.
- Client nhập dữ liệu và gửi yêu cầu.     LAB_06_RMI
Bài 5. Bài tập Chat Đơn Giản với RMI
Mô tả:
Tạo ứng dụng chat 1-1 giữa client và server thông qua RMI.

Yêu cầu:
- Interface có phương thức:void sendMessage(String message)
- Server nhận tin nhắn từ client và phản hồi.
- Client nhập tin nhắn và hiển thị phản hồi.     LAB_06_RMI
Bài 6. Bài tập Quản Lý Tài Khoản Ngân Hàng
Mô tả:
Xây dựng ứng dụng ngân hàng từ xa, cho phép client kiểm tra số dư, gửi tiền và rút tiền.

Yêu cầu:
- Interface có các phương thức:double getBalance()
  void deposit(double amount)
  boolean withdraw(double amount)
- Server quản lý tài khoản với số dư ban đầu.
- Client có thể gửi/rút tiền và kiểm tra số dư.     LAB_06_RMI
Bài 7. Bài tập Tính Diện Tích Hình Học
Mô tả:
Viết chương trình RMI giúp client tính diện tích hình chữ nhật, hình tròn, hình tam giác.

Yêu cầu:
- Interface có phương thức:double rectangleArea(double width, double height)
  double circleArea(double radius)
  double triangleArea(double base, double height)
- Server thực hiện các phép tính.
- Client gửi thông số và nhận diện tích từ server.     LAB_06_RMI
Bài 8. Bài tập Hệ Thống Đặt Vé Máy Bay
Mô tả:
Viết ứng dụng RMI quản lý đặt chỗ vé máy bay từ xa.

Yêu cầu:
- Interface có các phương thức:boolean bookTicket(String flightNumber, int seats)
  int availableSeats(String flightNumber)
  boolean cancelBooking(String flightNumber, int seats)
- Server quản lý danh sách các chuyến bay.
- Client có thể đặt, hủy vé và kiểm tra số ghế trống.     LAB_06_RMI
Bài 9. Hệ thống Đấu giá trực tuyến (Online Auction System)
Mô tả:
- Xây dựng hệ thống đấu giá trực tuyến bằng Java RMI.
- Người dùng có thể đăng ký sản phẩm đấu giá và đưa ra giá thầu (bid).
- Server quản lý danh sách sản phẩm và giá thầu, cập nhật giá cao nhất theo thời gian thực.     LAB_06_RMI
Yêu cầu chức năng:

1. Chủ sở hữu (Seller):
- Thêm sản phẩm vào hệ thống đấu giá (tên sản phẩm, giá khởi điểm).
2. Người đấu giá (Bidder):
- Xem danh sách sản phẩm đang đấu giá.
- Đưa ra giá thầu cao hơn giá hiện tại.
3. Server (Auction Server):
- Quản lý danh sách sản phẩm & giá thầu.
- Cập nhật giá cao nhất cho từng sản phẩm.
- Thông báo khi có người đặt giá thầu cao hơn.