package com.example.fileapi.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder(toBuilder = true)
public class FileInfo {
    private Long id;
    private String name;
    private Long size;
    private String key;
    private LocalDate uploadFile;
}
