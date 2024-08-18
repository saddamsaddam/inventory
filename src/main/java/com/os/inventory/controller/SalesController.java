package com.os.inventory.controller;

import com.os.inventory.common.BaseController;
import com.os.inventory.services.service.ProductService;
import com.os.inventory.services.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class SalesController extends BaseController {

    @Autowired
    private SalesService salesService;

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/api/searchproductbyproductidandname", method = RequestMethod.GET)
    @ResponseBody
    public String searchProductByProductIdAndName(@RequestParam Map<String, Object> parameters) {
        return productService.searchProductByProductIdAndName(parameters);
    }

    @GetMapping("/createsale")
    public String createSale(@RequestParam Map<String, Object> parameters, Model model) {
        return "sales/listproductforsale";
    }

    @RequestMapping(value = "/savesale", method = RequestMethod.POST)
    @ResponseBody
    public String saveSale(@RequestParam Map<String, Object> parameters) { //TODO: can replace map by model
        return salesService.saveSales(parameters);
    }

    @GetMapping("/listsoldproduct")
    public String listProduct() {
        return "sales/listsoldproduct";
    }

    @RequestMapping(value = "/api/listsoldproduct", method = RequestMethod.GET)
    @ResponseBody
    public String soldProductList(@RequestParam Map<String, Object> parameters) {
        return salesService.listSales(parameters);
    }
}
