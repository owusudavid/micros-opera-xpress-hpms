# Micros Opera Xpress HPMS

## Hotel Property Management System (HPMS)

A comprehensive Java-based Hotel Property Management System inspired by Oracle Micros Opera Xpress. This application provides complete hotel operations management including guest management, reservations, room management, billing, and reporting.

### Features

- **Guest Management**: Complete guest profiles, preferences, and history
- **Reservation Management**: Online and manual reservations with modifications
- **Room Management**: Room inventory, status tracking, housekeeping
- **Billing & POS**: Guest billing, payment processing, folio management
- **Staff Management**: Employee scheduling, roles, and permissions
- **Reporting**: Revenue reports, occupancy analysis, guest analytics
- **Security**: JWT authentication, role-based access control
- **Multi-property Support**: Manage multiple hotel properties

### Technology Stack

- **Backend**: Java 11, Spring Boot 2.7.14
- **Database**: MySQL 8.0
- **Security**: Spring Security, JWT
- **API Documentation**: Swagger/OpenAPI 3.0
- **Build Tool**: Maven

### Prerequisites

- Java 11+
- MySQL 8.0+
- Maven 3.6+

### Project Structure

```
src/
├── main/
│   ├── java/com/hpms/opera/
│   │   ├── config/           # Application configuration
│   │   ├── controller/        # REST endpoints
│   │   ├── service/           # Business logic
│   │   ├── repository/        # Data access layer
│   │   ├── entity/            # JPA entities
│   │   ├── dto/               # Data transfer objects
│   │   ├── exception/         # Custom exceptions
│   │   ├── security/          # Security components
│   │   ├── util/              # Utility classes
│   │   └── HpmsApplication.java # Main application class
│   └── resources/
│       ├── application.yml    # Application properties
│       └── db/migration/      # Flyway migrations
└── test/
    └── java/com/hpms/opera/   # Unit and integration tests
```

### Getting Started

1. **Clone the repository**
   ```bash
   git clone https://github.com/owusudavid/micros-opera-xpress-hpms.git
   cd micros-opera-xpress-hpms
   ```

2. **Configure database**
   - Update `application.yml` with your MySQL credentials
   - Create the database: `CREATE DATABASE hpms_db;`

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the API**
   - API Documentation: http://localhost:8080/swagger-ui.html
   - Health Check: http://localhost:8080/actuator/health

### API Endpoints

#### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration
- `POST /api/auth/refresh` - Refresh token

#### Guest Management
- `GET /api/guests` - List all guests
- `POST /api/guests` - Create new guest
- `GET /api/guests/{id}` - Get guest details
- `PUT /api/guests/{id}` - Update guest
- `DELETE /api/guests/{id}` - Delete guest

#### Reservations
- `GET /api/reservations` - List reservations
- `POST /api/reservations` - Create reservation
- `PUT /api/reservations/{id}` - Update reservation
- `DELETE /api/reservations/{id}` - Cancel reservation
- `GET /api/reservations/{id}/checkin` - Check-in guest
- `GET /api/reservations/{id}/checkout` - Check-out guest

#### Room Management
- `GET /api/rooms` - List all rooms
- `GET /api/rooms/{id}` - Get room details
- `PUT /api/rooms/{id}/status` - Update room status
- `GET /api/rooms/available` - Get available rooms

#### Billing
- `GET /api/folios/{guestId}` - Get guest folio
- `POST /api/folios/{guestId}/charges` - Add charge
- `POST /api/folios/{guestId}/payments` - Process payment
- `GET /api/invoices/{id}` - Get invoice

#### Reports
- `GET /api/reports/occupancy` - Occupancy report
- `GET /api/reports/revenue` - Revenue report
- `GET /api/reports/guests` - Guest analytics

### Configuration

Edit `application.yml` to configure:

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/hpms_db
    username: root
    password: password
  jpa:
    hibernate:
      ddl-auto: validate

jwt:
  secret: your-secret-key
  expiration: 86400000
```

### Database Schema

The application uses Flyway for database migrations. All schemas are automatically created on startup.

### Security

- JWT-based authentication
- Role-based access control (Admin, Manager, Receptionist, Housekeeper)
- Password encryption using BCrypt
- CORS support for frontend integration

### Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### License

This project is licensed under the MIT License - see the LICENSE file for details.

### Support

For support, email support@hpms-opera.com or open an issue on GitHub.
