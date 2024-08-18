package com.os.inventory.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@Service
public class BaseController {
    protected void exportModeSelector(String xprtMode, JasperPrint jasperPrint, OutputStream outputStream) throws JRException {
        if (xprtMode.equals("pdf")) {// exports report to pdf
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            exporter.setConfiguration(configuration);
            try {
                exporter.exportReport();
            } catch (JRException e) {
                e.printStackTrace();
            }
        }else {// exports report to excel
            JRXlsxExporter xlsExporter = new JRXlsxExporter();
            xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();
            reportConfig.setOnePagePerSheet(false);
            reportConfig.setRemoveEmptySpaceBetweenRows(true);
            reportConfig.setDetectCellType(true);
            reportConfig.setWhitePageBackground(false);
            xlsExporter.setConfiguration(reportConfig);
            try {
                xlsExporter.exportReport();
            } catch (JRException e) {
                e.printStackTrace();
            }
        }
    }

}
