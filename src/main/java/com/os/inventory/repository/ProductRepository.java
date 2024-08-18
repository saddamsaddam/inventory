package com.os.inventory.repository;

import com.os.inventory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("productRepository")
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(long id);
    Product findByProductId (String productId);
    List<Product> findAllByProductId(String productId);
    List<Product> findAllByName(String productName);
    List<Product> findAllByProductIdAndName(String productId, String productName);
    List<Product> findAll();
    @Query(value = "SELECT * FROM product WHERE available_unit > 0", nativeQuery = true)
    List<Product> findDistinctProductList();

}
