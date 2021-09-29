package com.dvoraninovich.establishment.model.service.impl;

import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.service.FileUploadService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.regex.Pattern;

public class FileUploadServiceImpl implements FileUploadService {
    private static final Logger logger = LogManager.getLogger(FileUploadServiceImpl.class);
    private static final String UPLOAD_DIR = "images";
    private static final String LOCAL_UPLOAD_DIR = "c:\\tmp\\";
    private static final String PNG_EXTENSION = ".png";
    private static final String JPG_EXTENSION = ".jpg";
    private static FileUploadServiceImpl instance;

    private FileUploadServiceImpl() {
    }

    public static FileUploadServiceImpl getInstance() {
        if (instance == null) {
            instance = new FileUploadServiceImpl();
        }
        return instance;
    }

    public String uploadFile(String applicationDir, Collection<Part> parts,
                             String objectClassName, String objectId) throws ServiceException{

        String filename = "";
        objectClassName = objectClassName.substring(objectClassName.lastIndexOf(".")+1);
        objectClassName = objectClassName.toLowerCase();
        String uploadFileDir = applicationDir + File.separator
                + UPLOAD_DIR + File.separator
                + objectClassName.toLowerCase() + File.separator;

        File fileSaveDir = new File(uploadFileDir);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        try{
            for (Part part: parts){
                if (part.getSubmittedFileName() != null){
                    if (!part.getSubmittedFileName().equals("")) {
                        filename = part.getSubmittedFileName();
                        String extension = filename.substring(filename.lastIndexOf("."));
                        if (Pattern.matches(PNG_EXTENSION, extension)
                            || Pattern.matches(JPG_EXTENSION, extension)) {
                            filename = objectClassName + "_" + objectId + "_" + LocalDateTime.now() + extension;
                            filename = filename.replace(":", "-");
                            part.write(uploadFileDir + filename);
                            part.write(LOCAL_UPLOAD_DIR + filename);
                        }
                        else {
                            filename = "";
                        }
                    }
                }
            }

        }
        catch (IOException e){
            logger.info("Impossible to upload file ", e);
            throw new ServiceException("Impossible to upload file ", e);
        }
        return filename;
    }
}