# API Usage Examples

This document provides practical examples of how to use the Opera Xpress HPMS API.

## Prerequisites

- Base URL: `http://localhost:8080`
- Authentication: JWT Bearer Token
- Content-Type: `application/json`

## 1. Authentication Flow

### 1.1 Register a New User

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john.doe",
    "email": "john@example.com",
    "password": "SecurePass123!",
    "firstName": "John",
    "lastName": "Doe",
    "role": "RECEPTIONIST"
  }'
```

### 1.2 Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john.doe",
    "password": "SecurePass123!"
  }'
```

**Response:**
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwi...",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwi...",
    "expiresIn": 86400000
  },
  "timestamp": "2024-08-30T10:00:00"
}
```

### 1.3 Refresh Token

```bash
curl -X POST http://localhost:8080/api/auth/refresh \
  -H "Authorization: Bearer <refresh_token>"
```

### 1.4 Get Current User

```bash
curl -X GET http://localhost:8080/api/auth/me \
  -H "Authorization: Bearer <token>"
```

## 2. Guest Management

### 2.1 Create a New Guest

```bash
curl -X POST http://localhost:8080/api/guests \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jane",
    "lastName": "Smith",
    "email": "jane.smith@example.com",
    "phone": "+1-555-0100",
    "address": "456 Maple Ave",
    "city": "Los Angeles",
    "state": "CA",
    "zipCode": "90001",
    "country": "USA",
    "idType": "PASSPORT",
    "idNumber": "US123456789"
  }'
```

### 2.2 Retrieve Guest Details

```bash
curl -X GET http://localhost:8080/api/guests/1 \
  -H "Authorization: Bearer <token>"
```

### 2.3 List All Guests

```bash
curl -X GET http://localhost:8080/api/guests \
  -H "Authorization: Bearer <token>"
```

### 2.4 Search Guests by Name

```bash
curl -X GET "http://localhost:8080/api/guests/search/Smith" \
  -H "Authorization: Bearer <token>"
```

### 2.5 Update Guest Information

```bash
curl -X PUT http://localhost:8080/api/guests/1 \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jane",
    "lastName": "Smith",
    "email": "jane.smith.new@example.com",
    "phone": "+1-555-0101"
  }'
```

### 2.6 Delete Guest

```bash
curl -X DELETE http://localhost:8080/api/guests/1 \
  -H "Authorization: Bearer <token>"
```

## 3. Room Management

### 3.1 Create a Room

```bash
curl -X POST http://localhost:8080/api/rooms \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "roomNumber": "301",
    "roomType": "DELUXE",
    "capacity": 3,
    "pricePerNight": 250.00,
    "floor": 3,
    "status": "AVAILABLE",
    "propertyId": 1
  }'
```

### 3.2 Get All Rooms

```bash
curl -X GET http://localhost:8080/api/rooms \
  -H "Authorization: Bearer <token>"
```

### 3.3 Get Available Rooms for a Property

```bash
curl -X GET http://localhost:8080/api/rooms/available/1 \
  -H "Authorization: Bearer <token>"
```

### 3.4 Update Room Status

```bash
curl -X PATCH "http://localhost:8080/api/rooms/1/status?status=OCCUPIED" \
  -H "Authorization: Bearer <token>"
```

**Available Status Values:**
- AVAILABLE
- OCCUPIED
- MAINTENANCE
- CLEANING

### 3.5 Update Room Details

```bash
curl -X PUT http://localhost:8080/api/rooms/1 \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "roomNumber": "301",
    "roomType": "DELUXE_SUITE",
    "capacity": 4,
    "pricePerNight": 300.00
  }'
```

## 4. Reservation Management

### 4.1 Create a Reservation

```bash
curl -X POST http://localhost:8080/api/reservations \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "guestId": 1,
    "roomId": 1,
    "checkInDate": "2024-09-01",
    "checkOutDate": "2024-09-05",
    "numberOfGuests": 2,
    "status": "CONFIRMED"
  }'
```

### 4.2 View Reservation

```bash
curl -X GET http://localhost:8080/api/reservations/1 \
  -H "Authorization: Bearer <token>"
```

### 4.3 View All Reservations

```bash
curl -X GET http://localhost:8080/api/reservations \
  -H "Authorization: Bearer <token>"
```

### 4.4 View Guest's Reservations

```bash
curl -X GET http://localhost:8080/api/reservations/guest/1 \
  -H "Authorization: Bearer <token>"
```

### 4.5 Check-in Guest

```bash
curl -X POST http://localhost:8080/api/reservations/1/check-in \
  -H "Authorization: Bearer <token>"
```

### 4.6 Check-out Guest

```bash
curl -X POST http://localhost:8080/api/reservations/1/check-out \
  -H "Authorization: Bearer <token>"
```

### 4.7 Cancel Reservation

```bash
curl -X POST http://localhost:8080/api/reservations/1/cancel \
  -H "Authorization: Bearer <token>"
```

## 5. Billing & Folio Management

### 5.1 View Folio

```bash
curl -X GET http://localhost:8080/api/folios/1 \
  -H "Authorization: Bearer <token>"
```

### 5.2 View Guest's Folios

```bash
curl -X GET http://localhost:8080/api/folios/guest/1 \
  -H "Authorization: Bearer <token>"
```

### 5.3 Add Charge to Folio

```bash
curl -X POST http://localhost:8080/api/folios/1/charges \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "description": "Room Service - Breakfast",
    "amount": 35.50,
    "chargeType": "SERVICE"
  }'
```

**Available Charge Types:**
- ROOM (Room charges)
- SERVICE (Service charges)
- MISC (Miscellaneous charges)
- TELEPHONE (Telephone charges)
- PARKING (Parking charges)

### 5.4 View Folio Charges

```bash
curl -X GET http://localhost:8080/api/folios/1/charges \
  -H "Authorization: Bearer <token>"
```

### 5.5 Process Payment

```bash
curl -X POST http://localhost:8080/api/folios/1/payments \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 500.00,
    "paymentMethod": "CREDIT_CARD",
    "referenceNumber": "CC-20240901-001"
  }'
```

**Available Payment Methods:**
- CASH
- CREDIT_CARD
- DEBIT_CARD
- BANK_TRANSFER
- CHECK

### 5.6 View Payments

```bash
curl -X GET http://localhost:8080/api/folios/1/payments \
  -H "Authorization: Bearer <token>"
```

### 5.7 Close Folio

```bash
curl -X POST http://localhost:8080/api/folios/1/close \
  -H "Authorization: Bearer <token>"
```

## 6. Complete Workflow Example

Here's a complete workflow from guest registration to checkout:

### Step 1: Create a Guest
```bash
GUEST_ID=$(curl -s -X POST http://localhost:8080/api/guests \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Traveler",
    "email": "john.traveler@example.com",
    "phone": "+1-555-1234"
  }' | jq -r '.data.id')

echo "Created guest: $GUEST_ID"
```

### Step 2: Create a Reservation
```bash
RESERVATION_ID=$(curl -s -X POST http://localhost:8080/api/reservations \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d "{
    \"guestId\": $GUEST_ID,
    \"roomId\": 1,
    \"checkInDate\": \"2024-09-01\",
    \"checkOutDate\": \"2024-09-03\",
    \"numberOfGuests\": 1,
    \"status\": \"CONFIRMED\"
  }" | jq -r '.data.id')

echo "Created reservation: $RESERVATION_ID"
```

### Step 3: Check-in Guest
```bash
curl -s -X POST http://localhost:8080/api/reservations/$RESERVATION_ID/check-in \
  -H "Authorization: Bearer $TOKEN" | jq '.'
```

### Step 4: Add Charges
```bash
curl -s -X POST http://localhost:8080/api/folios/1/charges \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "description": "Room charges",
    "amount": 300.00,
    "chargeType": "ROOM"
  }' | jq '.'
```

### Step 5: Process Payment
```bash
curl -s -X POST http://localhost:8080/api/folios/1/payments \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 300.00,
    "paymentMethod": "CREDIT_CARD",
    "referenceNumber": "CC-001"
  }' | jq '.'
```

### Step 6: Check-out Guest
```bash
curl -s -X POST http://localhost:8080/api/reservations/$RESERVATION_ID/check-out \
  -H "Authorization: Bearer $TOKEN" | jq '.'
```

### Step 7: Close Folio
```bash
curl -s -X POST http://localhost:8080/api/folios/1/close \
  -H "Authorization: Bearer $TOKEN" | jq '.'
```

## 7. Error Handling

### Example: Unauthorized Request
```bash
curl -X GET http://localhost:8080/api/guests/1
```

**Response (401):**
```json
{
  "success": false,
  "message": "Unauthorized",
  "timestamp": "2024-08-30T10:00:00"
}
```

### Example: Not Found
```bash
curl -X GET http://localhost:8080/api/guests/999 \
  -H "Authorization: Bearer <token>"
```

**Response (404):**
```json
{
  "success": false,
  "message": "Guest not found with id: 999",
  "timestamp": "2024-08-30T10:00:00",
  "path": "/api/guests/999"
}
```

### Example: Validation Error
```bash
curl -X POST http://localhost:8080/api/guests \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"firstName": ""}'
```

**Response (400):**
```json
{
  "success": false,
  "message": "Validation error",
  "data": {
    "firstName": "First name is required",
    "lastName": "Last name is required"
  },
  "timestamp": "2024-08-30T10:00:00",
  "path": "/api/guests"
}
```

## 8. Using Postman

### Import Collection

1. Open Postman
2. Click "Import"
3. Choose "Link"
4. Paste: `http://localhost:8080/v3/api-docs`
5. Click "Continue" and "Import"

### Set Environment Variables

1. Create a new environment
2. Add variables:
   - `base_url`: `http://localhost:8080`
   - `token`: (will be set after login)

### Login Script

Add to "Tests" tab of login request:
```javascript
if (pm.response.code === 200) {
    var jsonData = pm.response.json();
    pm.environment.set("token", jsonData.data.token);
}
```

## 9. Performance Tips

1. **Batch Operations**: Avoid multiple requests when possible
2. **Caching**: Cache guest and room data locally when appropriate
3. **Pagination**: Use query parameters for large datasets
4. **Connection Pooling**: Configure proper connection pool settings

## 10. Security Best Practices

1. **Store Tokens Securely**: Never store tokens in plain text
2. **Use HTTPS**: Always use HTTPS in production
3. **Refresh Tokens**: Refresh tokens before expiration
4. **Validate Input**: Always validate input on the client side
5. **Rate Limiting**: Implement rate limiting for API requests

---

For more information, see [README.md](README.md)
