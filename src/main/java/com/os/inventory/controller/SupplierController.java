package com.os.inventory.controller;

import com.os.inventory.common.BaseController;
import com.os.inventory.entity.Product;
import com.os.inventory.entity.Supplier;
import com.os.inventory.services.service.SupplierService;
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
public class SupplierController extends BaseController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/createsupplier")
    public String createSupplier() {
        return "supplier/createsupplier";
    }

    @RequestMapping(value = "/savesupplier", method = RequestMethod.POST)
    @ResponseBody
    public String saveSupplier(@RequestParam Map<String, Object> parameters) { //TODO: can replace map by model
        return supplierService.saveSupplier(parameters);
    }

    @GetMapping("/api/listsupplier")
    @ResponseBody
    public List<Supplier> listSupplier() {
        return supplierService.findAll();
    }

    @GetMapping("/listsupplier")
    public String listProduct() {
        return "supplier/listpsupplier";
    }

    @RequestMapping(value = "/api/getsupplierlist", method = RequestMethod.GET)
    @ResponseBody
    public String supplierList(@RequestParam Map<String, Object> parameters) {
        return supplierService.listSupplier(parameters);
    }

    @RequestMapping(value = "/editsupplier", method = {RequestMethod.GET, RequestMethod.POST})
    public String supplierEdit(@RequestParam Map<String, Object> parameters, Model model) {
        long id = Long.parseLong((String) parameters.get("id"));
        Supplier supplier = supplierService.findById(id);
        model.addAttribute("supplier", supplier);
        return "supplier/editsupplier";
    }


    @RequestMapping(value = "/api/viewsupplier", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Supplier> viewSupplier(@RequestParam Map<String, Object> parameters) {
        long id = Long.parseLong((String) parameters.get("id"));
        Supplier supplier = supplierService.findById(id);
        ResponseEntity<Supplier> supplierResponseEntity = new ResponseEntity<>(supplier, HttpStatus.OK);
        return supplierResponseEntity;
    }

    @RequestMapping(value = "/deletesupplier", method = RequestMethod.POST)
    public @ResponseBody HashMap deleteCity(@RequestParam Map<String, Object> parameters) {
        long id = Long.parseLong((String) parameters.get("id"));
        Supplier supplier = supplierService.findById(id);
        if (supplier == null) {
            return null;
        }

        supplierService.delete(supplier);
        HashMap<String, String> map = new HashMap<>();
        map.put("responseText", "Successfully deleted");
        return map;
    }

}