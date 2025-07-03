# Expense Management System

A Spring Boot REST API application for managing personal/business expenses with database storage.

## Features

- ✅ Add new expenses
- ✅ View all expenses
- ✅ Update existing expenses
- ✅ Delete expenses
- ✅ Search expenses by description
- ✅ Filter expenses by category
- ✅ Calculate total expenses
- ✅ Get expense summaries
- ✅ H2 in-memory database for data storage
- ✅ Input validation
- ✅ Comprehensive error handling

## Technology Stack

- **Framework**: Spring Boot 2.2.5
- **Database**: H2 In-Memory Database
- **ORM**: Spring Data JPA/Hibernate
- **Build Tool**: Gradle
- **Java Version**: 1.8

## Project Structure

```
src/main/java/com/example/demo/
├── DemoApplication.java          # Main application class
├── model/
│   └── Expense.java             # Expense entity
├── repository/
│   └── ExpenseRepository.java   # Data access layer
├── service/
│   └── ExpenseService.java      # Business logic layer
└── controller/
    └── ExpenseController.java   # REST API endpoints
```

## Getting Started

### Prerequisites

- Java 8 or higher
- Gradle

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application:
   ```bash
   ./gradlew bootRun
   ```

4. The application will start on `http://localhost:8080`

### Access Points

- **API Base URL**: `http://localhost:8080/api/expenses`
- **H2 Database Console**: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:expensedb`
  - Username: `sa`
  - Password: (leave empty)

## API Endpoints

### 1. Create Expense
- **URL**: `POST /api/expenses`
- **Description**: Add a new expense
- **Request Body**:
```json
{
  "description": "Grocery Shopping",
  "amount": 85.50,
  "category": "Food",
  "expenseDate": "2024-01-15T10:30:00",
  "notes": "Weekly grocery shopping"
}
```
- **Response**: Created expense with ID and timestamps

### 2. Get All Expenses
- **URL**: `GET /api/expenses`
- **Description**: Retrieve all expenses (ordered by date, most recent first)
- **Response**: List of all expenses with count

### 3. Get Expense by ID
- **URL**: `GET /api/expenses/{id}`
- **Description**: Retrieve a specific expense by ID
- **Response**: Single expense details

### 4. Update Expense
- **URL**: `PUT /api/expenses/{id}`
- **Description**: Update an existing expense
- **Request Body**: Same as create expense
- **Response**: Updated expense details

### 5. Delete Expense
- **URL**: `DELETE /api/expenses/{id}`
- **Description**: Delete an expense by ID
- **Response**: Success/failure message

### 6. Get Expenses by Category
- **URL**: `GET /api/expenses/category/{category}`
- **Description**: Retrieve all expenses for a specific category
- **Example**: `GET /api/expenses/category/Food`
- **Response**: List of expenses in the specified category

### 7. Search Expenses
- **URL**: `GET /api/expenses/search?keyword={keyword}`
- **Description**: Search expenses by description (case-insensitive)
- **Example**: `GET /api/expenses/search?keyword=grocery`
- **Response**: List of matching expenses

### 8. Get Total Expenses
- **URL**: `GET /api/expenses/total`
- **Description**: Calculate total amount of all expenses
- **Response**: Total expense amount

### 9. Get Total by Category
- **URL**: `GET /api/expenses/total/category/{category}`
- **Description**: Calculate total expenses for a specific category
- **Example**: `GET /api/expenses/total/category/Food`
- **Response**: Total amount for the category

## Sample API Usage

### Creating an Expense
```bash
curl -X POST http://localhost:8080/api/expenses \
  -H "Content-Type: application/json" \
  -d '{
    "description": "Coffee",
    "amount": 4.50,
    "category": "Food"
  }'
```

### Getting All Expenses
```bash
curl -X GET http://localhost:8080/api/expenses
```

### Searching Expenses
```bash
curl -X GET "http://localhost:8080/api/expenses/search?keyword=coffee"
```

## Data Model

### Expense Entity
```java
{
  "id": Long,                    // Auto-generated ID
  "description": String,         // Required - Expense description
  "amount": Double,             // Required - Expense amount (positive)
  "category": String,           // Required - Expense category
  "createdDate": LocalDateTime, // Auto-generated creation timestamp
  "expenseDate": LocalDateTime, // Expense date (defaults to now)
  "notes": String              // Optional - Additional notes
}
```

## Validation Rules

- **Description**: Required, cannot be blank
- **Amount**: Required, must be positive number
- **Category**: Required, cannot be blank
- **Expense Date**: Defaults to current date/time if not provided

## Response Format

All API responses follow this structure:
```json
{
  "success": boolean,
  "message": "string",
  "data": object,      // Present for successful data operations
  "count": number      // Present for list operations
}
```

## Error Handling

The API provides comprehensive error handling with appropriate HTTP status codes:
- `200 OK` - Successful operation
- `201 Created` - Resource created successfully
- `400 Bad Request` - Invalid input/validation errors
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server errors

## Database Schema

The application automatically creates the following table:

```sql
CREATE TABLE expenses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    amount DOUBLE NOT NULL,
    category VARCHAR(255) NOT NULL,
    created_date TIMESTAMP,
    expense_date TIMESTAMP,
    notes VARCHAR(255)
);
```

## Advanced Repository Features

The `ExpenseRepository` includes advanced query methods:
- Find by category (case-insensitive)
- Find by date range
- Find by amount range
- Search by description keywords
- Custom aggregation queries
- Monthly expense summaries

## Development Features

- **Auto-reload**: Spring Boot DevTools enabled for development
- **SQL Logging**: JPA queries are logged to console
- **H2 Console**: Available for database inspection
- **CORS**: Enabled for frontend integration

## Future Enhancements

Potential features for future versions:
- User authentication and authorization
- Multiple currency support
- Expense categories management
- File upload for receipts
- Reporting and analytics
- Export functionality (PDF, Excel)
- Recurring expenses
- Budget management

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is open source and available under the MIT License.