package com.example.fileapi.dao;

import com.example.fileapi.entities.FileInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;

@Repository
@RequiredArgsConstructor
public class FileDao {
    private static final String CREATE_FILE = "INSERT INTO files_info(file_name, file_size, file_key, upload_date) VALUES (?, ?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    public FileInfo create(final FileInfo file) {
        LocalDate uploadDate = LocalDate.now();
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(x -> {
            PreparedStatement preparedStatement = x.prepareStatement(CREATE_FILE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, file.getName());
            preparedStatement.setLong(2, file.getSize());
            preparedStatement.setString(3, file.getKey());
            preparedStatement.setDate(4, Date.valueOf(uploadDate));
            return preparedStatement;
        }, keyHolder);

        return file.toBuilder()
                .id(keyHolder.getKey().longValue())
                .uploadFile(uploadDate)
                .build();
    }
}
