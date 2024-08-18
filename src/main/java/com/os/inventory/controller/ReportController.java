package com.os.inventory.controller;

import com.os.inventory.common.BaseController;
import com.os.inventory.services.service.ReportActionService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

@Controller
public class ReportController extends BaseController {

    @Autowired
    ReportActionService reportActionService;

    @GetMapping("/viewloadproductsinstock")
    public String showPoductsInStock(Model model) {
        return "view/report/productsinstock";
    }

    @RequestMapping(value = "/loadproductsinstock", method = {RequestMethod.GET, RequestMethod.POST})
    public void loadpPoductsInStock(@RequestParam Map<String, Object> parameters, HttpServletResponse response) throws IOException, JRException, SQLException {
        response.setHeader("Content-Disposition", String.format("inline; filename = Product_In_Stock_Report" + "_"
                + java.time.LocalDate.now() + "." + parameters.get("exportMode")));
        OutputStream out = response.getOutputStream();
        response.setContentType("application/" + parameters.get("exportMode"));
        parameters.put("fileName", "rptproductinstock.jrxml");
        JasperPrint jasperPrint = reportActionService.exportAllfellowsReport(parameters);
        super.exportModeSelector((String) parameters.get("exportMode"), jasperPrint, out);
    }

    @GetMapping("/viewproductssoldlist")
    public String showFellowWithoutExam(Model model) {
        return "view/report/rptproductsold";
    }

    @RequestMapping(value = "/loadsoldproducts", method = {RequestMethod.GET, RequestMethod.POST})
    public void loadFellowWithoutExamReport(@RequestParam Map<String, Object> parameters, HttpServletResponse response) throws IOException, JRException, SQLException {
        response.setHeader("Content-Disposition", String.format("inline; filename = Product_Sold_Report" + "_"
                + java.time.LocalDate.now() + "." + parameters.get("exportMode")));
        OutputStream out = response.getOutputStream();
        response.setContentType("application/" + parameters.get("exportMode"));
        parameters.put("fileName", "rptproductsold.jrxml");
        JasperPrint jasperPrint = reportActionService.exportAllfellowsReport(parameters);
        super.exportModeSelector((String) parameters.get("exportMode"), jasperPrint, out);
    }
}
