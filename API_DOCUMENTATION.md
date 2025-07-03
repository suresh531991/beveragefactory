# API Documentation - Beverage Order Pricing System

## Overview

This is a Spring Boot application that provides a beverage ordering and pricing service. The system supports various beverages (Coffee, Chai, Banana Smoothie, Strawberry Shake, Mojito) with customizable ingredients and dynamic pricing based on inclusions and exclusions.

## Project Information

- **Framework**: Spring Boot 2.2.5
- **Java Version**: 1.8
- **Build Tool**: Gradle
- **Package**: `com.example.demo`

---

## REST API Endpoints

### ServiceApi Class

The `ServiceApi` class provides REST endpoints for the beverage ordering system.

#### POST /order

**Description**: Calculates the total price for an array of beverage orders with optional ingredient exclusions.

**Endpoint**: `POST /order`

**Request Body**: `String[]` - Array of order strings

**Response**: `Double` - Total price of all orders

**Content-Type**: `application/json`

#### Example Usage

```bash
curl -X POST http://localhost:8080/order \
  -H "Content-Type: application/json" \
  -d '["Chai, -sugar", "Coffee", "Mojito, -mint"]'
```

**Response**: `16.0`

#### Order Format

Orders follow the pattern: `"BeverageName, -excludedIngredient1, -excludedIngredient2"`

- Base beverage name (case-insensitive)
- Optional exclusions prefixed with `"-"`
- Multiple exclusions separated by `", -"`

---

## Utility Classes

### OrderPriceUitl Class

**Package**: `com.example.demo`  
**Annotation**: `@Component`

Core utility class that handles beverage menu management and price calculations.

#### Public Methods

##### `orderPrice(String[] orders)`

**Description**: Calculates the total price for an array of beverage orders.

**Signature**: `public static Double orderPrice(String[] orders)`

**Parameters**:
- `orders` (String[]): Array of order strings with optional exclusions

**Returns**: `Double` - Total calculated price

**Example**:
```java
String[] orders = {"Chai, -sugar", "Coffee, -milk", "Mojito"};
Double totalPrice = OrderPriceUitl.orderPrice(orders);
// Returns: 15.0
```

##### `getMenuItems()`

**Description**: Retrieves the complete menu with ingredients for each beverage.

**Signature**: `public Map<String, List<String>> getMenuItems()`

**Returns**: `Map<String, List<String>>` - Menu items with their ingredients

**Example**:
```java
OrderPriceUitl util = new OrderPriceUitl();
Map<String, List<String>> menu = util.getMenuItems();
// Returns: {coffee=[coffee, milk, sugar, water], chai=[tea, milk, sugar, water], ...}
```

##### `setMenuItems(Map<String, List<String>> menuItems)`

**Description**: Sets the menu items configuration.

**Signature**: `public void setMenuItems(Map<String, List<String>> menuItems)`

**Parameters**:
- `menuItems` (Map<String, List<String>>): New menu configuration

##### `getMenuPrice()`

**Description**: Retrieves the pricing information for all menu items and ingredients.

**Signature**: `public Map<String, Double> getMenuPrice()`

**Returns**: `Map<String, Double>` - Price mapping for beverages and ingredients

**Example**:
```java
OrderPriceUitl util = new OrderPriceUitl();
Map<String, Double> prices = util.getMenuPrice();
// Returns: {coffee=5.0, chai=4.0, milk=1.0, sugar=0.5, ...}
```

##### `setMenuPrice(Map<String, Double> menuPrice)`

**Description**: Sets the pricing configuration.

**Signature**: `public void setMenuPrice(Map<String, Double> menuPrice)`

**Parameters**:
- `menuPrice` (Map<String, Double>): New pricing configuration

##### `populateItems()`

**Description**: Initializes the menu with default beverages, ingredients, and prices.

**Signature**: `public void populateItems()`

**Called automatically in constructor**

---

## Application Entry Point

### DemoApplication Class

**Package**: `com.example.demo`  
**Annotation**: `@SpringBootApplication`

Main application class that bootstraps the Spring Boot application.

#### Methods

##### `main(String[] args)`

**Description**: Application entry point that starts the Spring Boot application and demonstrates order processing.

**Signature**: `public static void main(String[] args)`

**Parameters**:
- `args` (String[]): Command line arguments

**Example**:
```java
// Starts Spring Boot application
SpringApplication.run(DemoApplication.class, args);

// Demo order processing
String[] orders = {"Chai, -sugar,", "Chai", "Coffee, -milk"};
System.out.println("Price " + OrderPriceUitl.orderPrice(orders));
```

---

## Menu Configuration

### Available Beverages

| Beverage | Base Price | Default Ingredients |
|----------|------------|-------------------|
| Coffee | $5.00 | coffee, milk, sugar, water |
| Chai | $4.00 | tea, milk, sugar, water |
| Banana Smoothie | $6.00 | banana, milk, sugar, water |
| Strawberry Shake | $7.00 | strawberries, milk, sugar, water |
| Mojito | $7.50 | lemon, sugar, water, soda, mint |

### Ingredient Prices

| Ingredient | Price |
|------------|-------|
| milk | $1.00 |
| sugar | $0.50 |
| water | $0.50 |
| tea | $1.00 |
| mint | $0.50 |
| soda | $0.50 |

---

## Usage Examples

### Basic Order

```java
String[] orders = {"Chai"};
Double price = OrderPriceUitl.orderPrice(orders);
// Result: 4.0
```

### Order with Exclusions

```java
String[] orders = {"Chai, -sugar"};
Double price = OrderPriceUitl.orderPrice(orders);
// Result: 3.5 (4.0 - 0.5)
```

### Multiple Orders

```java
String[] orders = {"Chai, -sugar", "Chai", "Coffee, -milk"};
Double price = OrderPriceUitl.orderPrice(orders);
// Result: 11.5 (3.5 + 4.0 + 4.0)
```

### Complex Exclusions

```java
String[] orders = {"Coffee, -milk, -sugar"};
Double price = OrderPriceUitl.orderPrice(orders);
// Result: 3.5 (5.0 - 1.0 - 0.5)
```

---

## Error Handling

### Edge Cases

1. **Null Orders**: Returns 0.0 if orders array is null
2. **Empty Orders**: Returns 0.0 if orders array is empty
3. **All Ingredients Excluded**: Returns 0.0 if all ingredients are excluded from a beverage
4. **Case Insensitive**: Beverage names are converted to lowercase for processing

### Testing Examples

From `DemoApplicationTests.java`:

```java
// Valid order calculation
String[] orders = {"Chai, -sugar", "Chai", "Coffee, -milk"};
Assert.isTrue(OrderPriceUitl.orderPrice(orders) == 11.5);

// Complete exclusion scenario
String[] orders = {"Chai, -Tea, -milk, -sugar, -water"};
Assert.isTrue(OrderPriceUitl.orderPrice(orders) == 0.0);
```

---

## API Response Examples

### Successful Response

**Request**:
```json
["Chai", "Coffee, -milk"]
```

**Response**: `9.0`

### Multiple Orders Response

**Request**:
```json
["Chai, -sugar", "Mojito, -mint", "Coffee"]
```

**Response**: `15.5`

---

## Running the Application

1. **Start the application**:
   ```bash
   ./gradlew bootRun
   ```

2. **Test the API**:
   ```bash
   curl -X POST http://localhost:8080/order \
     -H "Content-Type: application/json" \
     -d '["Chai", "Coffee"]'
   ```

3. **Run tests**:
   ```bash
   ./gradlew test
   ```

---

## Integration Notes

- The application uses Spring Boot's dependency injection
- `OrderPriceUitl` is registered as a Spring `@Component`
- The REST controller automatically serializes responses to JSON
- All price calculations are done server-side for security

---

## Development Notes

- Menu items and prices are currently hardcoded in `populateItems()`
- The application supports dynamic menu modification through setters
- Order parsing handles malformed input gracefully
- Case-insensitive beverage matching is implemented