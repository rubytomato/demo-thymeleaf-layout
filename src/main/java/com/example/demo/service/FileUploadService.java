package com.example.demo.service;

import com.example.demo.dto.FileUploadResultDto;

import java.io.InputStream;

public interface FileUploadService {
  FileUploadResultDto upload(String fileName, long fileSize, InputStream inputStream);
}
