package com.dvoraninovich.establishment.model.service;

import com.dvoraninovich.establishment.exception.ServiceException;

import javax.servlet.http.Part;
import java.util.Collection;

/**
 * The interface File upload service.
 */
public interface FileUploadService {
    /**
     * Upload file string.
     *
     * @param applicationDir the application dir
     * @param parts          the parts
     * @param objectType     the object type
     * @param objectId       the object id
     * @return the string of uploaded file name.
     * If string equals "", then file wasn't uploaded
     * @throws ServiceException the service exception
     */
    String uploadFile(String applicationDir, Collection<Part> parts,
                      String objectType, String objectId) throws ServiceException;
}
