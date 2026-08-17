package br.com.CarlosHenriqueSL.controllers;

import br.com.CarlosHenriqueSL.controllers.docs.EmailControllerDocs;
import br.com.CarlosHenriqueSL.data.dto.request.EmailRequestDTO;
import br.com.CarlosHenriqueSL.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/email/v1")
public class EmailController implements EmailControllerDocs {

    @Autowired
    private EmailService service;

    @PostMapping
    @Override
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequestDTO emailRequest) {
        service.sendSimpleEmail(emailRequest);
        return new ResponseEntity<>("E-mail sent with success", HttpStatus.OK);
    }

    @PostMapping(value = "/withAttachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public ResponseEntity<String> sendEmailWithAttachment(
            @RequestParam("emailRequest") String emailRequestJSON,
            @RequestParam("attachment") MultipartFile attachment)
    {
        service.sendEmailWithAttachment(emailRequestJSON, attachment);
        return new ResponseEntity<>("E-mail with attachment sent successfully", HttpStatus.OK);
    }
}
