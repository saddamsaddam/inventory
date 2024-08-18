package com.os.inventory.services.service;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.util.Map;

@Service
@Component
public class ReportActionService {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    DataSource dataSource;

    @Autowired
    ServletContext context;

    public JasperPrint exportAllfellowsReport(Map parameters) {
         String RPT_FILE_LOCATION = "/WEB-INF/jasper/";
        try {
            String jasperFileName = (String) parameters.get("fileName");
            log.info(jasperFileName + "------------ Report printing------------ start----");
            String absolutePath = context.getRealPath(RPT_FILE_LOCATION + jasperFileName);
            JasperReport jasperReport = JasperCompileManager.compileReport(absolutePath);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());
            log.info(jasperFileName + "------------ Report printing------------ stop----");
            return print;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
