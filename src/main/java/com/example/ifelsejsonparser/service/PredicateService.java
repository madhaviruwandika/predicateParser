package com.example.ifelsejsonparser.service;

import com.example.ifelsejsonparser.model.EvaluationInput;
import com.example.ifelsejsonparser.model.PredicateNode;
import com.example.ifelsejsonparser.model.exception.FileStorageException;
import com.example.ifelsejsonparser.model.filePaser.Constant;
import com.example.ifelsejsonparser.model.filePaser.Parser;
import com.example.ifelsejsonparser.model.filePaser.ParserFactory;
import com.example.ifelsejsonparser.model.filePaser.Util;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class PredicateService {
    private final Path fileStorageLocation;

    private String uploadDir = "./predicateFiles";
    ObjectMapper mapper;

    public PredicateService() {
        // absolute path of file storage location
        this.fileStorageLocation = Paths.get(uploadDir)
                .toAbsolutePath().normalize();

        // create directory to store predicate file - if not exist
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }

        mapper = new ObjectMapper()
                .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
    }

    /**
     * Override the predicate file in the given location
     */
    public void uploadPredicate(String predicate) {

        try {
            JSONParser jsonParser = new JSONParser(predicate);
        } catch (Exception e) {
            log.error("Could not process the predicate string and write data. Invalid json format");
            throw new FileStorageException("Could not process the predicate string and wite data", e);
        }

        try {
            log.info("Starting to override data in predicate file");
            File file = new  File(fileStorageLocation+"/test.json");

            // if file doesn't exist, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(predicate);
            bw.close();
            log.info("Successfully overwrote data in predicate file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Evaluate predicate on given input and return the result
     */
    public boolean evaluate(EvaluationInput input) {
        // parser factory is introduced for future extension of different file format.
        // Current implementation only covers the json parsing
        ParserFactory parserFactory = new ParserFactory();
        Parser parser = parserFactory.getParser(Constant.json);

        try {
            String filename = "test.json";
            File file = new  File(fileStorageLocation+"/"+filename);
            if (!file.exists()) {
                file = Util.getFileFromResource(filename);
            }
            PredicateNode predicateNode = parser.parse(file);
            return predicateNode.findResult(input.getA(), input.getB());
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}
