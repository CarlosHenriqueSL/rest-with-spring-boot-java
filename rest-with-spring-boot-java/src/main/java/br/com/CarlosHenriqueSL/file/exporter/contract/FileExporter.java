package br.com.CarlosHenriqueSL.file.exporter.contract;

import br.com.CarlosHenriqueSL.data.dto.PersonDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface FileExporter {

    Resource exportFile(List<PersonDTO> people) throws Exception;
}