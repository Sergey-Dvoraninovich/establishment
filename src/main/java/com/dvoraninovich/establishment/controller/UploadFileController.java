package com.dvoraninovich.establishment.controller;

import com.dvoraninovich.establishment.controller.command.validator.IngredientValidator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@WebServlet(urlPatterns = {"/upload/*"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class UploadFileController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(UploadFileController.class);
    private static final String UPLOAD_DIR = "uploads";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String applicationDir = request.getServletContext().getRealPath("");

        String uploadFileDir = applicationDir + File.separator + UPLOAD_DIR + File.separator;

        File fileSaveDir = new File(uploadFileDir);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        //TODO Choose variant
        /*int[] counter = {0};
        // System.out.println("Upload File Directory ="+fileSaveDir.getAbsolutePath());
        request.getParts()
                .stream()
                .forEach(part -> {
                    try {
                        // part.write(uploadFilePath + File.separator + part.getSubmittedFileName());//.substring(2)
                        String randFilename = null;
                        if (counter[0] == 0) {
                            String path = part.getSubmittedFileName();
                            randFilename = UUID.randomUUID() + path.substring(path.lastIndexOf("."));//
                            counter[0] = 1;
                        }
                        part.write(uploadFileDir + randFilename);
                        request.setAttribute("upload_result", " upload successfully ");
                    } catch(IOException e){
                        request.setAttribute("upload_result", " upload failed ");
                    }
                });
        request.getRequestDispatcher("/jsp/upload_res.jsp").forward(request, response);
        }
        */

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try{
            for (Part part: request.getParts()){
                if (part.getSubmittedFileName() != null){
                    //System.out.println(part.getSubmittedFileName());
                    part.write("c:\\tmp\\" + part.getSubmittedFileName());
                    response.getWriter().print(part.getSubmittedFileName() + " uploaded successfully");
                }
            }

        }
        catch (IOException e){
            logger.info("Impossible to upload file ", e);
        }
    }
}
