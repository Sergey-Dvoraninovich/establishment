package com.dvoraninovich.establishment.model.service;

import com.dvoraninovich.establishment.exception.ServiceException;

import javax.servlet.http.Part;
import java.util.Collection;

public interface FileUploadService {
    public String uploadFile(String applicationDir, Collection<Part> parts,
                             String objectType, String objectId) throws ServiceException;
}
