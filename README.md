Redis (viết tắt của Remote Dictionary Server) là một hệ quản trị cơ sở dữ liệu NoSQL mã nguồn mở, lưu trữ dữ liệu trên bộ nhớ (in-memory database) dưới dạng key-value. Redis nổi bật bởi hiệu suất cao, hỗ trợ nhiều cấu trúc dữ liệu, và khả năng mở rộng mạnh mẽ.

Đặc điểm chính của Redis: Lưu trữ trên bộ nhớ (In-memory):

Redis lưu dữ liệu trong RAM, giúp truy xuất cực nhanh. Dữ liệu có thể được ghi vào đĩa để sao lưu và phục hồi. Key-Value Store:

Redis hoạt động như một cơ sở dữ liệu key-value, trong đó mỗi giá trị có thể là: Chuỗi (String) Danh sách (List) Tập hợp (Set) Bản đồ băm (Hash) Tập hợp có thứ tự (Sorted Set) Các cấu trúc phức tạp hơn như HyperLogLog, Stream. Hiệu suất cao:

Redis có tốc độ đọc/ghi dữ liệu rất nhanh, phù hợp với các ứng dụng cần xử lý dữ liệu theo thời gian thực. Hỗ trợ Pub/Sub:

Redis có thể được sử dụng như một hệ thống nhắn tin publish/subscribe, giúp truyền thông giữa các thành phần ứng dụng. Đa dạng ứng dụng:

Cache: Lưu trữ tạm thời dữ liệu để tăng tốc độ truy cập. Session Store: Lưu trạng thái phiên làm việc của người dùng. Message Queue: Hàng đợi tin nhắn. Leaderboard: Tạo bảng xếp hạng trong các ứng dụng. Hỗ trợ đa nền tảng và ngôn ngữ:

Redis hỗ trợ rất nhiều ngôn ngữ lập trình như Python, Java, JavaScript, C#, Go, PHP, Ruby, v.v.

Docker Image là một mẫu (template) chỉ định cách thức xây dựng một container trong Docker. Nó chứa mọi thứ cần thiết để chạy một ứng dụng, bao gồm hệ điều hành, các thư viện, mã nguồn, cấu hình và phụ thuộc khác.

Docker Image là immutable (không thể thay đổi) và có thể tái sử dụng nhiều lần để tạo ra các container. Các container, khi được tạo từ một image, là các phiên bản chạy của image đó.

Cấu trúc của Docker Image: Docker Image được tạo từ một tệp tin có tên Dockerfile, chứa các chỉ thị (instructions) hướng dẫn Docker cách tạo ra một image từ cơ sở (base image) và các bước cài đặt các thành phần cần thiết.

Git flow
