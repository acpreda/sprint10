package com.acpreda.sprint10.vfs;

import java.util.List;

public class VFSFolder {

    private final String name;
    private final List<VFSFile> files;

    public VFSFolder(String name, List<VFSFile> files) {
        this.name = name;
        this.files = files;
    }

    public String getName() {
        return name;
    }

    public List<VFSFile> getFiles() {
        return files;
    }
}
