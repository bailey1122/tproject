package com.pxsy.data;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileRepository {

    List<String> listFilesForFolder(final File folder);

    String readFile(String path) throws IOException;

    void upload(String filename);
}
