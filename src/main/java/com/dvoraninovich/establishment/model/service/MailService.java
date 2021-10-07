package com.dvoraninovich.establishment.model.service;

import com.dvoraninovich.establishment.exception.ServiceException;

/**
 * The interface Mail service.
 */
public interface MailService {

    /**
     * Send activate mail.
     *
     * @param recipient      the recipient
     * @param activationCode the activation code
     * @return the boolean result of mail sending
     * @throws ServiceException the service exception
     */
    boolean sendActivateMail(String recipient, String activationCode) throws ServiceException;
}
