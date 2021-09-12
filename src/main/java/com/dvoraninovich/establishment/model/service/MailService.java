package com.dvoraninovich.establishment.model.service;

import com.dvoraninovich.establishment.exception.ServiceException;

public interface MailService {
    void sendMail(String recipient, String subject, String body) throws ServiceException;
}
