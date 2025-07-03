package com.example.demo.repository;

import com.example.demo.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
    // Find expenses by category
    List<Expense> findByCategory(String category);
    
    // Find expenses by category (case insensitive)
    List<Expense> findByCategoryIgnoreCase(String category);
    
    // Find expenses within a date range
    List<Expense> findByExpenseDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Find expenses by amount greater than
    List<Expense> findByAmountGreaterThan(Double amount);
    
    // Find expenses by amount less than
    List<Expense> findByAmountLessThan(Double amount);
    
    // Find expenses by amount between range
    List<Expense> findByAmountBetween(Double minAmount, Double maxAmount);
    
    // Find expenses by description containing keyword (case insensitive)
    List<Expense> findByDescriptionContainingIgnoreCase(String keyword);
    
    // Custom query to get total expense amount
    @Query("SELECT SUM(e.amount) FROM Expense e")
    Double getTotalExpenseAmount();
    
    // Custom query to get total expense amount by category
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.category = :category")
    Double getTotalExpenseAmountByCategory(@Param("category") String category);
    
    // Custom query to get expenses by date range and category
    @Query("SELECT e FROM Expense e WHERE e.expenseDate BETWEEN :startDate AND :endDate AND e.category = :category")
    List<Expense> findExpensesByDateRangeAndCategory(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("category") String category
    );
    
    // Find all expenses ordered by expense date descending (most recent first)
    List<Expense> findAllByOrderByExpenseDateDesc();
    
    // Find all expenses ordered by amount descending (highest amount first)
    List<Expense> findAllByOrderByAmountDesc();
    
    // Custom query to get monthly expense summary
    @Query("SELECT e.category, SUM(e.amount) FROM Expense e WHERE YEAR(e.expenseDate) = :year AND MONTH(e.expenseDate) = :month GROUP BY e.category")
    List<Object[]> getMonthlyExpenseSummary(@Param("year") int year, @Param("month") int month);
}