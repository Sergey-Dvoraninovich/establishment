package com.dvoraninovich.establishment.model.service.impl;

import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.service.MailService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailServiceImpl implements MailService {
    private static final Logger logger = LogManager.getLogger(MailServiceImpl.class);
    private static final String MAIL_PROPERTIES_NAME = "mail.properties";
    private static final String USERNAME_PROPERTY = "username";
    private static final String PASSWORD_PROPERTY = "password";
    private static final String HTML_BODY_TYPE = "text/html; charset=UTF-8";

    private static Session mailSession;
    private static String sender;

    static {
        ClassLoader classLoader = MailServiceImpl.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(MAIL_PROPERTIES_NAME)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            sender = properties.getProperty(USERNAME_PROPERTY);
            String password = properties.getProperty(PASSWORD_PROPERTY);

            mailSession = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(sender, password);
                }
            });
        } catch (IOException e) {
            logger.fatal("Unable to read mailing properties", e);
            throw new RuntimeException("Unable to read mailing properties", e);
        }
    }

    @Override
    public void sendMail(String recipient, String subject, String body) throws ServiceException {
        Message message = new MimeMessage(mailSession);

        try {
            message.setFrom(new InternetAddress(sender));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);

            //MimeBodyPart mimeBodyPart = new MimeBodyPart();
            //mimeBodyPart.setContent(body, HTML_BODY_TYPE);

            //Multipart multipart = new MimeMultipart();
            //multipart.addBodyPart(mimeBodyPart);

            //message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new ServiceException(e);
        }
    }
}
