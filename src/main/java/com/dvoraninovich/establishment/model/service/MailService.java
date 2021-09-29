package com.dvoraninovich.establishment.model.service;

import com.dvoraninovich.establishment.exception.ServiceException;

/**
 * The interface Mail service.
 */
public interface MailService {
    /**
     * Send mail.
     *
     * @param recipient the recipient
     * @param subject   the subject
     * @param body      the body
     * @throws ServiceException the service exception
     */
    void sendMail(String recipient, String subject, String body) throws ServiceException;
}
