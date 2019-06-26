package com.Beans;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Hp on 11/20/2018.
 */
public class FileWrapper {

    private MultipartFile file;

    private String types;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }


}
