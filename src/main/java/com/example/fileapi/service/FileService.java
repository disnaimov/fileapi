package com.example.fileapi.service;

import com.example.fileapi.dao.FileDao;
import com.example.fileapi.entities.FileInfo;
import com.example.fileapi.util.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileDao fileDao;
    private final FileManager fileManager;

    @Transactional(rollbackFor = {IOException.class})
    public FileInfo upload(MultipartFile resource) throws IOException {
        String key = UUID.randomUUID().toString();
        FileInfo createdFile = FileInfo.builder()
                .name(resource.getOriginalFilename())
                .key(key)
                .size(resource.getSize())
                .build();

        createdFile = fileDao.create(createdFile);
        fileManager.upload(resource.getBytes(), key);

        return createdFile;
    }
}
