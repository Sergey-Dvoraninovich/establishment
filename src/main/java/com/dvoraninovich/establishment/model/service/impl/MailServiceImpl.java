package com.dvoraninovich.establishment.model.service.impl;

import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.service.MailService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.*;
//import jakarta.mail.*;
//import jakarta.mail.internet.InternetAddress;
//import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class MailServiceImpl implements MailService {
    private static final Logger logger = LogManager.getLogger(MailServiceImpl.class);
    private static final String MAIL_SUBJECT = "Account activation";
    private static final String MAIL_PROPERTIES_NAME = "mail.properties";
    private static final String USERNAME_PROPERTY = "username";
    private static final String PASSWORD_PROPERTY = "password";
    private static final String HTML_BODY_TYPE = "text/html; charset=UTF-8";

    private static MailServiceImpl instance;
    private static Session mailSession;
    private static String sender;

    public static MailServiceImpl getInstance() {
        if (instance == null) {
            instance = new MailServiceImpl();
        }
        return instance;
    }

    private MailServiceImpl() {
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
            logger.fatal("Impossible to read mailing properties", e);
            throw new RuntimeException("Impossible to read mailing properties", e);
        }
    }

    public boolean sendActivateMail(String recipient, String activationCode) throws ServiceException {
        boolean result = true;
        String mailText = createActivationText(activationCode);
        Message message = createMessage(recipient, MAIL_SUBJECT, mailText);

        try {
            Transport.send(message);
        } catch (MessagingException e) {
            logger.error("Impossible to send email", e);
            throw new ServiceException("Impossible to send email", e);
        }
        return result;
    }

    private String createActivationText(String activateCode) {
        StringBuilder mailText = new StringBuilder("Email confirmation ");
        mailText.append(" To be able to order dishes, activate your account. ");
        mailText.append(" Your verification code is: ");
        mailText.append(activateCode);
        mailText.append(" If you haven't signed up, just ignore this email. Thanks.");
        return mailText.toString();
    }

    private Message createMessage(String recipient, String mailSubject, String mailText) {
        Message message = new MimeMessage(mailSession);
        try {
            message.setFrom(new InternetAddress(sender));
            message.setSubject(mailSubject);
            message.setContent(mailText, HTML_BODY_TYPE);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        } catch (MessagingException e) {
            logger.error("Exception in init Email Message", e);
            return null;
        }
        return message;
    }
}
