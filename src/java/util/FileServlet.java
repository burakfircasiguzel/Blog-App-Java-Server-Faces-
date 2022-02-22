/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import controller.DocumentController;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ELOCK2
 */
@WebServlet(name = "FileServlet", urlPatterns = {"/img/*"})
public class FileServlet extends HttpServlet {

    
    private DocumentController documentController;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String file = request.getPathInfo();
        File f = new File(getDocumentController().getUploadTo() + file);

        Files.copy(f.toPath(), response.getOutputStream());
    }
    
        public DocumentController getDocumentController() {
        if (this.documentController == null) {
            this.documentController = new DocumentController();
        }
        return documentController;
    }

    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
    }
    
    

}
