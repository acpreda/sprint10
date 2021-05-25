package com.acpreda.sprint10.vfs;

public class VFSFile {

    private final String name;
    private final String originalName;
    private final String contentType;

    public VFSFile(String name, String originalName, String contentType) {
        this.name = name;
        this.originalName = originalName;
        this.contentType = contentType;
    }

    public String getName() {
        return name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getContentType() {
        return contentType;
    }
}
