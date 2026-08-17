package br.com.CarlosHenriqueSL.services;

import br.com.CarlosHenriqueSL.config.EmailConfig;
import br.com.CarlosHenriqueSL.data.dto.request.EmailRequestDTO;
import br.com.CarlosHenriqueSL.mail.EmailSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class EmailService {

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private EmailConfig emailConfigs;

    public void sendSimpleEmail(EmailRequestDTO emailRequest) {
        emailSender.to(emailRequest.getTo())
            .withSubject(emailRequest.getSubject())
            .withMessage(emailRequest.getBody())
            .send(emailConfigs);
    }

    public void sendEmailWithAttachment(String emailRequestJSON, MultipartFile attachment) {
        File tempFile = null;

        try {
            EmailRequestDTO emailRequest = new ObjectMapper().readValue(emailRequestJSON, EmailRequestDTO.class);
            tempFile = File.createTempFile("attachment", attachment.getOriginalFilename());
            attachment.transferTo(tempFile);

            emailSender.to(emailRequest.getTo())
                    .withSubject(emailRequest.getSubject())
                    .withMessage(emailRequest.getBody())
                    .attach(tempFile.getAbsolutePath())
                    .send(emailConfigs);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing email request JSON!", e);
        } catch (IOException e) {
            throw new RuntimeException("Error processing file!", e);
        }
        finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}
