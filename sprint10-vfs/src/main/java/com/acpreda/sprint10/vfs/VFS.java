package com.acpreda.sprint10.vfs;

public interface VFS {

    void save(VFSFile file);
    VFSFile find(String name);
    void addFileToFolder(String file, String folder);

}
