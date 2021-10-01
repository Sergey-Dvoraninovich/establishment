package com.dvoraninovich.establishment.model.service;

import com.dvoraninovich.establishment.exception.ServiceException;

public interface MailService {

    boolean sendActivateMail(String recipient, String activationCode) throws ServiceException;
    void test() throws ServiceException;
}
