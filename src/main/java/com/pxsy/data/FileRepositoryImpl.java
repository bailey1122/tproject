package com.pxsy.data;

import com.pxsy.clients.FileUploaderClient;
import com.pxsy.clients.FilesForFolder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Repository
public class FileRepositoryImpl implements FileRepository {

    static final Logger LOGGER = Logger.getLogger(FileRepositoryImpl.class);

    @Override
    public List<String> listFilesForFolder(final File folder) {
        LOGGER.warn("Listing files for a folder from a repository");
        FilesForFolder ff = new FilesForFolder();
        return ff.listFilesForFolder(folder);
    }

    @Override
    public String readFile(String path) throws IOException {
        LOGGER.warn("Reading a file from a repository");
        FilesForFolder ff = new FilesForFolder();
        return ff.readFile(path);
    }

    @Override
    public void upload(String filename) {
        LOGGER.warn("Uploading a file to an FTP from a repository");
        FileUploaderClient fu = new FileUploaderClient();
        fu.upload(filename);
    }
}
