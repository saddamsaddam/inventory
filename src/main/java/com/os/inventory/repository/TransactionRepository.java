package com.os.inventory.repository;

import com.os.inventory.entity.Product;
import com.os.inventory.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("transactionRepository")
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findById(long id);
    List<Transaction> findByProductId (String productId);
    List<Transaction> findByProductIdAndSupplierIdAndUnitPrice (String productId, int supplierId, double unitPrice);
    List<Transaction> findAllByProductId(String productId);
    List<Transaction> findAllByName(String productName);
    List<Transaction> findAllByProductIdAndName(String productId, String productName);
    List<Transaction> findAll();
    @Query(value = "SELECT * FROM product WHERE available_unit > 0", nativeQuery = true)
    List<Transaction> findDistinctProductList();

}
