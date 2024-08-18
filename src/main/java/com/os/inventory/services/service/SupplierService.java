package com.os.inventory.services.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.os.inventory.entity.Product;
import com.os.inventory.entity.Supplier;
import com.os.inventory.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SupplierService {
    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    private ObjectMapper mapperObj;

    @Transactional
    public String saveSupplier(Map<String, Object> parameters) {
        Supplier supplier = null;
        String output = "";

        try {
            if (parameters.get("id") != null) {
                long id = Long.parseLong((String) parameters.get("id"));
                supplier = supplierRepository.findById(id);
                parameters.put("idAvailable", "true");
            } else {
                supplier = new Supplier();
            }

            supplier.setName((String) parameters.get("supplier_name"));
            supplier.setAddress((String) parameters.get("address"));
            supplier.setEmail((String) parameters.get("email"));
            supplier.setPhone((String) parameters.get("phone"));

            if (parameters.get("idAvailable") == "true") {
                supplier.setUpdatedBy((String) SecurityContextHolder.getContext().getAuthentication().getName());
                supplier.setUpdatedOn(new Date());
            } else {
                supplier.setCreatedBy((String) SecurityContextHolder.getContext().getAuthentication().getName());
                supplier.setCreatedOn(new Date());
            }

            supplierRepository.save(supplier);
            parameters.put("isError", false); //Not working if throw exception

        } catch (Exception ex) {
            parameters.put("isError", true); //Not working if throw exception
        }

        try {
            output = mapperObj.writeValueAsString(parameters);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return output; //If throw open, return from try block

    }

    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    public String listSupplier(Map<String, Object> parameters) {
        String output = "";
        List<Supplier> supplierList = supplierRepository.findAll();
        try {
            parameters.put("queryList", supplierList);
            output = mapperObj.writeValueAsString(parameters);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return output; //If throw open, return from try block
    }

    public Supplier findById(long id) {
        return supplierRepository.findById(id);
    }

    public void delete(Supplier supplier) {
        supplierRepository.delete(supplier);
    }
}
