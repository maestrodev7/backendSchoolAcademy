package com.example.school.application.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Service pour envoyer des emails via l'API externe
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    @Value("${email.service.url:https://servicemail.e-etatcivil.com/api/mail/send}")
    private String emailServiceUrl;

    @Value("${email.service.from-name:Qdov Academy}")
    private String fromName;

    private final RestTemplate restTemplate;

    /**
     * Envoie un email avec un OTP
     */
    public void sendOtpEmail(String destinationEmail, String otpCode, String purpose) {
        String subject = "Code de vérification - Qdov Academy";
        String title = "Code OTP";
        String content = String.format(
            "<p>Bonjour,</p>" +
            "<p>Votre code de vérification pour %s est : <strong>%s</strong></p>" +
            "<p>Ce code est valide pendant 15 minutes.</p>" +
            "<p>Si vous n'avez pas demandé ce code, veuillez ignorer cet email.</p>",
            purpose, otpCode
        );

        sendEmail(destinationEmail, subject, title, content);
    }

    /**
     * Envoie un email avec les identifiants de connexion
     */
    public void sendCredentialsEmail(String destinationEmail, String username, String password) {
        String subject = "Vos identifiants de connexion - Qdov Academy";
        String title = "Bienvenue sur Qdov Academy";
        String content = String.format(
            "<p>Bonjour,</p>" +
            "<p>Votre compte a été créé avec succès sur Qdov Academy.</p>" +
            "<p><strong>Identifiants de connexion :</strong></p>" +
            "<ul>" +
            "<li><strong>Nom d'utilisateur :</strong> %s</li>" +
            "<li><strong>Mot de passe temporaire :</strong> %s</li>" +
            "</ul>" +
            "<p>Pour votre sécurité, veuillez changer votre mot de passe lors de votre première connexion.</p>" +
            "<p>Vous recevrez également un code OTP par email pour confirmer votre compte.</p>",
            username, password
        );

        sendEmail(destinationEmail, subject, title, content);
    }

    /**
     * Envoie un email générique
     */
    private void sendEmail(String destinationEmail, String subject, String title, String content) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("destinationEmail", destinationEmail);
            body.add("mailSubject", subject);
            body.add("mailTitle", title);
            body.add("mailContentText", content);
            body.add("fromName", fromName);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                emailServiceUrl,
                requestEntity,
                String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Email envoyé avec succès à {}", destinationEmail);
            } else {
                log.error("Erreur lors de l'envoi de l'email à {}: {}", destinationEmail, response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi de l'email à {}: {}", destinationEmail, e.getMessage(), e);
            // Ne pas faire échouer la création de l'utilisateur si l'email échoue
        }
    }
}

