package com.pxsy.clients;

import org.apache.commons.net.ftp.FTPClient;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.net.ftp.FTP;
import org.apache.log4j.Logger;

// upload files from local machine to a remote FTP server.
// It's shown only for an example. Therefore, we will get an exception
// (Connection timed out) when we run our code.
public class FileUploaderClient {

    final static Logger LOGGER = Logger.getLogger(FileUploaderClient.class);

    private static final String UPLOADING_DIR = "C:\\files\\"; // local folder

    public void upload(String fileName) {
        LOGGER.info("Entering the Upload method");
        String server = "www.someserver.com";
        int port = 21;
        String user = "user";
        String pass = "password";

        FTPClient ftpClient = new FTPClient();
        try {
            LOGGER.warn("Connecting to a server");
            // gets a connection
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            File localFile = new File(UPLOADING_DIR + fileName);

            String remoteFile = fileName;
            InputStream inputStream = new FileInputStream(localFile);

            System.out.println("Start uploading first file");
            boolean done = ftpClient.storeFile(remoteFile, inputStream);
            LOGGER.warn("Closing an input stream");
            // close a connection
            inputStream.close();
            if (done) {
                System.out.println("The file is uploaded successfully.");
            }
        } catch (IOException ex) {
            // we'll get an java.net.ConnectException because there's no connection available
            LOGGER.error("Error while uploading a file to an FTP. Message: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    LOGGER.warn("Closing a connection");
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                LOGGER.error("Error while closing a connection");
                ex.printStackTrace();
            }
        }
    }
}

