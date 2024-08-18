package com.os.inventory.services.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.os.inventory.entity.SalesProduct;
import com.os.inventory.entity.Product;
import com.os.inventory.entity.Sales;
import com.os.inventory.repository.ProductRepository;
import com.os.inventory.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class SalesService {
    @Autowired
    SalesRepository salesRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private ObjectMapper mapperObj;

    @Autowired
    ProductService productService;

    @Transactional
    public String saveSales(Map<String, Object> parameters) {
        Sales sales = null;
        String output = "";
        int availableUnit = 0;
        List<Sales> salesList = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonData = (String) parameters.get("product_info");
            SalesProduct[] productList = objectMapper.readValue(jsonData, SalesProduct[].class);
            for (SalesProduct item : productList) {
                long id = Long.parseLong(item.getId());
                Product product = productRepository.findById(id);

                sales = new Sales();
                sales.setProductId(product.getProductId());
                sales.setCustomerName((String) parameters.get("customer_name"));
                sales.setAddress((String) parameters.get("address"));
                sales.setEmail((String) parameters.get("email"));
                sales.setPhone((String) parameters.get("phone"));
                int sellingUint = Integer.parseInt(item.getSellingUnit());
                sales.setUnit(sellingUint);
                sales.setUnitPrice(Double.parseDouble(item.getSellingUnitPrice()));
                sales.setSellingDate((String) parameters.get("selling_date"));

                if (parameters.get("idAvailable") == "true") {
                    sales.setUpdatedBy((String) SecurityContextHolder.getContext().getAuthentication().getName());
                    sales.setUpdatedOn(new Date());
                } else {
                    sales.setCreatedBy((String) SecurityContextHolder.getContext().getAuthentication().getName());
                    sales.setCreatedOn(new Date());
                }

                salesRepository.save(sales);

                availableUnit = product.getAvailableUnit() - sellingUint;
                product.setAvailableUnit(availableUnit);
                product.setProductStatus("Sold");
                productRepository.save(product);
                parameters.put("isError", false); //Not working if throw exception
            }
        } catch (Exception ex) {
            parameters.put("isError", true); //Not working if throw exception
        }

        try {
            output = mapperObj.writeValueAsString(parameters);
        } catch (
                Exception ex) {
            System.out.println(ex);
        }

        return output; //If throw open, return from try block

    }

    public List<Sales> findAll() {
        return salesRepository.findAll();
    }

    public String listSales(Map<String, Object> parameters) {
        String output = "";
        List<Sales> salesList = salesRepository.findAll();
        for (Sales item : salesList) {
            Product product = productRepository.findByProductId(item.getProductId());
            item.setProductName(product.getName());
            item.setBrand(product.getBrand());
        }
        try {
            parameters.put("queryList", salesList);
            output = mapperObj.writeValueAsString(parameters);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return output; //If throw open, return from try block
    }
}