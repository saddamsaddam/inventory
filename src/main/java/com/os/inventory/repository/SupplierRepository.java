package com.os.inventory.repository;

import com.os.inventory.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("supplierRepository")
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Supplier findById(long id);
    List<Supplier> findAll();

}