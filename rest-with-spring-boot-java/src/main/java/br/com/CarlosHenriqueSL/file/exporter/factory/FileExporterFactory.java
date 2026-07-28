package br.com.CarlosHenriqueSL.file.exporter.factory;

import br.com.CarlosHenriqueSL.exception.BadRequestException;
import br.com.CarlosHenriqueSL.file.exporter.MediaTypes;
import br.com.CarlosHenriqueSL.file.exporter.contract.FileExporter;
import br.com.CarlosHenriqueSL.file.exporter.impl.CsvExporter;
import br.com.CarlosHenriqueSL.file.exporter.impl.XlsxExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FileExporterFactory {

    Logger logger = LoggerFactory.getLogger(FileExporterFactory.class);

    @Autowired
    private ApplicationContext context;

    public FileExporter getExporter(String acceptHeader) {
        if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_XLSX_VALUE)) {
            return context.getBean(XlsxExporter.class);
        } else if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_CSV_VALUE)) {
            return context.getBean(CsvExporter.class);
        } else {
            throw new BadRequestException("Invalid file format!");
        }
    }
}
