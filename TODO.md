Badminton Booking System - TODO


Day 1 Target

## FR-04 Register User

- [x] RegisterRequest DTO
- [x] RegisterResponse DTO
- [x] UserService
- [x] UserServiceImpl
- [x] AuthController
- [x] BCrypt password encryption
- [x] Duplicate email validation
- [x] ResponseDTO
- [x] Global Exception Handler

## FR-05 User Management

- [x] Get User By Id
- [x] Get Users
- [x] Pagination
- [x] Search
- [x] Update User
- [x] Delete User

* ## FR-06 Booking Court

- [x] Create Booking
- [x] Booking Validation
- [x] Prevent Double Booking
- [x] Booking DTO
- [x] Booking Service
- [x] Booking Controller


## FR-07 Booking History

- [x] Get Booking History
- [x] Stream API Mapping
- [x] Sort By Created Date
⸻

Day 2 Target

Authentication

* Login
* JWT Access Token
* Refresh Token
* Logout
* Token Blacklist

Booking Management

* Booking History
* Approve Booking
* Reject Booking

File Upload

* Cloudinary Integration
* Upload Court Images

Account

* Change Password
* Forgot Password

⸻

Day 3 Target

FR-11 AOP Logging (10 pts)

* LoggingAspect
* Audit Log
* Execution Time Log

FR-12 Unit Test (20 pts)

Service Layer

* UserServiceTest
* BookingServiceTest
* AuthServiceTest
* CourtServiceTest
* FileServiceTest

Controller Layer

* UserControllerTest
* BookingControllerTest
* AuthControllerTest
* CourtControllerTest
* FileControllerTest

FR-13 Redis Blacklist (10 pts)

* Redis Configuration
* Redis Token Blacklist
* Replace Database Blacklist

⸻

Nice To Have

* Swagger OpenAPI
* Docker Compose
* Flyway Migration
* GitHub Actions CI/CD
* MapStruct