package br.com.CarlosHenriqueSL.file.importer.factory;

import br.com.CarlosHenriqueSL.exception.BadRequestException;
import br.com.CarlosHenriqueSL.file.importer.contract.FileImporter;

import br.com.CarlosHenriqueSL.file.importer.impl.CsvImporter;
import br.com.CarlosHenriqueSL.file.importer.impl.XlsxImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FileImporterFactory {

    Logger logger = LoggerFactory.getLogger(FileImporterFactory.class);

    @Autowired
    private ApplicationContext context;

    public FileImporter getImporter(String fileName) {
        if (fileName.endsWith(".xlsx")) {
            // return new XlsxImporter();
            return context.getBean(XlsxImporter.class);
        } else if (fileName.endsWith(".csv")) {
            // return new CsvImporter();
            return context.getBean(CsvImporter.class);
        } else {
            throw new BadRequestException("Invalid file format!");
        }
    }
}
