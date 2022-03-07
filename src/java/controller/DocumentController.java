/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DocumentDao;
import entity.Document;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;

/**
 *
 * @author Burak Fircasiguzel < www.github.com/burakfircasiguzel >
 */
@SessionScoped
@ManagedBean(name = "DocumentController", eager = true)
public class DocumentController implements Serializable {

    private Document document;
    private DocumentDao documentDao;
    private List<Document> documentList;

    private Part doc;

    private String uploadTo = "/Users/ELOCK2/Documents/NetBeansProjects/BlogApp/img/";

    public void upload() {
        try {
            InputStream input = doc.getInputStream();
            File f = new File(uploadTo + doc.getSubmittedFileName());
            Files.copy(input, f.toPath());
            document = this.getDocument();
            document.setFilePath(f.getParent());
            document.setFileName(f.getName());
            document.setFileType(doc.getContentType());
            this.getDocumentDao().insert(document);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int uploadWithBlogId(int blog_id) {
        int doc_id = 0;
        try {
            InputStream input = doc.getInputStream();
            File f = new File(uploadTo + doc.getSubmittedFileName());
            Files.copy(input, f.toPath());
            document = this.getDocument();
            document.setFilePath(f.getParent());
            document.setFileName(f.getName());
            document.setFileType(doc.getContentType());
            doc_id = this.getDocumentDao().insertWithBlogId(document, blog_id);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return doc_id;
    }

    public String getUploadTo() {
        return uploadTo;
    }

    public Document getDocument() {
        if (this.document == null) {
            this.document = new Document();
        }
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public DocumentDao getDocumentDao() {
        if (this.documentDao == null) {
            this.documentDao = new DocumentDao();
        }
        return documentDao;
    }

    public void setDocumentDao(DocumentDao documentDao) {
        this.documentDao = documentDao;
    }

    public List<Document> getDocumentList() {
        this.documentList = this.getDocumentDao().findAll();
        return documentList;
    }

    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }

    public Part getDoc() {
        return doc;
    }

    public void setDoc(Part doc) {
        this.doc = doc;
    }
    
    
}
