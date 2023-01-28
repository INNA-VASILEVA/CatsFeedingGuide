package com.example.catsfeedingguide.services.impl;

import com.example.catsfeedingguide.services.FilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

@Service
public class FilesServiceImpl implements FilesService {

    @Value("${path.to.data.files}")
    private String dataFilesPath;

    @Override
    public boolean saveToJsonFile(String json, String dataFileName) {
        Path path = Path.of(dataFilesPath, dataFileName);
        try {
            cleanDataFile(path);
            Files.writeString(path, json);
            return true;
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readFromJsonFile(String dataFileName) {
        try {
            return Files.readString(Path.of(dataFilesPath, dataFileName));
        } catch (NoSuchFileException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean cleanDataFile(Path path) {
        try {
            Files.createDirectories(path.getParent());
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}