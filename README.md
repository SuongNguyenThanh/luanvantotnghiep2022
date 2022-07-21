# Luan Van Tot Nghiep 2022 - NGUYEN THANH SUONG
###Các công nghệ sử dụng:
```
Spring Boot 2.7.1
Spring 5.3.21
Maven 3.8.6
JDK 11
IntellIJ
```

###Chỉnh sửa file application.properties
```
server.port: 8080
spring.datasource.url=jdbc:postgresql://${POSTGRES_HOST}:${POSTGRES_PORT}/${POSTGRES_DB}
spring.datasource.username=${POSTGRES_NAME}
spring.datasource.password=${POSTGRES_PASSWORD}
```
###Run application
```
git clone https://github.com/SuongNguyenThanh/luanvantotnghiep2022.git
cd luanvantotnghiep2022
mvn clean install
spring-boot:run
```
### Hoặc
```
git clone https://github.com/SuongNguyenThanh/luanvantotnghiep2022.git
cd luanvantotnghiep2022
java -jar target/*.jar
```
##Sau do ban truy cap hppt://localhost:8080

### LOGIN 
#FRONTEND: http://nguyenthanhsuong.com
#ADMIN: http://nguyenthanhsuong.com/admin/index.do
```
Username: demo
Password: &#l9tz!UGm69
```
