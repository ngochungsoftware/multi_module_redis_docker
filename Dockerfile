# Sử dụng một base image nhẹ của OpenJDK
FROM openjdk:21-slim

# Thiết lập thư mục làm việc
WORKDIR /app

# Sao chép file JAR của ứng dụng vào container
COPY blogger-core/target/blogger-core-1.0-SNAPSHOT.jar app.jar

# Mở cổng mà ứng dụng sẽ lắng nghe
EXPOSE 8080

# Chạy ứng dụng khi container khởi động
ENTRYPOINT ["java", "-jar", "app.jar"]