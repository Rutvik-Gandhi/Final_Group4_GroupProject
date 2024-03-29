package com.nihar.final_group4_groupproject.model;

public class ListItemNewsletter {
    private String id;
    private String fileName;
    private String path;
    private String type;
    private String uploadTime;

    public ListItemNewsletter(String id, String fileName, String path, String type, String uploadTime) {
        this.id = id;
        this.fileName = fileName;
        this.path = path;
        this.type = type;
        this.uploadTime = uploadTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

}
