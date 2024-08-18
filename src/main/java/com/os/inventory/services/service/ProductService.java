package com.os.inventory.services.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.os.inventory.entity.Product;
import com.os.inventory.entity.Sales;
import com.os.inventory.entity.Supplier;
import com.os.inventory.entity.Transaction;
import com.os.inventory.repository.ProductRepository;
import com.os.inventory.repository.SalesRepository;
import com.os.inventory.repository.SupplierRepository;
import com.os.inventory.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    SalesRepository salesRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    private ObjectMapper mapperObj;

    @Transactional
    public String saveProduct(Map<String, Object> parameters) {
        Product product = null;
        Transaction transaction = null;
        String output = "";
        String productId = (String) parameters.get("product_id");
        product = productRepository.findByProductId(productId);
        int currUnit = (Integer.parseInt((String) parameters.get("unit")));
        int prevUnit = 0;

        try {
            if (parameters.get("id") != null) {
                long id = Long.parseLong((String) parameters.get("id"));
                transaction = transactionRepository.findById(id);
                prevUnit = transaction.getUnit();
                parameters.put("idAvailable", "true");
            } else {
                transaction = new Transaction();
                if (product == null) {
                    product = new Product();
                    product.setProductId((String) parameters.get("product_id"));
                    product.setName((String) parameters.get("product_name"));
                    product.setBrand((String) parameters.get("brand"));
                    product.setManufacturingDate((String) parameters.get("manufacturing_date"));
                    product.setColour((String) parameters.get("colour"));
                    product.setDescription((String) parameters.get("description"));
                    product.setUnit(Integer.parseInt((String) parameters.get("unit")));
                    product.setUnitPrice(Double.parseDouble((String) parameters.get("unit_price")));
                    product.setSupplierId(Integer.parseInt((String) parameters.get("sel_supplier")));
                    product.setAvailableUnit(currUnit);
                } else {
                    double unitPrice = (Double.parseDouble((String) parameters.get("unit_price")));
                    unitPrice = unitPrice > product.getUnitPrice() ? unitPrice : product.getUnitPrice();
                    product.setUnitPrice(unitPrice);
                    int availableUnit = product.getAvailableUnit() + currUnit;
                    product.setAvailableUnit(availableUnit);
                }

            }

            transaction.setProductId((String) parameters.get("product_id"));
            transaction.setName((String) parameters.get("product_name"));
            transaction.setBrand((String) parameters.get("brand"));
            transaction.setManufacturingDate((String) parameters.get("manufacturing_date"));
            transaction.setColour((String) parameters.get("colour"));
            transaction.setDescription((String) parameters.get("description"));
            transaction.setUnit(Integer.parseInt((String) parameters.get("unit")));
            transaction.setUnitPrice(Double.parseDouble((String) parameters.get("unit_price")));
            transaction.setSupplierId(Integer.parseInt((String) parameters.get("sel_supplier")));

            if (parameters.get("idAvailable") == "true") {
                int unitDiff = currUnit - prevUnit;
                int availableUnit = product.getAvailableUnit() + unitDiff;
                product.setAvailableUnit(availableUnit);
                double unitPrice = (Double.parseDouble((String) parameters.get("unit_price")));
                unitPrice = unitPrice > product.getUnitPrice() ? unitPrice : product.getUnitPrice();
                product.setUnitPrice(unitPrice);

                transaction.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
                transaction.setUpdatedOn(new Date());
                product.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
                product.setUpdatedOn(new Date());
            } else {
                product.setProductStatus("In Stock");
                transaction.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
                transaction.setCreatedOn(new Date());
                product.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
                product.setCreatedOn(new Date());
            }

            transactionRepository.save(transaction);
            productRepository.save(product);
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

    public String listPurchaseProduct(Map<String, Object> parameters) {
        String output = "";
        List<Transaction> transactionList = transactionRepository.findAll();
        for (Transaction item : transactionList) {
            Supplier supplier = supplierRepository.findById(item.getSupplierId());
            item.setSupplierName(supplier.getName());
        }
        try {
            parameters.put("queryList", transactionList);
            output = mapperObj.writeValueAsString(parameters);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return output; //If throw open, return from try block
    }

    public String listAvailableProduct(Map<String, Object> parameters) {
        String output = "";
        List<Product> productList = productRepository.findDistinctProductList();
        try {
            parameters.put("queryList", productList);
            output = mapperObj.writeValueAsString(parameters);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return output; //If throw open, return from try block
    }

    public String searchProductByProductIdAndName(Map<String, Object> parameters) {
        String output = "";
        try {
            String productId = (String) parameters.get("product_id");
            String productName = (String) parameters.get("product_name");
            List<Product> allProductList = new ArrayList<>();
            List<Product> availableProductList = new ArrayList<>();
            if (productName.trim().isEmpty() && !productId.trim().isEmpty()) {
                allProductList = productRepository.findAllByProductId(productId);
            } else if (productId.trim().isEmpty() && !productName.trim().isEmpty()) {
                allProductList = productRepository.findAllByName(productName);
            } else if (!productId.trim().isEmpty() && !productName.trim().isEmpty()) {
                allProductList = productRepository.findAllByProductIdAndName(productId, productName);
            }

            for (Product item : allProductList) {
                if (item.getAvailableUnit() > 0) {
                    availableProductList.add(item);
                }
            }

            parameters.put("queryList", availableProductList);
            output = mapperObj.writeValueAsString(parameters);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return output; //If throw open, return from try block
    }

    public Product findById(long id) {
        return productRepository.findById(id);
    }

    public Transaction findTransactionById(long id) {
        return transactionRepository.findById(id);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Supplier findSupplier(long id) {
        return supplierRepository.findById(id);
    }

    public List<Sales> findSalesByProductId(String productId) {
        return salesRepository.findAllByProductId(productId);
    }

    public void deleteTransaction(Transaction transaction) {
        transactionRepository.delete(transaction);
    }

}
