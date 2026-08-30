# Opera Xpress HPMS - Hotel Property Management System

## Overview

Opera Xpress HPMS is a comprehensive Hotel Property Management System built with Spring Boot and designed to streamline hotel operations including guest management, room management, reservations, and billing.

## Features

### Core Functionality
- **Guest Management**: Complete guest profile management with contact details and preferences
- **Room Management**: Track room status, inventory, and availability
- **Reservation System**: Manage bookings with check-in/check-out tracking
- **Billing & Folio**: Advanced billing system with charges and payment processing
- **User Authentication**: JWT-based authentication with role-based access control
- **Audit Trail**: Track all operations with timestamps and user information

### Security
- JWT token-based authentication
- Role-Based Access Control (RBAC) with multiple roles:
  - ADMIN: Full system access
  - MANAGER: Operational management
  - RECEPTIONIST: Front-desk operations
  - ACCOUNTANT: Financial operations
  - HOUSEKEEPER: Room maintenance
- Password encryption with BCrypt
- CORS configuration for secure cross-origin requests

### Architecture
- RESTful API design
- Layered architecture (Controller → Service → Repository)
- JPA/Hibernate ORM
- MySQL database
- OpenAPI/Swagger documentation

## Technology Stack

- **Framework**: Spring Boot 2.7.14
- **Java Version**: 11
- **Database**: MySQL 8.0
- **ORM**: Hibernate/JPA
- **Security**: Spring Security + JWT
- **API Documentation**: SpringDoc OpenAPI (Swagger)
- **Build Tool**: Maven
- **Logging**: SLF4J + Logback

## Prerequisites

- Java 11 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

## Installation

### 1. Clone the Repository
```bash
git clone https://github.com/owusudavid/micros-opera-xpress-hpms.git
cd micros-opera-xpress-hpms
```

### 2. Configure Database

Create a MySQL database:
```sql
CREATE DATABASE opera_hpms;
```

Update `application.yml` with your database credentials:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/opera_hpms?useSSL=false&serverTimezone=UTC
    username: your_username
    password: your_password
```

### 3. Configure JWT Secret

Update the JWT secret in `application.yml`:
```yaml
jwt:
  secret: your-super-secret-key-change-this-in-production-environment
  expiration: 86400000  # 24 hours in milliseconds
```

### 4. Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start at `http://localhost:8080`

## API Documentation

### Access Swagger UI
Once the application is running, visit:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/v3/api-docs

## API Endpoints

### Authentication Endpoints

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "user@example.com",
  "password": "password123"
}

Response (200):
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzUxMiJ9...",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9...",
    "expiresIn": 86400000
  },
  "timestamp": "2024-08-30T10:00:00"
}
```

#### Register
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "newuser",
  "email": "user@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe",
  "role": "RECEPTIONIST"
}

Response (201):
{
  "success": true,
  "message": "User registered successfully",
  "data": {
    "id": 1,
    "username": "newuser",
    "email": "user@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "RECEPTIONIST"
  },
  "timestamp": "2024-08-30T10:00:00"
}
```

#### Refresh Token
```http
POST /api/auth/refresh
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...

Response (200):
{
  "success": true,
  "message": "Token refreshed successfully",
  "data": {
    "token": "eyJhbGciOiJIUzUxMiJ9...",
    "expiresIn": 86400000
  },
  "timestamp": "2024-08-30T10:00:00"
}
```

### Guest Endpoints

#### Create Guest
```http
POST /api/guests
Authorization: Bearer <token>
Content-Type: application/json

{
  "firstName": "Jane",
  "lastName": "Smith",
  "email": "jane@example.com",
  "phone": "+1-555-0100",
  "address": "123 Main St",
  "city": "New York",
  "state": "NY",
  "zipCode": "10001",
  "country": "USA",
  "idType": "PASSPORT",
  "idNumber": "A12345678"
}

Response (201):
{
  "success": true,
  "message": "Guest created successfully",
  "data": {
    "id": 1,
    "firstName": "Jane",
    "lastName": "Smith",
    "email": "jane@example.com",
    "phone": "+1-555-0100",
    "address": "123 Main St",
    "city": "New York",
    "state": "NY",
    "zipCode": "10001",
    "country": "USA",
    "idType": "PASSPORT",
    "idNumber": "A12345678",
    "createdAt": "2024-08-30T10:00:00"
  },
  "timestamp": "2024-08-30T10:00:00"
}
```

#### Get Guest
```http
GET /api/guests/{id}
Authorization: Bearer <token>

Response (200):
{
  "success": true,
  "message": "Guest retrieved successfully",
  "data": {
    "id": 1,
    "firstName": "Jane",
    "lastName": "Smith",
    "email": "jane@example.com",
    "phone": "+1-555-0100",
    "createdAt": "2024-08-30T10:00:00"
  },
  "timestamp": "2024-08-30T10:00:00"
}
```

#### Get All Guests
```http
GET /api/guests
Authorization: Bearer <token>

Response (200):
{
  "success": true,
  "message": "Guests retrieved successfully",
  "data": [
    {
      "id": 1,
      "firstName": "Jane",
      "lastName": "Smith",
      "email": "jane@example.com",
      "phone": "+1-555-0100"
    },
    {
      "id": 2,
      "firstName": "John",
      "lastName": "Doe",
      "email": "john@example.com",
      "phone": "+1-555-0101"
    }
  ],
  "timestamp": "2024-08-30T10:00:00"
}
```

#### Search Guests
```http
GET /api/guests/search/{name}
Authorization: Bearer <token>

Response (200):
{
  "success": true,
  "message": "Guests searched successfully",
  "data": [
    {
      "id": 1,
      "firstName": "Jane",
      "lastName": "Smith",
      "email": "jane@example.com"
    }
  ],
  "timestamp": "2024-08-30T10:00:00"
}
```

#### Update Guest
```http
PUT /api/guests/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "firstName": "Jane",
  "lastName": "Smith",
  "email": "jane.new@example.com",
  "phone": "+1-555-0102"
}

Response (200):
{
  "success": true,
  "message": "Guest updated successfully",
  "data": {
    "id": 1,
    "firstName": "Jane",
    "lastName": "Smith",
    "email": "jane.new@example.com",
    "phone": "+1-555-0102",
    "updatedAt": "2024-08-30T10:30:00"
  },
  "timestamp": "2024-08-30T10:30:00"
}
```

#### Delete Guest
```http
DELETE /api/guests/{id}
Authorization: Bearer <token>

Response (200):
{
  "success": true,
  "message": "Guest deleted successfully",
  "data": null,
  "timestamp": "2024-08-30T10:30:00"
}
```

### Room Endpoints

#### Create Room
```http
POST /api/rooms
Authorization: Bearer <token>
Content-Type: application/json

{
  "roomNumber": "101",
  "roomType": "STANDARD",
  "capacity": 2,
  "pricePerNight": 150.00,
  "floor": 1,
  "status": "AVAILABLE",
  "propertyId": 1
}

Response (201):
{
  "success": true,
  "message": "Room created successfully",
  "data": {
    "id": 1,
    "roomNumber": "101",
    "roomType": "STANDARD",
    "capacity": 2,
    "pricePerNight": 150.00,
    "floor": 1,
    "status": "AVAILABLE",
    "createdAt": "2024-08-30T10:00:00"
  },
  "timestamp": "2024-08-30T10:00:00"
}
```

#### Get Available Rooms
```http
GET /api/rooms/available/{propertyId}
Authorization: Bearer <token>

Response (200):
{
  "success": true,
  "message": "Available rooms retrieved successfully",
  "data": [
    {
      "id": 1,
      "roomNumber": "101",
      "roomType": "STANDARD",
      "capacity": 2,
      "pricePerNight": 150.00,
      "status": "AVAILABLE"
    },
    {
      "id": 2,
      "roomNumber": "102",
      "roomType": "DELUXE",
      "capacity": 3,
      "pricePerNight": 250.00,
      "status": "AVAILABLE"
    }
  ],
  "timestamp": "2024-08-30T10:00:00"
}
```

#### Update Room Status
```http
PATCH /api/rooms/{id}/status?status=OCCUPIED
Authorization: Bearer <token>

Response (200):
{
  "success": true,
  "message": "Room status updated successfully",
  "data": {
    "id": 1,
    "roomNumber": "101",
    "status": "OCCUPIED",
    "updatedAt": "2024-08-30T10:30:00"
  },
  "timestamp": "2024-08-30T10:30:00"
}
```

### Reservation Endpoints

#### Create Reservation
```http
POST /api/reservations
Authorization: Bearer <token>
Content-Type: application/json

{
  "guestId": 1,
  "roomId": 1,
  "checkInDate": "2024-09-01",
  "checkOutDate": "2024-09-05",
  "numberOfGuests": 2,
  "status": "CONFIRMED"
}

Response (201):
{
  "success": true,
  "message": "Reservation created successfully",
  "data": {
    "id": 1,
    "guestId": 1,
    "roomId": 1,
    "checkInDate": "2024-09-01",
    "checkOutDate": "2024-09-05",
    "numberOfGuests": 2,
    "status": "CONFIRMED",
    "totalPrice": 600.00,
    "createdAt": "2024-08-30T10:00:00"
  },
  "timestamp": "2024-08-30T10:00:00"
}
```

#### Check-in Guest
```http
POST /api/reservations/{id}/check-in
Authorization: Bearer <token>

Response (200):
{
  "success": true,
  "message": "Guest checked in successfully",
  "data": {
    "id": 1,
    "guestId": 1,
    "roomId": 1,
    "status": "CHECKED_IN",
    "checkInTime": "2024-09-01T15:30:00",
    "updatedAt": "2024-09-01T15:30:00"
  },
  "timestamp": "2024-09-01T15:30:00"
}
```

#### Check-out Guest
```http
POST /api/reservations/{id}/check-out
Authorization: Bearer <token>

Response (200):
{
  "success": true,
  "message": "Guest checked out successfully",
  "data": {
    "id": 1,
    "guestId": 1,
    "roomId": 1,
    "status": "CHECKED_OUT",
    "checkOutTime": "2024-09-05T11:00:00",
    "updatedAt": "2024-09-05T11:00:00"
  },
  "timestamp": "2024-09-05T11:00:00"
}
```

#### Cancel Reservation
```http
POST /api/reservations/{id}/cancel
Authorization: Bearer <token>

Response (200):
{
  "success": true,
  "message": "Reservation cancelled successfully",
  "data": {
    "id": 1,
    "status": "CANCELLED",
    "updatedAt": "2024-08-30T10:30:00"
  },
  "timestamp": "2024-08-30T10:30:00"
}
```

### Billing Endpoints

#### Add Charge to Folio
```http
POST /api/folios/{id}/charges
Authorization: Bearer <token>
Content-Type: application/json

{
  "description": "Room Service",
  "amount": 25.00,
  "chargeType": "SERVICE"
}

Response (201):
{
  "success": true,
  "message": "Charge added successfully",
  "data": {
    "id": 1,
    "folioId": 1,
    "description": "Room Service",
    "amount": 25.00,
    "chargeType": "SERVICE",
    "createdAt": "2024-09-01T15:30:00"
  },
  "timestamp": "2024-09-01T15:30:00"
}
```

#### Process Payment
```http
POST /api/folios/{id}/payments
Authorization: Bearer <token>
Content-Type: application/json

{
  "amount": 600.00,
  "paymentMethod": "CREDIT_CARD",
  "referenceNumber": "CC123456789"
}

Response (201):
{
  "success": true,
  "message": "Payment processed successfully",
  "data": {
    "id": 1,
    "folioId": 1,
    "amount": 600.00,
    "paymentMethod": "CREDIT_CARD",
    "referenceNumber": "CC123456789",
    "status": "COMPLETED",
    "createdAt": "2024-09-05T11:00:00"
  },
  "timestamp": "2024-09-05T11:00:00"
}
```

#### Get Folio Details
```http
GET /api/folios/{id}
Authorization: Bearer <token>

Response (200):
{
  "success": true,
  "message": "Folio retrieved successfully",
  "data": {
    "id": 1,
    "guestId": 1,
    "reservationId": 1,
    "totalCharges": 625.00,
    "totalPayments": 600.00,
    "balance": 25.00,
    "status": "OPEN",
    "createdAt": "2024-09-01T15:30:00"
  },
  "timestamp": "2024-09-01T15:30:00"
}
```

#### Close Folio
```http
POST /api/folios/{id}/close
Authorization: Bearer <token>

Response (200):
{
  "success": true,
  "message": "Folio closed successfully",
  "data": {
    "id": 1,
    "status": "CLOSED",
    "closedAt": "2024-09-05T12:00:00"
  },
  "timestamp": "2024-09-05T12:00:00"
}
```

## Error Handling

The API returns standardized error responses:

```json
{
  "success": false,
  "message": "Error description",
  "data": null,
  "timestamp": "2024-08-30T10:00:00",
  "path": "/api/guests/999"
}
```

### HTTP Status Codes
- `200 OK`: Successful request
- `201 Created`: Resource created successfully
- `400 Bad Request`: Invalid request parameters
- `401 Unauthorized`: Authentication required
- `403 Forbidden`: Insufficient permissions
- `404 Not Found`: Resource not found
- `500 Internal Server Error`: Server error

## Database Schema

### Users Table
```sql
CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(255) UNIQUE NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  role VARCHAR(50) NOT NULL,
  is_active BOOLEAN DEFAULT true,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Guests Table
```sql
CREATE TABLE guests (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  email VARCHAR(255),
  phone VARCHAR(20),
  address VARCHAR(255),
  city VARCHAR(100),
  state VARCHAR(100),
  zip_code VARCHAR(20),
  country VARCHAR(100),
  id_type VARCHAR(50),
  id_number VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Rooms Table
```sql
CREATE TABLE rooms (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  room_number VARCHAR(50) NOT NULL,
  room_type VARCHAR(50) NOT NULL,
  capacity INT NOT NULL,
  price_per_night DECIMAL(10, 2),
  floor INT,
  status VARCHAR(50) DEFAULT 'AVAILABLE',
  property_id BIGINT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Reservations Table
```sql
CREATE TABLE reservations (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  guest_id BIGINT NOT NULL,
  room_id BIGINT NOT NULL,
  check_in_date DATE NOT NULL,
  check_out_date DATE NOT NULL,
  number_of_guests INT,
  total_price DECIMAL(10, 2),
  status VARCHAR(50) DEFAULT 'CONFIRMED',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (guest_id) REFERENCES guests(id),
  FOREIGN KEY (room_id) REFERENCES rooms(id)
);
```

### Folios Table
```sql
CREATE TABLE folios (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  guest_id BIGINT NOT NULL,
  reservation_id BIGINT,
  total_charges DECIMAL(10, 2) DEFAULT 0,
  total_payments DECIMAL(10, 2) DEFAULT 0,
  status VARCHAR(50) DEFAULT 'OPEN',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (guest_id) REFERENCES guests(id),
  FOREIGN KEY (reservation_id) REFERENCES reservations(id)
);
```

## Role-Based Access Control

### ADMIN
- Full system access
- Manage users, properties, and configurations
- Access all reports

### MANAGER
- Operational management
- Manage guests, rooms, and reservations
- Process billing
- Generate reports

### RECEPTIONIST
- Front-desk operations
- Check-in/check-out guests
- View guest information
- Create and modify reservations

### ACCOUNTANT
- Financial operations
- Process payments
- Generate billing reports
- View financial data

### HOUSEKEEPER
- Room maintenance
- Update room status
- View room assignments

## Development

### Running Tests
```bash
mvn test
```

### Building JAR
```bash
mvn clean package
```

### Running JAR
```bash
java -jar target/opera-xpress-hpms-1.0.0.jar
```

## Deployment

### Using Docker
```bash
# Build Docker image
docker build -t opera-xpress-hpms:1.0.0 .

# Run container
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/opera_hpms \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=password \
  opera-xpress-hpms:1.0.0
```

### Environment Variables
- `SPRING_DATASOURCE_URL`: Database URL
- `SPRING_DATASOURCE_USERNAME`: Database username
- `SPRING_DATASOURCE_PASSWORD`: Database password
- `JWT_SECRET`: JWT signing secret
- `JWT_EXPIRATION`: Token expiration time

## Monitoring

Access monitoring endpoints:
- **Health**: http://localhost:8080/actuator/health
- **Metrics**: http://localhost:8080/actuator/metrics
- **Info**: http://localhost:8080/actuator/info

## Logging

Logs are stored in `logs/opera-hpms.log` with the following configuration:
- **Max File Size**: 10MB
- **Max History**: 30 days
- **Log Level**: DEBUG for application, INFO for others

## Troubleshooting

### Connection Issues
1. Verify MySQL is running
2. Check database credentials in `application.yml`
3. Ensure database `opera_hpms` exists

### Authentication Issues
1. Verify JWT secret is configured
2. Check token format in Authorization header: `Bearer <token>`
3. Ensure token is not expired

### Performance Issues
1. Check database indexes
2. Monitor application logs
3. Verify database connection pool settings

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support, contact: support@operaxpress.com

## Version History

### v1.0.0 (2024-08-30)
- Initial release
- Core features implementation
- API documentation
- User authentication and authorization

---

**Developed by**: Opera Xpress Team

**Last Updated**: 2024-08-30
