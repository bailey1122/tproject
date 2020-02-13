package com.pxsy.clients;

import org.apache.log4j.Logger;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FilesForFolder {

    static final Logger LOGGER = Logger.getLogger(FilesForFolder.class);

    // lists available files for some folder
    public List<String> listFilesForFolder(final File folder) {
        LOGGER.info("Listing files for a folder");
        List<String> list = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                LOGGER.info("Iterating a folder");
                // gets to a root folder
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
                LOGGER.info("Adding files to a list");
                // adds available files to a list
                list.add(fileEntry.getName());
            }
        }
        return list;
    }

    // reads text files only
    public String readFile(String path) throws FileNotFoundException {
        LOGGER.info("Reading a file");
        File file = new File("C:\\files\\" + path);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        if (file.isFile() && file.getName().endsWith(".txt")) {
            // it's synchronized, so read operations
            // can safely be done from multiple threads
            BufferedReader br = null;
            try {
            br = new BufferedReader(isr);
            String line;
            // reads the first line
            line = br.readLine();
            System.out.println(line);
            LOGGER.warn("Closing a connection");
            // close a connection
            br.close();
            return line;
            } catch (IOException e) {
                LOGGER.error("Error while reading a file");
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        LOGGER.warn("Closing the connection");
                        br.close();
                    } catch (IOException e) {
                        LOGGER.error("Error while closing a connection");
                        e.printStackTrace();
                    }
                }
            }
        }
        LOGGER.warn("File is empty or inappropriate");
        return null;
    }
}
