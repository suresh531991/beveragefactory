package com.example.demo.service;

import com.example.demo.model.Expense;
import com.example.demo.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    
    @Autowired
    private ExpenseRepository expenseRepository;
    
    // Create a new expense
    public Expense createExpense(Expense expense) {
        if (expense.getCreatedDate() == null) {
            expense.setCreatedDate(LocalDateTime.now());
        }
        if (expense.getExpenseDate() == null) {
            expense.setExpenseDate(LocalDateTime.now());
        }
        return expenseRepository.save(expense);
    }
    
    // Get all expenses
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }
    
    // Get all expenses ordered by date (most recent first)
    public List<Expense> getAllExpensesByDateDesc() {
        return expenseRepository.findAllByOrderByExpenseDateDesc();
    }
    
    // Get expense by ID
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }
    
    // Update an existing expense
    public Expense updateExpense(Long id, Expense expenseDetails) {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if (optionalExpense.isPresent()) {
            Expense expense = optionalExpense.get();
            expense.setDescription(expenseDetails.getDescription());
            expense.setAmount(expenseDetails.getAmount());
            expense.setCategory(expenseDetails.getCategory());
            expense.setExpenseDate(expenseDetails.getExpenseDate());
            expense.setNotes(expenseDetails.getNotes());
            return expenseRepository.save(expense);
        }
        throw new RuntimeException("Expense not found with id: " + id);
    }
    
    // Delete an expense
    public void deleteExpense(Long id) {
        if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id);
        } else {
            throw new RuntimeException("Expense not found with id: " + id);
        }
    }
    
    // Get expenses by category
    public List<Expense> getExpensesByCategory(String category) {
        return expenseRepository.findByCategoryIgnoreCase(category);
    }
    
    // Get expenses by date range
    public List<Expense> getExpensesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return expenseRepository.findByExpenseDateBetween(startDate, endDate);
    }
    
    // Get expenses by amount range
    public List<Expense> getExpensesByAmountRange(Double minAmount, Double maxAmount) {
        return expenseRepository.findByAmountBetween(minAmount, maxAmount);
    }
    
    // Search expenses by description keyword
    public List<Expense> searchExpensesByDescription(String keyword) {
        return expenseRepository.findByDescriptionContainingIgnoreCase(keyword);
    }
    
    // Get total expense amount
    public Double getTotalExpenseAmount() {
        Double total = expenseRepository.getTotalExpenseAmount();
        return total != null ? total : 0.0;
    }
    
    // Get total expense amount by category
    public Double getTotalExpenseAmountByCategory(String category) {
        Double total = expenseRepository.getTotalExpenseAmountByCategory(category);
        return total != null ? total : 0.0;
    }
    
    // Get expenses by date range and category
    public List<Expense> getExpensesByDateRangeAndCategory(LocalDateTime startDate, LocalDateTime endDate, String category) {
        return expenseRepository.findExpensesByDateRangeAndCategory(startDate, endDate, category);
    }
    
    // Get monthly expense summary
    public List<Object[]> getMonthlyExpenseSummary(int year, int month) {
        return expenseRepository.getMonthlyExpenseSummary(year, month);
    }
    
    // Get expenses ordered by amount (highest first)
    public List<Expense> getExpensesByAmountDesc() {
        return expenseRepository.findAllByOrderByAmountDesc();
    }
}