package com.os.inventory.repository;

import com.os.inventory.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("salesRepository")
public interface SalesRepository extends JpaRepository<Sales, Long> {
    Sales findById(long id);

    List<Sales> findAllByProductId(String productId);

    List<Sales> findAll();

}