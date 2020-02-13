package com.pxsy.api;

import com.pxsy.data.FileRepository;
import com.pxsy.exceptions.Error;
import com.pxsy.exceptions.NoFileAvailableException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class Uploader {

    static final Logger LOGGER = Logger.getLogger(Uploader.class);

    private static final String UPLOADING_DIR = "C:\\files\\"; // local folder
    private static final String TOPIC = "Kafka_top"; // kafka's topic

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private FileRepository fileRepository;

    @Autowired
    public Uploader(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    // lists available files for a local folder
    @GetMapping
    public ModelAndView home() {
        LOGGER.info("Entering the Home controller");
        final File folder = new File(UPLOADING_DIR);
        ModelAndView mv = new ModelAndView();
        mv.addObject("names", fileRepository.listFilesForFolder(folder));
        mv.setViewName("home");
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "text/plain")
    public @ResponseBody ResponseEntity findFile(@RequestParam("val") String val) {
        LOGGER.info("Entering the FindFile restController");
        String v;
        try {
            LOGGER.info("Reading a file");
            // reads a file
            v = fileRepository.readFile(val);
        } catch (IOException e) {
            LOGGER.error("Error while reading a file");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (v != null) {
            LOGGER.info("Sending an output");
            // sends an output by kafka
            kafkaTemplate.send(TOPIC, v);
            LOGGER.info("Uploading a file");
            // uploads a file to a FTP
            fileRepository.upload(val);
            // everything is going great
            return new ResponseEntity(HttpStatus.OK);
        }
        LOGGER.error("Error while reading a file. Inappropriate format or empty");
        throw new NoFileAvailableException(v);
    }

    // register a user-defined exception
    @ExceptionHandler(NoFileAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    Error noFileAvailable(NoFileAvailableException e) {
        String fileName = e.getFileName();
        return new Error(4, "File is unreadable or empty [" + fileName + "]");
    }

    // upload a file or files to a local folder
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity uploadingPost(@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles) throws IOException {
        LOGGER.info("Entering the UploadingPost");
            for (MultipartFile uploadedFile : uploadingFiles) {
                // uploads files
                File file = new File(UPLOADING_DIR + uploadedFile.getOriginalFilename());
                LOGGER.info("Transferring a file");
                try {
                    uploadedFile.transferTo(file);
                } catch (IOException ex) {
                    LOGGER.error("Error while uploading files");
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }
            }
        return new ResponseEntity(HttpStatus.OK);
    }
}