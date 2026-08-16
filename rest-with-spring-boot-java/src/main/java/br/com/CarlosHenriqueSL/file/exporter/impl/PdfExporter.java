package br.com.CarlosHenriqueSL.file.exporter.impl;

import br.com.CarlosHenriqueSL.data.dto.PersonDTO;
import br.com.CarlosHenriqueSL.file.exporter.contract.PersonExporter;
import br.com.CarlosHenriqueSL.services.QRCodeService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PdfExporter implements PersonExporter {

    @Autowired
    private QRCodeService service;

    @Override
    public Resource exportPeople(List<PersonDTO> people) throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/templates/people.jrxml");

        if (inputStream == null) {
            throw new RuntimeException("Template file not found: /templates/people.jrxml");
        }

        // Compiles the inputStream file "/templates/people.jrxml"
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(people);

        // We'll not use parameters
        // parameters.put("title", "Jasper Report");
        Map<String, Object> parameters = new HashMap<>();

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            return new ByteArrayResource(outputStream.toByteArray());
        }
    }

    @Override
    public Resource exportPerson(PersonDTO person) throws Exception {
        InputStream mainTemplateStream = getClass().getResourceAsStream("/templates/person.jrxml");

        if (mainTemplateStream == null) {
            throw new RuntimeException("Template file not found: /templates/person.jrxml");
        }

        InputStream subReportStream = getClass().getResourceAsStream("/templates/books.jrxml");

        if (subReportStream == null) {
            throw new RuntimeException("Template file not found: /templates/books.jrxml");
        }

        /* Compiles the mainTemplateStream file at "/templates/person.jrxml"
           and the subReportStream file at "/templates/books.jrxml" */
        JasperReport mainReport = JasperCompileManager.compileReport(mainTemplateStream);
        JasperReport subReport = JasperCompileManager.compileReport(subReportStream);

        // Just one instance
        JRBeanCollectionDataSource mainDataSource = new JRBeanCollectionDataSource(Collections.singletonList(person));

        JRBeanCollectionDataSource subReportDataSource = new JRBeanCollectionDataSource(person.getBooks());

        // Generates the QRCode that will be passed as "QR_CODEIMAGE" parameter
        InputStream qrCodeStream = service.generateQRCode(person.getProfileUrl(), 200, 200);

        String path = getClass().getResource("/templates/books.jasper").getPath();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("SUB_REPORT_DATA_SOURCE", subReportDataSource);
        parameters.put("SUB_REPORT_DIR", path);
        parameters.put("BOOK_SUB_REPORT", subReport);
        parameters.put("QR_CODEIMAGE", qrCodeStream);

        JasperPrint jasperPrint = JasperFillManager.fillReport(mainReport, parameters, mainDataSource);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            return new ByteArrayResource(outputStream.toByteArray());
        }
    }
}
