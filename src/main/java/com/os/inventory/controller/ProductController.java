package com.os.inventory.controller;

import com.os.inventory.common.BaseController;
import com.os.inventory.entity.Product;
import com.os.inventory.entity.Supplier;
import com.os.inventory.entity.Transaction;
import com.os.inventory.services.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    @GetMapping("/createproduct")
    public String createProduct() {
        return "product/createproduct";
    }

    @RequestMapping(value = "/saveproduct", method = RequestMethod.POST)
    @ResponseBody
    public String saveProduct(@RequestParam Map<String, Object> parameters) { //TODO: can replace map by model
        return productService.saveProduct(parameters);
    }

    @GetMapping("/listpurchaseproduct")
    public String listPurchaseProduct() {
        return "product/listpurchaseproductd";
    }

    @RequestMapping(value = "/api/listpurchaseproduct", method = RequestMethod.GET)
    @ResponseBody
    public String purchaseProductList(@RequestParam Map<String, Object> parameters) {
        return productService.listPurchaseProduct(parameters);
    }

    @GetMapping("/listavailableproduct")
    public String listAvailableProduct() {
        return "product/listavailableproduct";
    }

    @RequestMapping(value = "/api/listavailableproduct", method = RequestMethod.GET)
    @ResponseBody
    public String availableProductList(@RequestParam Map<String, Object> parameters) {
        return productService.listAvailableProduct(parameters);
    }

    @RequestMapping(value = "/editproduct", method = {RequestMethod.GET, RequestMethod.POST})
    public String productEdit(@RequestParam Map<String, Object> parameters, Model model) {
        long id = Long.parseLong((String) parameters.get("id"));
        Transaction transaction = productService.findTransactionById(id);
        model.addAttribute("product", transaction);
        return "product/editproduct";
    }

    @RequestMapping(value = "/deleteproduct", method = RequestMethod.POST)
    public @ResponseBody HashMap deleteTransaction(@RequestParam Map<String, Object> parameters) {
        long id = Long.parseLong((String) parameters.get("id"));
        Transaction transaction = productService.findTransactionById(id);
        if (transaction == null) {
            return null;
        }

        productService.deleteTransaction(transaction);
        HashMap<String, String> map = new HashMap<>();
        map.put("responseText", "Successfully deleted");
        return map;
    }

    @RequestMapping(value = "/api/viewproduct", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Transaction> viewProduct(@RequestParam Map<String, Object> parameters) {
        long id = Long.parseLong((String) parameters.get("id"));
        Transaction transaction = productService.findTransactionById(id);
        if (transaction == null) {
            return null;
        }

        Supplier supplier = productService.findSupplier(transaction.getSupplierId());
        if (supplier != null) {
            transaction.setSupplierName(supplier.getName());
        }

        ResponseEntity<Transaction> productResponseEntity = new ResponseEntity<>(transaction, HttpStatus.OK);
        return productResponseEntity;
    }

    @GetMapping("/api/distinctproductlist")
    @ResponseBody
    public List<Product> listDistinctProduct() {
        return productService.findAll();
    }

//    @GetMapping("/api/getproducbyproductid")
//    @ResponseBody
//    public Product listDistinctBrand(@RequestParam Map<String, Object> parameters) {
//        String productId = (String) parameters.get("productId");
//        Product product = productService.findProductByProductId(productId);
//        int availableUnit = product.getUnit();
//        Sales sales = productService.findSalesByProductId(productId);
//        if (sales != null) {
//            availableUnit = product.getUnit() - sales.getUnit();
//        }
//
//        product.setAvailableUnit(availableUnit);
//        return product;
//    }


}
