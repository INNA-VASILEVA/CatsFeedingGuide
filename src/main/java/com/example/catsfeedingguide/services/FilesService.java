package com.example.catsfeedingguide.services;

public interface FilesService {
    boolean saveToJsonFile(String json, String dataFileName);

    String readFromJsonFile(String dataFileName);
}