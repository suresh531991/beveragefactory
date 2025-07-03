package com.example.demo.controller;

import com.example.demo.model.Expense;
import com.example.demo.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*")
public class ExpenseController {
    
    @Autowired
    private ExpenseService expenseService;
    
    // POST /api/expenses - Create a new expense
    @PostMapping
    public ResponseEntity<Map<String, Object>> createExpense(@Valid @RequestBody Expense expense) {
        try {
            Expense createdExpense = expenseService.createExpense(expense);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Expense created successfully");
            response.put("data", createdExpense);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error creating expense: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    
    // GET /api/expenses - Get all expenses
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllExpenses() {
        try {
            List<Expense> expenses = expenseService.getAllExpensesByDateDesc();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Expenses retrieved successfully");
            response.put("data", expenses);
            response.put("count", expenses.size());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error retrieving expenses: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // GET /api/expenses/{id} - Get expense by ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getExpenseById(@PathVariable Long id) {
        try {
            Optional<Expense> expense = expenseService.getExpenseById(id);
            Map<String, Object> response = new HashMap<>();
            if (expense.isPresent()) {
                response.put("success", true);
                response.put("message", "Expense found");
                response.put("data", expense.get());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("success", false);
                response.put("message", "Expense not found with id: " + id);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error retrieving expense: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // PUT /api/expenses/{id} - Update an expense
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateExpense(@PathVariable Long id, @Valid @RequestBody Expense expenseDetails) {
        try {
            Expense updatedExpense = expenseService.updateExpense(id, expenseDetails);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Expense updated successfully");
            response.put("data", updatedExpense);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error updating expense: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    
    // DELETE /api/expenses/{id} - Delete an expense
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteExpense(@PathVariable Long id) {
        try {
            expenseService.deleteExpense(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Expense deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error deleting expense: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // GET /api/expenses/category/{category} - Get expenses by category
    @GetMapping("/category/{category}")
    public ResponseEntity<Map<String, Object>> getExpensesByCategory(@PathVariable String category) {
        try {
            List<Expense> expenses = expenseService.getExpensesByCategory(category);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Expenses retrieved for category: " + category);
            response.put("data", expenses);
            response.put("count", expenses.size());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error retrieving expenses by category: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // GET /api/expenses/search?keyword={keyword} - Search expenses by description
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchExpenses(@RequestParam String keyword) {
        try {
            List<Expense> expenses = expenseService.searchExpensesByDescription(keyword);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Search completed for keyword: " + keyword);
            response.put("data", expenses);
            response.put("count", expenses.size());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error searching expenses: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // GET /api/expenses/total - Get total expense amount
    @GetMapping("/total")
    public ResponseEntity<Map<String, Object>> getTotalExpenses() {
        try {
            Double total = expenseService.getTotalExpenseAmount();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Total expense amount retrieved");
            response.put("totalAmount", total);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error calculating total expenses: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // GET /api/expenses/total/category/{category} - Get total expense amount by category
    @GetMapping("/total/category/{category}")
    public ResponseEntity<Map<String, Object>> getTotalExpensesByCategory(@PathVariable String category) {
        try {
            Double total = expenseService.getTotalExpenseAmountByCategory(category);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Total expense amount for category: " + category);
            response.put("category", category);
            response.put("totalAmount", total);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error calculating total expenses by category: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}