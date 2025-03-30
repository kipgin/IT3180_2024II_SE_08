# IT3180_2024II_SE_01

## Yêu cầu

Trước khi bắt đầu sử dụng ứng dụng, bạn cần đảm bảo máy tính của mình đã cài đặt đầy đủ các công cụ sau:

1. **IDE để chạy Java**:
    - IntelliJ IDEA và Eclipse. Trong đó Intellij sẽ dùng để chạy Backend, và Eclipse sẽ chạy Frontend
2. **Tài nguyên**:
    - https://drive.google.com/drive/folders/1DOx_PrBDPG-06iy4j011uQ1ZQ3B-xG4j?usp=sharing
    - Bạn hãy truy cập vào link trên để lấy hai file application.properties và application.yaml (trong trường hợp bạn muốn chạy project bên backend) (khi đó bạn sẽ cần copy hai file này vào \src\main\resources)


### Chú ý
1. Nhánh main - Backend
2. Nhánh fronted - Frontend
## Hướng dẫn cách chạy ứng dụng

Sau khi đã cài đặt tất cả các công cụ cần thiết, bạn có thể làm theo các bước dưới đây để chạy ứng dụng:

### Bước 1: Clone repository

Trước tiên, bạn cần **clone repository**  từ GitHub về máy tính của mình.
### Trường hợp bạn muốn chạy Backend
### Bước 2: Chạy Backend

1. Mở thư mục `backend` trong IDE IntelliJ IDEA
2. Chạy ứng dụng Java này bằng cách nhấn vào nút **Run** trong IntelliJ IDEA

Lúc này, backend của ứng dụng sẽ bắt đầu chạy

### Tuy nhiên, bạn có thể chạy trực tiếp Frontend và sử dụng (do các API đã được đưa lên AWS)
### Bước 3: Chạy Frontend

1. Import project vào Eclipse
2. Import các jar cần thiết từ thư mục libs vào project, bên cạnh đó là của JavaFx
3. Xóa module-info.java trong src
4. Chạy project

### Kiểm tra hoạt động của ứng dụng

Nếu mọi thứ đã được cấu hình đúng, bạn sẽ thấy giao diện chính của ứng dụng.

## Đăng nhập vào ứng dụng

Khi mở ứng dụng, bạn sẽ được yêu cầu đăng nhập. Sử dụng thông tin tài khoản mặc định sau để đăng nhập:

- **Tên đăng nhập**: `admin`
- **Mật khẩu**: `1234`

Sau khi đăng nhập thành công, bạn sẽ được chuyển đến giao diện chính của ứng dụng với các tính năng cơ bản.

