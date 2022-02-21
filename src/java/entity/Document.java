/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javax.servlet.http.Part;

/**
 *
 * @author ELOCK2
 */
public class Document {
    
    private int id;
    private String filePath;
    private String fileName;
    private String fileType;
    
    public Document() {
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Document{" + "id=" + id + ", filePath=" + filePath + ", fileName=" + fileName + ", fileType=" + fileType + '}';
    }
    
    

}
